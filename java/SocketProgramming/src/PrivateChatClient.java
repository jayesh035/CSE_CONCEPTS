import java.io.*;
import java.net.*;

public class PrivateChatClient {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("10.20.41.76", 8080);
            System.out.println("Connected to the server.");

            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader console = new BufferedReader(new InputStreamReader(System.in));

            // Read welcome message
            System.out.println(reader.readLine());

            // Enter username
            String username = console.readLine();
            writer.println(username);

            // Start a thread to listen for server messages
            Thread listener = new Thread(() -> {
                try {
                    String serverMessage;
                    while ((serverMessage = reader.readLine()) != null) {
                        System.out.println(serverMessage);
                    }
                } catch (IOException e) {
                    System.out.println("Disconnected from server.");
                }
            });
            listener.start();

            // Sending messages
            String userInput;
            while ((userInput = console.readLine()) != null) {
                if (userInput.equalsIgnoreCase("exit")) {
                    writer.println("exit");
                    break;
                }
                writer.println(userInput);
            }

            socket.close();
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
