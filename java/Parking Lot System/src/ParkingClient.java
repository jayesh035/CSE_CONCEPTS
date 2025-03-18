import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ParkingClient {
    private static final String SERVER_ADDRESS = "127.0.0.1"; // or "localhost"
    private static final int SERVER_PORT = 8080;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n=== Parking Lot System ===");
            System.out.println("1. Park Vehicle");
            System.out.println("2. Release Vehicle");
            System.out.println("3. Check Parking Lot Status");
            System.out.println("4. Exit");
            System.out.print("Select an option: ");
            String option = scanner.nextLine();

            switch (option) {
                case "1":
                    // PARK command: e.g., PARK ABC123 CAR
                    System.out.print("Enter Vehicle License Number: ");
                    String license = scanner.nextLine();
                    System.out.print("Enter Vehicle Type (CAR, MOTORCYCLE, TRUCK): ");
                    String type = scanner.nextLine();
                    String parkCommand = "PARK " + license + " " + type.toUpperCase();
                    sendCommandToServer(parkCommand);
                    break;

                case "2":
                    // RELEASE command: e.g., RELEASE S23
                    System.out.print("Enter Parking Spot ID to release: ");
                    String spotId = scanner.nextLine();
                    String releaseCommand = "RELEASE " + spotId;
                    sendCommandToServer(releaseCommand);
                    break;

                case "3":
                    // STATUS command to get real-time parking lot info
                    sendCommandToServer("STATUS");
                    break;

                case "4":
                    String updateCommand = "UPDATEFILE";
                    sendCommandToServer(updateCommand);
//                    System.out.println("Server response:);
                    running = false;
                    System.out.println("Exiting client.");

                    break;

                default:
                    System.out.println("Invalid option, please try again.");
            }
        }
        scanner.close();
    }


    private static void sendCommandToServer(String command) {
        try (
                Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true)
        ) {
            // Send the command to the server
            out.println(command);
            // Read and print the server's response
            String response;
            while((response= in.readLine())!=null)
            {
                if(response!=null)
                {
                    System.out.println(response);
                }

            }
//            System.out.println("Server: " + response);
        } catch (IOException e) {
            System.err.println("Error communicating with server: " + e.getMessage());
        }
    }
}
