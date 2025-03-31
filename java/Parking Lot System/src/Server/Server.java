package Server;
import Server.Classes.ParkingLot;
import Util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.Executors;

public class Server
{
    private static final Logger logger = LoggerFactory.getLogger(Server.class);

    private static final ParkingLot parkingLot = new ParkingLot();

    public static void main(String[] args) {

        //initialize executor service with same threads
        var executor = Executors.newFixedThreadPool(8);

        try (ServerSocket serverSocket = new ServerSocket(Constants.SERVER_PORT))
        {
            logger.info("Server started on port " + Constants.SERVER_PORT);

            while (!Thread.currentThread().isInterrupted())
            {
                var clientSocket = serverSocket.accept();

                logger.info("Client connected: {}", clientSocket.getInetAddress());

                executor.submit(() -> handleClient(clientSocket));
            }
        }
        catch (IOException e)
        {
            logger.error("Server error: {}", e.getMessage());
        }
    }

    private static void handleClient(Socket clientSocket) {
        try (
                var out = new ObjectOutputStream(clientSocket.getOutputStream());

                var in = new ObjectInputStream(clientSocket.getInputStream())
        ) {
            var receivedObject = in.readObject();

            if (receivedObject instanceof HashMap)
            {
                var data = (HashMap<String, String>) receivedObject;

                logger.info("Received data: {}", data);

                var response = processCommand(data);

                out.writeObject(response);

                out.flush();
            }
            else
            {
                out.writeObject("Invalid data received");

                out.flush();
            }
        }
        catch (IOException | ClassNotFoundException e)
        {
            logger.error("Error handling client: {}", e.getMessage());
        }
        finally {

            try
            {
                clientSocket.close();
            }
            catch (IOException e)
            {
                logger.error("Error closing client socket: {}", e.getMessage());
            }
        }
    }

    private static String processCommand(HashMap<String, String> data)
    {
        if (data == null || !data.containsKey("command"))
        {
            return "Invalid command.";
        }

        var command = data.get("command").trim().toUpperCase();

        return switch (command)
        {
            case "PARK" ->
            {
                if (data.containsKey("license") && data.containsKey("type") &&
                        data.containsKey("level") && data.containsKey("spotNumber"))
                {
                    String license = data.getOrDefault("license", "").trim();

                    String type = data.getOrDefault("type", "").trim().toUpperCase();

                    String level = data.getOrDefault("level", "").trim();

                    String spotNumber = data.getOrDefault("spotNumber", "").trim();

                    yield parkingLot.parkVehicle(level, spotNumber, type, license);
                }

                yield "Invalid PARK command format. Required fields: license, type, level, spotNumber.";
            }

            case "RELEASE" ->
            {
                if (data.containsKey("spotID"))
                {
                    yield parkingLot.releaseSpot(data.get("spotID").trim());
                }

                yield "Invalid RELEASE command format. Required field: spotID.";
            }

            case "STATUS" ->
            {
                if (data.containsKey("type"))
                {
                    yield parkingLot.getStatus(data.get("type").trim().toUpperCase());
                }

                yield "Invalid STATUS command format. Required field: type.";
            }

            case "AVAILABILITY" ->
            {
                if (data.containsKey("spotNumber") &&
                        data.containsKey("level") &&
                        data.containsKey("type"))
                {
                    yield parkingLot.checkAvailable(
                            data.get("level"),
                            data.get("spotNumber"),
                            data.get("type").trim().toUpperCase()
                    );
                }

                yield "Invalid AVAILABILITY command format. Required fields: spotNumber, level, type.";
            }

            default -> "Unknown command.";
        };
    }
}
