package Client;
import Util.Constants;
import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Scanner;

public class ParkingClient
{
    public static void main(String[] args)
    {
        var scanner = new Scanner(System.in);

        var running = true;

        while (running)
        {
            System.out.println("\n=== Parking Lot System ===");
            System.out.println("1. Park Vehicle");
            System.out.println("2. Release Vehicle");
            System.out.println("3. Check Parking Lot Status");
            System.out.println("4. Check Availability of Spot");
            System.out.println("5. Exit");

            System.out.print("Select an option: ");
            String option = scanner.nextLine();

            //hashmap to store data given by user
            HashMap<String, String> data = new HashMap<>();

            switch (option)
            {
                case "1":

                    //Input Licence Number
                    System.out.print("Enter Vehicle License Number: ");
                    data.put("command", "PARK");
                    data.put("license", scanner.nextLine());

                    //Input Vehicle Type
                    System.out.print("Enter Vehicle Type (CAR, BIKE, TRUCK): ");
                    data.put("type", scanner.nextLine());

                    //Input level Number
                    System.out.print("Enter Level Number: ");
                    String level = scanner.nextLine();
                    data.put("level", level);

                    //Input Parking Spot Number
                    System.out.print("Enter Preferred Parking Spot Number: ");
                    String spotNumber = scanner.nextLine();
                    data.put("spotNumber", spotNumber);

                    sendCommandToServer(data);

                    break;

                case "2":

                    //Input SpotID
                    System.out.print("Enter Parking Spot ID to release: ");
                    data.put("command", "RELEASE");
                    data.put("spotID", scanner.nextLine());

                    sendCommandToServer(data);

                    break;

                case "3":

                    //Input Vehicle Type
                    System.out.print("Enter Vehicle Type (CAR, BIKE, TRUCK): ");
                    data.put("command", "STATUS");
                    data.put("type", scanner.nextLine());

                    sendCommandToServer(data);

                    break;

                case "4":

                    data.put("command","AVAILABILITY");

                    //Input Spot Number
                    System.out.print("Enter SpotNumber: ");
                    data.put("spotNumber",scanner.nextLine());

                    //Input Level Number
                    System.out.print("Enter level: ");
                    data.put("level",scanner.nextLine());

                    //Input Vehicle Type
                    System.out.print("Enter vehicle type: ");
                    data.put("type", scanner.nextLine());


                    sendCommandToServer(data);

                    break;

                case "5":

                    running = false;

                    System.out.println("Exiting client.");

                    break;

                default:

                    System.out.println("Invalid option, please try again.");
            }
        }

        scanner.close();
    }

    private static void sendCommandToServer(HashMap<String, String> data)
    {
        try (
                //create socket and connect to server
                var socket = new Socket(Constants.SERVER_ADDRESS, Constants.SERVER_PORT);

                var out = new ObjectOutputStream(socket.getOutputStream());

                var in = new ObjectInputStream(socket.getInputStream())
        )
        {
            //send data
            out.writeObject(data);

            out.flush();

            var response = in.readObject();

            //check if response from server is string
            if (response instanceof String)
            {
                System.out.println("Server Response: " + response);
            }
        }
        catch (IOException | ClassNotFoundException e)
        {
            System.err.println("Error communicating with server: " + e.getMessage());
        }
    }
}
