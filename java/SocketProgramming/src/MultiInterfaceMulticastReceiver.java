import java.io.*;
import java.net.*;
import java.util.*;

public class MultiInterfaceMulticastReceiver {
    public static void main(String[] args) {
        final String MULTICAST_GROUP = "230.0.0.1";
        final int MULTICAST_PORT = 4446;

        try {
            // Print available network interfaces
            System.out.println("Available network interfaces:");
            Enumeration<NetworkInterface> interfaces = null;
            try {
                interfaces = NetworkInterface.getNetworkInterfaces();
            } catch (SocketException e) {
                throw new RuntimeException(e);
            }

            while (interfaces.hasMoreElements()) {
                NetworkInterface ni = interfaces.nextElement();
                if (ni.isUp() && !ni.isLoopback() && ni.supportsMulticast()) {
                    System.out.println("  - " + ni.getName() + ": " + ni.getDisplayName());
                }
            }

            // Create MulticastSocket
            MulticastSocket socket = new MulticastSocket(MULTICAST_PORT);

            // Get the multicast group address
            InetAddress groupAddress = InetAddress.getByName(MULTICAST_GROUP);

            // Join the multicast group on a specific interface
            // You can select a specific interface by name or use null for the default
            NetworkInterface networkInterface = NetworkInterface.getByName("eth0");

            // For Java 7 and above, use:
            socket.joinGroup(new InetSocketAddress(groupAddress, MULTICAST_PORT),
                    networkInterface);

            System.out.println("Joined multicast group " + MULTICAST_GROUP +
                    " on interface " +
                    (networkInterface != null ? networkInterface.getName() : "default"));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}