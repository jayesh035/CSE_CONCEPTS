import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class UDPserver {

    public static void main(String[] args){
        final  int PORT =8081;

        try {
            DatagramSocket serverSocket=new DatagramSocket(PORT);
            System.out.println("UDP Server started on port " + PORT);

            byte[] receiveBuffer = new byte[1024]; // Buffer for incoming data

           while (true) {
                // Step 2: Create a packet to receive data
                DatagramPacket receivePacket =
                        new DatagramPacket(receiveBuffer, receiveBuffer.length);

                // Step 3: Wait for incoming data (blocks until received)
                serverSocket.receive(receivePacket);

                // Step 4: Process the received data
                String clientMessage = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println("Received from client: " + clientMessage);

                // Get client address and port from the packet
                InetAddress clientAddress = receivePacket.getAddress();
                int clientPort = receivePacket.getPort();

                // Step 5: Prepare response
                String response = "Server acknowledges: " + clientMessage;
                byte[] sendBuffer = response.getBytes();

                // Step 6: Create a packet for sending response
                DatagramPacket sendPacket = new DatagramPacket(
                        sendBuffer,
                        sendBuffer.length,
                        clientAddress,
                        clientPort
                );

                // Step 7: Send the response
                serverSocket.send(sendPacket);
                System.out.println("Response sent to client");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}