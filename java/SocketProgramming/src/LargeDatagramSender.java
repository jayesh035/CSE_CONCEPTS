import java.io.*;
import java.net.*;

public class LargeDatagramSender {
    public static void main(String[] args) {
        final int SERVER_PORT = 9878;

        try {
            // Create socket
            DatagramSocket socket = new DatagramSocket();
            InetAddress serverAddress = InetAddress.getByName("localhost");

            // Create a large message (64KB)
            StringBuilder messageBuilder = new StringBuilder();
            for (int i = 0; i < 65536; i++) {
                messageBuilder.append((char) ('A' + (i % 26)));
            }
            String largeMessage = messageBuilder.toString();

            // Get the message bytes
            byte[] messageBytes = largeMessage.getBytes();
            System.out.println("Message size: " + messageBytes.length + " bytes");

            // Define a reasonable chunk size (8KB)
            final int CHUNK_SIZE = 8192;

            // Split the message into chunks to avoid excessive fragmentation
            int totalChunks = (int) Math.ceil((double) messageBytes.length / CHUNK_SIZE);
            System.out.println("Sending message in " + totalChunks + " chunks");

            for (int i = 0; i < totalChunks; i++) {
                // Calculate chunk boundaries
                int startIndex = i * CHUNK_SIZE;
                int endIndex = Math.min(startIndex + CHUNK_SIZE, messageBytes.length);
                int chunkLength = endIndex - startIndex;

                // Create buffer for this chunk (include chunk number and total chunks)
                byte[] chunkHeader = (i + ":" + totalChunks + ":").getBytes();
                byte[] sendData = new byte[chunkHeader.length + chunkLength];

                // Copy header
                System.arraycopy(chunkHeader, 0, sendData, 0, chunkHeader.length);

                // Copy chunk data
                System.arraycopy(messageBytes, startIndex, sendData, chunkHeader.length, chunkLength);

                // Create and send packet
                DatagramPacket packet = new DatagramPacket(
                        sendData,
                        sendData.length,
                        serverAddress,
                        SERVER_PORT
                );

                socket.send(packet);
                System.out.println("Sent chunk " + (i+1) + "/" + totalChunks +
                        " (" + sendData.length + " bytes)");

                // Small delay between chunks to reduce network congestion
                Thread.sleep(50);


                DatagramPacket recieve = new DatagramPacket(
                        sendData,
                        sendData.length,
                        serverAddress,
                        SERVER_PORT
                );
                socket.receive(recieve);
                String recievedData=new String(recieve.getData(),recieve.getOffset(), recieve.getLength());
                System.out.println(recievedData);


            }

            socket.close();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}