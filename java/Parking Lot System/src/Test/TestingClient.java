package Test;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;

public class TestingClient
{
    static void sendData(HashMap<String, String> data) {
        try (
                var socket = new Socket("localhost", 8080);
                var out = new ObjectOutputStream(socket.getOutputStream());
                var in = new ObjectInputStream(socket.getInputStream())
        ) {
            out.reset();  // Reset the stream before sending
            out.writeObject(data);
            out.flush();

            var response = in.readObject();
            if (response instanceof String) {
                System.out.println("Server Response: " + response);
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error communicating with server: " + e.getMessage());
        }
    }

    public static void main(String[] args) throws InterruptedException {
        HashMap<String, String> data = new HashMap<>();
        HashMap<String, String> data1 = new HashMap<>();
        HashMap<String, String> data3 = new HashMap<>();
        HashMap<String, String> data2 = new HashMap<>();

        data.put("command", "PARK");
        data.put("type", "CAR");
        data.put("license", "asd");
        data.put("spotNumber", "15");
        data.put("level", "4");

        data1.put("command", "PARK");
        data1.put("type", "CAR");
        data1.put("license", "asds");
        data1.put("spotNumber", "14");
        data1.put("level", "4");

        data3.put("command", "PARK");
        data3.put("type", "CAR");
        data3.put("license", "asddd");
        data3.put("spotNumber", "12");
        data3.put("level", "4");

        data2.put("command", "RELEASE");
        data2.put("spotID", "15SL4");

        Thread t1 = new Thread(() -> sendData(data));
        Thread t3 = new Thread(() -> sendData(data1));

        Thread t2 = new Thread(() -> sendData(data2));

        t1.start();
        t2.start();
        t3.start();
        // Ensure threads finish execution before main exits
//        t1.join();
//        t2.join();
    }
}
