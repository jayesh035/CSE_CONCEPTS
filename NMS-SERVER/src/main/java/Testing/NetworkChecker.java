package Testing;
import java.io.IOException;

public class NetworkChecker {

    // Method to check if IP is reachable using ping
    public static boolean isHostReachable(String ipAddress) {
        try {
            Process process = Runtime.getRuntime().exec("ping -c 1 " + ipAddress);
            int returnVal = process.waitFor();
            return returnVal == 0;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to check if a port is open using netcat (nc)
    public static boolean isPortOpen(String ipAddress, int port) {
        try {
            String command = String.format("nc -zvu %s %d", ipAddress, port);
            Process process = Runtime.getRuntime().exec(command);
            int returnVal = process.waitFor();
            return returnVal == 0;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Main method for testing
    public static void main(String[] args) {
        String ip = "10.20.41.66"; // Replace with your target IP
        int port = 161;            // Replace with the port to check

        boolean reachable = isHostReachable(ip);
        boolean portOpen = isPortOpen(ip, port);

        System.out.println("Ping " + ip + ": " + (reachable ? "Reachable" : "Unreachable"));
        System.out.println("Port " + port + " on " + ip + ": " + (portOpen ? "Open" : "Closed"));
    }
}

