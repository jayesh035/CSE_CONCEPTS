package Server;

import Server.Classes.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server
{
    private static final Logger logger = LoggerFactory.getLogger(Server.class);

    private static final int PORT = 8080;

    private static final ParkingLot parkingLot = new ParkingLot();

    public static void main(String[] args)
    {
        //this allows server to handle multiple client
        var executor = Executors.newCachedThreadPool();

        try (ServerSocket serverSocket = new ServerSocket(PORT))
        {
            logger.info("Server started on port " + PORT);

            while (true)
            {
                var clientSocket = serverSocket.accept();

                logger.info("Client connected: " + clientSocket.getInetAddress());

                executor.submit(() -> handleClient(clientSocket));

            }

        } catch (IOException e)
        {
            logger.error("Server error: " + e.getMessage());
        }
    }

    private static void handleClient(Socket clientSocket) {
        try (

                //for sending data
                var out = new ObjectOutputStream(clientSocket.getOutputStream());

                //for receiving data
                var in = new ObjectInputStream(clientSocket.getInputStream())

        )
        {

            //read data from client
            Object receivedObject = in.readObject();


            if (receivedObject instanceof HashMap)
            {
                var data = (HashMap<String, String>) receivedObject;

                logger.info("Received data: " + data);

                //process data
                var response = processCommand(data);

                //send response to client
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

            logger.error("Error handling client: " + e.getMessage());

        }
        finally
        {
            try
            {
                //close connection
                clientSocket.close();

            }
            catch (IOException e)
            {

                logger.error("Error closing client socket: " + e.getMessage());

            }
        }
    }

    private static String processCommand(HashMap<String, String> data)
    {

        var command = data.get("command");

        if (command == null)
        {

            return "Invalid command.";

        }

        switch (command.toUpperCase())
        {
            case "PARK":

                if (data.containsKey("license") && data.containsKey("type")
                        && data.containsKey("level") && data.containsKey("spotNumber"))
                {
                    var license = data.get("license").trim();

                    var type = data.get("type").trim().toUpperCase();

                    var level = data.get("level").trim();

                    var spotNumber = data.get("spotNumber").trim();

                    return parkingLot.parkVehicle(level, spotNumber, type, license);

                }

                return "Invalid PARK command format. Required fields: license, type, level, spotNumber.";

            case "RELEASE":

                if (data.containsKey("spotID"))
                {

                    return parkingLot.releaseSpot(data.get("spotID").trim());

                }

                return "Invalid RELEASE command format. Required field: spotID.";

            case "STATUS":

                if (data.containsKey("type"))
                {

                    return parkingLot.getStatus(data.get("type").trim().toUpperCase());

                }

                return "Invalid STATUS command format. Required field: type.";

            case "AVAILABILITY":

                if (data.containsKey("spotNumber") &&
                        data.containsKey("level") &&
                        data.containsKey("type"))
                {

                    return parkingLot.checkAvailable(
                            data.get("level"),
                            data.get("spotNumber"),
                            data.get("type").trim().toUpperCase()
                    );

                }

                return "Invalid AVAILABILITY command format. Required fields: spotNumber, level, type.";

            default:

                return "Unknown command.";

        }
    }
}
