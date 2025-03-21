package Test;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class TestingClient {


    static  void sendData(HashMap<String,String> data){

        try (
                var socket = new Socket("localhost", 8080);
                var out = new ObjectOutputStream(socket.getOutputStream());
                var in = new ObjectInputStream(socket.getInputStream())
        )
        {
            out.writeObject(data);
            out.flush();

            var response = in.readObject();
            if (response instanceof String)
            {
                System.out.println("Server Response: " + response);
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error communicating with server: " + e.getMessage());
        }

    }

    public static void main(String[] args) throws InterruptedException {



        HashMap<String,String>data=new HashMap<>();
        HashMap<String,String>data1=new HashMap<>();
        HashMap<String,String>data2=new HashMap<>();

        data.put("command", "PARK");
        data.put("type","CAR");
        data.put("license","asd");
        data.put("spotNumber","12");
        data.put("level","1");

        data1.put("command", "PARK");
        data1.put("type","CAR");
        data1.put("license","asdf");
        data1.put("spotNumber","3");
        data1.put("level","1");

        data2.put("command", "RELEASE");
        data2.put("spotID", "7SL2");


            Thread t1=new Thread(()->{
                try {
                    Thread.sleep(100000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                sendData(data);
            });

            Thread t2=new Thread(()->{

                try {
                    Thread.sleep(100000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                sendData(data1);
            });
//            Thread t3=new Thread(()->{
//                sendData(data1);
//            });
            t1.start();
            Thread.sleep(50);
            t2.start();

    }
}
