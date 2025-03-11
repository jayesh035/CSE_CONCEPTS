import java.io.IOException;
import java.net.*;

public class UDPclient {

    public static void main(String[] args) {


        try {
            DatagramSocket clientSocket=new DatagramSocket();
            InetAddress serverIP=InetAddress.getByName("localhost");
           final int PORT=8081;


           String message="Hello! from client";

           byte[] data=message.getBytes();
            //created packed that is sent to server
            DatagramPacket datagramPacket=
                    new DatagramPacket(
                            data,
                            data.length,
                            serverIP,
                            PORT);
            //send the packet

            clientSocket.send(datagramPacket);

            byte[] recieveBuffer=new byte[1024];

//            clientSocket.getRemoteSocketAddress()
            DatagramPacket recievePacket=new DatagramPacket(recieveBuffer,
                    recieveBuffer.length,
                    serverIP,
                    PORT);

            clientSocket.receive(recievePacket);

            String serverResponse = new String(recievePacket.getData(), 0, recievePacket.getLength());
            System.out.println("Response from server: " + serverResponse);

            // Step 9: Close the socket
            clientSocket.close();



        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
