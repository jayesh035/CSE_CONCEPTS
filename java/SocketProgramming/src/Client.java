import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try {
            // Connect to server
            Socket socket = new Socket("localhost", 8080);
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            Scanner sc = new Scanner(System.in);

            System.out.println("✅ Connected to server!");

            // Thread to listen for server messages
            Thread serverListener = new Thread(() -> {
                try {
                    String serverResponse;
                    while ((serverResponse = reader.readLine()) != null) {
                        System.out.println();
                        System.out.println("📩 Server: " + serverResponse);
                    }
                } catch (IOException e) {
                    System.out.println("⚠ Server has disconnected.");
                    System.exit(0);
                }
            });
            serverListener.start();

            // Sending messages to the server
            while (true) {
                System.out.print("Enter message (type 'exit' to quit): ");
                String message = sc.nextLine();

                if (message.equalsIgnoreCase("exit")) {
                    System.out.println("🚪 Closing connection...");
                    writer.println("Client is disconnecting...");
                    break;
                }

                try {
                    if(socket.isConnected())
                    {
                        System.out.println("Opps! Server is closed ");
                        break;
                    }
                    writer.println(message); // Send message to server
                    writer.flush();
                } catch (Exception e) {
                    System.out.println("❌ Server is down. Exiting...");
                    break;
                }
            }

            // Closing resources
            writer.close();
            socket.close();
            sc.close();
            System.out.println("✅ Client disconnected.");

        } catch (IOException e) {
            System.out.println("❌ Error: Unable to connect to server. Is it running?");
        }
    }
}
