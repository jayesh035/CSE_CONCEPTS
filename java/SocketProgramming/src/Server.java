import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        int port = 8080;
        try {
            ServerSocket server = new ServerSocket(port);
            System.out.println("✅ Server is running on port: " + port);

//            while (true) {  // Keep server running to accept multiple clients
                Socket client = server.accept();
                client.setKeepAlive(true);
                client.setSendBufferSize((8*1024));
//                client.setSoTimeout(500);
                System.out.println("🎉 Client connected!");

                // Create input and output streams
                BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
                PrintWriter writer = new PrintWriter(client.getOutputStream(), true);

                String message;
                while ((message = reader.readLine()) != null) {
                    System.out.println("📩 Client says: " + message);
                    writer.println("✅ Server received: " + message); // Send response to client
                }

                System.out.println("🚫 Client disconnected.");
                client.close(); // Close client connection
//            }

        } catch (Exception e) {
            System.out.println("❌ Error in server: " + e);
        }
    }
}
