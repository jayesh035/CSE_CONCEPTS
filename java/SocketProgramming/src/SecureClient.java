import javax.net.ssl.*;
import java.io.*;
import java.security.KeyStore;

public class SecureClient {
    public static void main(String[] args) {
        try {
            // Optional: Load TrustStore to verify server certificate
            char[] trustStorePassword = "password".toCharArray();
            KeyStore trustStore = KeyStore.getInstance("JKS");

            trustStore.load(new FileInputStream("client.truststore"), trustStorePassword);

            // Initialize TrustManagerFactory
            TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
            tmf.init(trustStore);

            // Set up SSLContext
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, tmf.getTrustManagers(), null);

            // Create SSLSocketFactory and SSLSocket
            final int PORT=8876;
            SSLSocketFactory factory = sslContext.getSocketFactory();
            SSLSocket socket = (SSLSocket) factory.createSocket("localhost", PORT);

            System.out.println("Connected to Secure Server...");

            // Send message to server
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            out.println("Hello from Secure Client!");

            // Read server response
            String response = in.readLine();
            System.out.println("Server says: " + response);

            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}