import java.io.*;
import java.net.*;
import java.util.*;

public class ReliableUDPClient {
    private static final int MAX_RETRIES = 5;
    private static final int TIMEOUT_MS = 1000; // 1 second

    public static void main(String[] args) {
        try {
            DatagramSocket socket = new DatagramSocket();
            socket.setSoTimeout(TIMEOUT_MS);

            InetAddress serverAddress = InetAddress.getByName("localhost");
            final int SERVER_PORT = 9876;

            // Generate a unique message ID
            String messageId = UUID.randomUUID().toString().substring(0, 8);
            String message = messageId + ":Hello, reliable UDP server!";

            boolean ackReceived = false;
            int retries = 0;

            while (!ackReceived && retries < MAX_RETRIES) {
                // Send message
                byte[] sendData = message.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(
                        sendData,
                        sendData.length,
                        serverAddress,
                        SERVER_PORT
                );

                socket.send(sendPacket);
                System.out.println("Sent message: " + message + " (Attempt " + (retries + 1) + ")");

                try {
                    // Wait for acknowledgment
                    byte[] receiveData = new byte[1024];
                    DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                    socket.receive(receivePacket);

                    String response = new String(receivePacket.getData(), 0, receivePacket.getLength());

                    // Check if this is the acknowledgment for our message
                    if (response.startsWith("ACK:" + messageId)) {
                        ackReceived = true;
                        System.out.println("Received acknowledgment: " + response);
                    } else {
                        System.out.println("Received unexpected response: " + response);
                    }
                } catch (SocketTimeoutException e) {
                    System.out.println("Timeout waiting for acknowledgment");
                    retries++;
                }
            }

            if (ackReceived) {
                System.out.println("Message delivered successfully");
            } else {
                System.out.println("Failed to deliver message after " + MAX_RETRIES + " attempts");
            }

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}