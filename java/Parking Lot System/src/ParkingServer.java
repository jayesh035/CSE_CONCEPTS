import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ParkingServer {
    private static final Logger logger = LoggerFactory.getLogger(ParkingServer.class);
    private static final int PORT = 8080;
    private static ParkingLot parkingLot = new ParkingLot();

    public static void main(String[] args) {
        // Use a thread pool to handle multiple client connections concurrently.
        ExecutorService executor = Executors.newFixedThreadPool(10);
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            logger.info("Server started on port " + PORT);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                logger.info("Client connected: " + clientSocket.getInetAddress());
                executor.submit(() -> handleClient(clientSocket));
            }
        } catch (IOException e) {
            logger.error("Server error: " + e.getMessage());
        }
    }

    private static void handleClient(Socket clientSocket) {
        try (
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true)
        ) {
            // Read one command from the client
            String commandLine = reader.readLine();
            if (commandLine == null) {
                writer.println("No command received.");
                return;
            }
            logger.info("Received command: " + commandLine);
            String response = processCommand(commandLine);
            writer.println(response);
        } catch (IOException e) {
            logger.error("Error handling client: " + e.getMessage());
        } finally {
            try {


                clientSocket.close();
            } catch (IOException e) {
                logger.error("Error closing client socket: " + e.getMessage());
            }
        }
    }

    // Processes the incoming command and returns the appropriate response.
    private static String processCommand(String commandLine) {
        String[] parts = commandLine.split(" ");
        if (parts.length == 0) {
            return "Invalid command.";
        }
        String command = parts[0].toUpperCase();
        switch (command) {
            case "PARK":
                if (parts.length == 3) {
                    String license = parts[1];
                    String type = parts[2];
                    Vehicle vehicle = new Vehicle(license, type);
                    return parkingLot.parkVehicle(vehicle);
                } else {
                    return "Invalid PARK command format. Use: PARK <license> <type>";
                }
            case "RELEASE":
                if (parts.length == 2) {
                    String spotId = parts[1];
                    return parkingLot.releaseSpot(spotId);
                } else {
                    return "Invalid RELEASE command format. Use: RELEASE <spotId>";
                }
            case "STATUS":
                return parkingLot.getStatus();
            case "UPDATEFILE":
                return updateFile();
            default:
                return "Unknown command.";
        }




    }


    public static String updateFile() {
        String fileName = "parking_spots.csv";
        try (FileWriter writer = new FileWriter(fileName)) {
            // Write header (adjust columns as needed)
            writer.write("Spot ID,Status,VehicleLicenseNumber,VehicleType,Level\n");
            for (Level level : parkingLot.getLevels()) {
                for (ParkingSpot spot : level.getSpots().values()) {
                    StringBuilder line = new StringBuilder();
                    line.append(spot.getId()).append(",");
                    line.append(spot.isAvailable()).append(",");

                    if (spot.getVehicle() != null) {
                        line.append(spot.getVehicle().getLicenseNumber()).append(",");
                        line.append(spot.getVehicle().getType());
                    } else {
                        // When the spot is released (vehicle is null), leave these fields empty
                        line.append(",");  // Blank for license number
                        line.append("");   // Blank for vehicle type
                    }
                    line.append(",").append(level.getLevelNumber()).append("\n");
                    writer.write(line.toString());
                }
            }
            return "File updated successfully.";
        } catch (IOException e) {
            return "Error updating file: " + e.getMessage();
        }
    }

}
