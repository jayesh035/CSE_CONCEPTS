import java.io.*;
import java.net.*;

public class BroadcastReceiver {
    public static void main(String[] args) {
        final int BROADCAST_PORT = 9876;
        final String SERVICE_NAME = "MyApplication";

        try {
            // Create socket bound to the broadcast port
            DatagramSocket socket = new DatagramSocket(BROADCAST_PORT);
            System.out.println("Broadcast receiver started on port " + BROADCAST_PORT);

            while (true) {
                // Prepare to receive broadcast
                byte[] receiveBuffer = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);

                // Wait for broadcast packet
                socket.receive(receivePacket);

                // Process the received broadcast
                String message = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println("Received broadcast from " +
                        receivePacket.getAddress().getHostAddress() +
                        ": " + message);

                // Check if this is a discovery request
                if (message.startsWith("DISCOVER:")) {
                    // Prepare response
                    String response = "SERVICE:" + SERVICE_NAME + ":AVAILABLE";
                    byte[] sendData = response.getBytes();

                    // Get sender's address and port
                    InetAddress senderAddress = receivePacket.getAddress();
                    int senderPort = receivePacket.getPort();

                    // Create response packet
                    DatagramPacket sendPacket = new DatagramPacket(
                            sendData,
                            sendData.length,
                            senderAddress,
                            senderPort
                    );

                    // Send response
                    socket.send(sendPacket);
                    System.out.println("Sent response: " + response);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}