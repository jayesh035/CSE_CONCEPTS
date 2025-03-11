import java.io.*;
import java.net.*;
import java.util.*;

public class ReliableUDPServer {
    // Track received message IDs to detect duplicates
    private static Set<String> receivedMessageIds = new HashSet<>();

    public static void main(String[] args) {
        final int PORT = 9876;

        try {
            DatagramSocket serverSocket = new DatagramSocket(PORT);
            System.out.println("Reliable UDP Server started on port " + PORT);

            while (true) {
                byte[] receiveData = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

                serverSocket.receive(receivePacket);

                String clientMessage = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println("Received: " + clientMessage);

                // Extract message ID (format: "id:message")
                String[] parts = clientMessage.split(":", 2);
                if (parts.length == 2) {
                    String messageId = parts[0];
                    String messageContent = parts[1];

                    // Check if this is a duplicate message
                    boolean isDuplicate = receivedMessageIds.contains(messageId);

                    if (!isDuplicate) {
                        // Process new message
                        System.out.println("Processing new message: " + messageContent);
                        receivedMessageIds.add(messageId);

                        // Limit the size of our tracking set
                        if (receivedMessageIds.size() > 1000) {
                            // Remove oldest entries (not implemented here for simplicity)
                        }
                    } else {
                        System.out.println("Duplicate message detected, ID: " + messageId);
                    }

                    // Send acknowledgment in either case
                    String ackMessage = "ACK:" + messageId;
                    byte[] sendData = ackMessage.getBytes();

                    InetAddress clientAddress = receivePacket.getAddress();
                    int clientPort = receivePacket.getPort();

                    DatagramPacket sendPacket = new DatagramPacket(
                            sendData,
                            sendData.length,
                            clientAddress,
                            clientPort
                    );

                    serverSocket.send(sendPacket);
                    System.out.println("Sent acknowledgment: " + ackMessage);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}