package Util;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class ParkingSpotsCSVGenerator {
    private static final int TOTAL_SPOTS = 100; // Adjust as needed

    public static void main(String[] args) {
        String fileName = "parking_spots.csv";

        try (FileWriter writer = new FileWriter(fileName)) {
            // Write CSV Header
            writer.append("Spot ID,Status,VehicleLicenseNumber,VehicleType,Server.Classes.Level\n");

            // Corrected Array Initialization
            String[] vehicles = {"BIKE", "CAR", "TRUCK"}; // Each vehicle is a separate element
            Random random = new Random();

            for (int i = 1; i <= TOTAL_SPOTS; i++) {
                String spotId = "S" + i;
                String status = "true"; // Sample data
                int level = (i % 4) + 1;
                int randomNumber = random.nextInt(vehicles.length); // Generates 0, 1, or 2 safely

                writer.append(spotId)
                        .append(",")
                        .append(status)
                        .append(",").
                        append("").
                        append(",")
                        .append(vehicles[randomNumber]) // Correct indexing
                        .append(",")
                        .append(String.valueOf(level))
                        .append("\n");
            }

            System.out.println("CSV file generated: " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
