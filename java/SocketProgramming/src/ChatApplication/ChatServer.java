package ChatApplication;

import java.io.*;
import java.net.*;
import java.util.concurrent.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

class ClientConnection {
    final int clientId;
    final Socket socket;
    final BufferedReader reader;
    final PrintWriter writer;

    public ClientConnection(int clientId, Socket socket) throws IOException {
        this.clientId = clientId;
        this.socket = socket;
        // Set a short timeout so that read operations do not block indefinitely.
        socket.setSoTimeout(2); // 2 milliseconds timeout
        this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.writer = new PrintWriter(socket.getOutputStream(), true);
    }

    public void send(String message) {
        writer.println(message);
    }
}

public class ChatServer {
    // A map of clientId to connection objects.
    static ConcurrentHashMap<Integer, ClientConnection> clients = new ConcurrentHashMap<>();
    static AtomicInteger clientIdCounter = new AtomicInteger(1);

    public static void main(String[] args) {
        int port = 8081;
        ExecutorService acceptorService = Executors.newSingleThreadExecutor();
        ExecutorService pollerService = Executors.newSingleThreadExecutor();

        // Create the server socket outside of try-with-resources
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("🚀 Server started on port: " + port);

            final ServerSocket finalServerSocket = serverSocket;

            // Start the acceptor thread: it accepts new clients and stores their connection info.
            acceptorService.submit(() -> {
                while (!Thread.currentThread().isInterrupted()) {
                    try {
                        Socket clientSocket = finalServerSocket.accept();
                        int id = clientIdCounter.getAndIncrement();
                        ClientConnection connection = new ClientConnection(id, clientSocket);
                        clients.put(id, connection);
                        connection.send("✅ Connected! Your ID is: " + id);
                        connection.send("📢 Type '@clientID message' to send a private message.");
                        System.out.println("🟢 Client " + id + " connected from " + clientSocket.getRemoteSocketAddress());
                    } catch (IOException e) {
                        if (finalServerSocket.isClosed() || Thread.currentThread().isInterrupted()) {
                            System.out.println("Acceptor thread shutting down");
                            break;
                        }
                        System.out.println("Acceptor error: " + e.getMessage());
                    }
                }
            });

            // Start the poller thread: it iterates over all client connections and attempts to read data.
            pollerService.submit(() -> {
                while (!Thread.currentThread().isInterrupted()) {
                    List<Integer> disconnectedClients = new ArrayList<>();

                    for (ClientConnection conn : clients.values()) {
                        try {
                            // Try reading a line (will wait up to 2 ms due to setSoTimeout)
                            String message = conn.reader.readLine();
                            if (message != null) {
                                System.out.println("📩 Received from " + conn.clientId + ": " + message);
                                processMessage(conn.clientId, message);
                            }
                        } catch (SocketTimeoutException ste) {
                            // No data available from this client within 2ms; move on.
                        } catch (IOException ioe) {
                            // Likely the client disconnected.
                            System.out.println("⚠ Client " + conn.clientId + " disconnected.");
                            disconnectedClients.add(conn.clientId);
                            try {
                                conn.socket.close();
                            } catch (IOException ex) {
                                // ignore
                            }
                        }
                    }

                    // Remove disconnected clients outside the iteration loop
                    for (Integer clientId : disconnectedClients) {
                        clients.remove(clientId);
                    }

                    // Yield a little to avoid CPU spinning too hard.
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException ie) {
                        // Restore interrupt status and break if needed.
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
            });

            // Add shutdown hook to properly close everything
            ServerSocket finalServerSocket1 = serverSocket;
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                System.out.println("Shutting down server...");
                acceptorService.shutdownNow();
                pollerService.shutdownNow();

                // Close all client connections
                for (ClientConnection conn : clients.values()) {
                    try {
                        conn.socket.close();
                    } catch (IOException e) {
                        // Ignore
                    }
                }

                // Close server socket
                try {

                    if (finalServerSocket1 != null && !finalServerSocket1.isClosed()) {
                        finalServerSocket1.close();
                    }
                } catch (IOException e) {
                    // Ignore
                }
            }));

            // Keep the main thread alive
            try {
                Thread.currentThread().join();
            } catch (InterruptedException e) {
                System.out.println("Main thread interrupted");
            }

        } catch (IOException e) {
            System.out.println("❌ Server error: " + e.getMessage());
        }
    }

    // Process messages received from clients.
    // If a message starts with "@", it is considered a private message.
    private static void processMessage(int senderId, String message) {
        if (message.startsWith("@")) {
            int spaceIndex = message.indexOf(" ");
            if (spaceIndex > 1) {
                try {
                    int receiverId = Integer.parseInt(message.substring(1, spaceIndex));
                    String msgContent = message.substring(spaceIndex + 1);
                    ClientConnection receiver = clients.get(receiverId);
                    if (receiver != null) {
                        receiver.send("🔔 Private from " + senderId + ": " + msgContent);
                    } else {
                        ClientConnection sender = clients.get(senderId);
                        if (sender != null) {
                            sender.send("⚠ Client " + receiverId + " not found.");
                        }
                    }
                } catch (NumberFormatException e) {
                    ClientConnection sender = clients.get(senderId);
                    if (sender != null) {
                        sender.send("⚠ Invalid private message format. Use: @<clientID> message");
                    }
                }
            }
        } else {
            // Broadcast to all clients except sender.
            for (ClientConnection conn : clients.values()) {
                if (conn.clientId != senderId) {
                    conn.send("📢 Client " + senderId + " says: " + message);
                }
            }
        }
    }
}




//import java.io.*;
//import java.net.*;
//import java.util.concurrent.*;
//
//class ChatServer {
//    static ServerSocket server;
//    static ConcurrentHashMap<Integer, Socket> clients = new ConcurrentHashMap<>();
//    static BlockingQueue<Message> messageQueue = new LinkedBlockingQueue<>();  // Central queue as DB
//    static ExecutorService clientPool = Executors.newFixedThreadPool(1);  // Handles multiple clients
//
//    public static void main(String[] args) {
//        int port = 8081;
//
//        try {
//            server = new ServerSocket(port);
//            System.out.println("🚀 Server started on port: " + port);
//
//            // Start the single-threaded message processor
//            new Thread(new MessageProcessor()).start();
//
//            while (true) {
//                Socket client = server.accept();
//                client.setSoTimeout(2);
//                int clientId = client.hashCode();
//                clients.put(clientId, client);
//                System.out.println("🟢 Client " + clientId + " connected.");
//
//                clientPool.execute(new ClientHandler(client, clientId));
//
//            }
//        } catch (IOException e) {
//            System.out.println("❌ Server error: " + e.getMessage());
//        }
//    }
//}
//
//// ClientHandler: Reads messages and pushes them to the queue
//class ClientHandler implements Runnable {
//    private final Socket clientSocket;
//    private final int clientId;
//
//    public ClientHandler(Socket clientSocket, int clientId) {
//        this.clientSocket = clientSocket;
//        this.clientId = clientId;
//    }
//
//    @Override
//    public void run() {
//        try (BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
//             PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true)) {
//
//            writer.println("✅ Connected! Your ID is: " + clientId);
//            writer.println("📢 Type '@clientID message' to send a private message.");
//
//            String message= reader.readLine();
//            long time=System.currentTimeMillis();
////            while ((message = reader.readLine()) != null) {
////                if(System.currentTimeMillis()-time > 200)
////                {
////                    break;
////                }
//                System.out.println("📩 Received from " + clientId + ": " + message);
//                processMessage(clientId, message);
////            }
//        }
//        catch (SocketTimeoutException e)
//        {
//
//        }
//        catch (IOException e) {
//            System.out.println("⚠ Client " + clientId + " disconnected.");
//        } finally {
//            ChatApplication.ChatServer.clients.remove(clientId);
//        }
//    }
//
//    private void processMessage(int senderId, String message) {
//        String[] parts = message.split(" ", 2);
//        if (parts.length < 2 || !parts[0].startsWith("@")) {
//            System.out.println("⚠ Invalid format. Use: @receiverID message");
//            return;
//        }
//
//        int receiverId;
//        try {
//            receiverId = Integer.parseInt(parts[0].substring(1));
//        } catch (NumberFormatException e) {
//            System.out.println("⚠ Invalid client ID format.");
//            return;
//        }
//
//        String content = parts[1];
//        ChatApplication.ChatServer.messageQueue.offer(new Message(senderId, receiverId, content));  // Store message in queue
//    }
//}
//
//// MessageProcessor: Single-threaded worker that processes messages from the queue
//class MessageProcessor implements Runnable {
//    @Override
//    public void run() {
//        while (true) {
//            try {
//                Message msg = ChatApplication.ChatServer.messageQueue.take(); // Waits for new messages
//                sendMessage(msg);
//            } catch (InterruptedException e) {
//                System.out.println("⚠ Message processor interrupted.");
//            }
//        }
//    }
//
//    private void sendMessage(Message msg) {
//        Socket receiverSocket = ChatApplication.ChatServer.clients.get(msg.receiverId);
//        if (receiverSocket != null) {
//            try {
//                PrintWriter writer = new PrintWriter(receiverSocket.getOutputStream(), true);
//                writer.println("📩 From " + msg.senderId + ": " + msg.content);
//            } catch (IOException e) {
//                System.out.println("⚠ Failed to send message to client " + msg.receiverId);
//            }
//        } else {
//            System.out.println("⚠ Client " + msg.receiverId + " is offline. Message stored.");
//            // We can persist it in a real database if needed
//        }
//    }
//}
//
//// Message class: Represents a chat message
//class Message {
//    int senderId;
//    int receiverId;
//    String content;
//
//    public Message(int senderId, int receiverId, String content) {
//        this.senderId = senderId;
//        this.receiverId = receiverId;
//        this.content = content;
//    }
//}
