import java.io.*;
import java.net.*;

public class AdvancedMulticastSender {
    public static void main(String[] args) {
        final String MULTICAST_GROUP = "230.0.0.1";
        final int MULTICAST_PORT = 4446;

        try {
            // Create a MulticastSocket for sending
            MulticastSocket socket = new MulticastSocket();

            // Set the Time-To-Live (TTL) value
            // TTL controls how far packets will travel:
            // 0 = Same host, 1 = Same subnet (default), 
            // 32 = Same site, 64 = Same region, 128 = Same continent, 255 = Unrestricted
            socket.setTimeToLive(32);

            // Define multicast group address
            InetAddress group = InetAddress.getByName(MULTICAST_GROUP);

            // Send a message
            String message = "Advanced multicast message with TTL=32";
            byte[] data = message.getBytes();

            DatagramPacket packet = new DatagramPacket(
                    data,
                    data.length,
                    group,
                    MULTICAST_PORT
            );

            socket.send(packet);
            System.out.println("Sent multicast message with TTL=32");

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}