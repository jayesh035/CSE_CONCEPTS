import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class LocalPortScanner {


    public static void main(String[] args)  {




//int port=1;
//        int port = 1;
//        while (port <= 65535) {
//            try (ServerSocket server = new ServerSocket(port)) {
//                // Port is free, so do nothing
////
//            } catch (IOException e) {
//                // Port is in use
////                server.close();
//                System.out.println("Port " + port + " is IN USE (Listening).");
//            }
//            port++;
//        }

        for (int port = 1; port <= 65535; port++) {
            try (Socket socket = new Socket("localhost", port)) {
                System.out.println("Port " + port + " is OPEN (Listening).");
            } catch (IOException e) {
                // Port is closed or not responding, do nothing
            }
        }





    }
}
