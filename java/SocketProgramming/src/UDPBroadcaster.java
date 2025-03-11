import java.io.*;
import java.net.*;

public class UDPBroadcaster {
    public static void main(String[] args) {
        final int BROADCAST_PORT = 9876;

        try {
            // Create client socket
            DatagramSocket socket = new DatagramSocket();

            // Enable broadcasting (required)
            socket.setBroadcast(true);

            // Broadcast address (255.255.255.255 sends to all devices on local network)
            InetAddress broadcastAddress = InetAddress.getByName("255.255.255.255");

            // Prepare broadcast message
            String broadcastMessage = "DISCOVER:Application Server";
            byte[] sendData = broadcastMessage.getBytes();

            // Create broadcast packet
            DatagramPacket packet = new DatagramPacket(
                    sendData,
                    sendData.length,
                    broadcastAddress,
                    BROADCAST_PORT
            );

            // Send broadcast packet
            socket.send(packet);
            System.out.println("Broadcast message sent: " + broadcastMessage);

            // Wait for responses
            System.out.println("Waiting for responses...");
            byte[] receiveBuffer = new byte[1024];

            // Set timeout for responses
            socket.setSoTimeout(5000); // 5 second timeout

            try {
                while (true) {
                    DatagramPacket responsePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                    socket.receive(responsePacket);

                    String response = new String(responsePacket.getData(), 0, responsePacket.getLength());
                    System.out.println("Received response from " +
                            responsePacket.getAddress().getHostAddress() +
                            ": " + response);
                }
            } catch (SocketTimeoutException e) {
                System.out.println("No more responses received after timeout");
            }

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}