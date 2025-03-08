package ChatApplication;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {
    public static void main(String[] args) {
        String serverAddress = "localhost";
        int port = 8081;

        try {
            Socket socket = new Socket(serverAddress, port);
            System.out.println("✅ Connected to chat server!");

            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            Scanner scanner = new Scanner(System.in);

            Thread readerThread = new Thread(() -> {
                try {
                    String message;
                    while ((message = reader.readLine()) != null) {
                        System.out.println("\n📩 " + message);
                        System.out.print("You: ");
                    }
                } catch (Exception e) {
                    System.out.println("⚠ Server disconnected.");
                    System.exit(0);
                }
            });

            readerThread.start();

            while (true) {
                System.out.print("You: ");
                String userMessage = scanner.nextLine();

                if (userMessage.equalsIgnoreCase("exit")) {
                    System.out.println("🚪 Exiting chat...");
                    writer.println("Client disconnected.");
                    break;
                }

                writer.println(userMessage); // Send message
            }

            socket.close();
            scanner.close();
            System.out.println("🔌 Disconnected from server.");

        } catch (Exception e) {
            System.out.println("❌ Could not connect to server.");
        }
    }
}
