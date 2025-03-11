import java.io.*;
import java.net.*;
import java.util.*;

public class LargeDatagramReceiver {
    public static void main(String[] args) {
        final int PORT = 9878;

        try {
            // Create socket
            DatagramSocket socket = new DatagramSocket(PORT);
            System.out.println("Large datagram receiver started on port " + PORT);

            // Buffer to store received chunks
            Map<Integer, byte[]> receivedChunks = new HashMap<>();
            int expectedTotalChunks = -1;

            while (true) {
                // Prepare to receive a chunk
                byte[] receiveBuffer = new byte[9000]; // Slightly larger than our chunk size
                DatagramPacket packet = new DatagramPacket(receiveBuffer, receiveBuffer.length);

                // Receive a chunk
                socket.receive(packet);

                // Extract the data
                byte[] data = Arrays.copyOfRange(packet.getData(), 0, packet.getLength());

                // Parse the header (format: "chunkNumber:totalChunks:data")
                String dataStr = new String(data);
                int firstColon = dataStr.indexOf(':');
                int secondColon = dataStr.indexOf(':', firstColon + 1);

                if (firstColon > 0 && secondColon > firstColon) {
                    int chunkNumber = Integer.parseInt(dataStr.substring(0, firstColon));
                    int totalChunks = Integer.parseInt(dataStr.substring(firstColon + 1, secondColon));

                    // Store total number of expected chunks
                    if (expectedTotalChunks == -1) {
                        expectedTotalChunks = totalChunks;
                    } else if (expectedTotalChunks != totalChunks) {
                        System.out.println("Warning: Inconsistent total chunk count");
                    }

                    // Extract actual data (after second colon)
                    byte[] chunkData = Arrays.copyOfRange(data, secondColon + 1, data.length);

                    // Store the chunk
                    receivedChunks.put(chunkNumber, chunkData);
                    System.out.println("Received chunk " + (chunkNumber+1) + "/" + totalChunks +
                            " (" + chunkData.length + " bytes)");

                    // Check if we have all chunks
                    if (receivedChunks.size() == expectedTotalChunks) {
                        // Calculate total message size
                        int totalSize = 0;
                        for (byte[] chunk : receivedChunks.values()) {
                            totalSize += chunk.length;
                        }

                        // Reassemble the complete message
                        byte[] completeMessage = new byte[totalSize];
                        int position = 0;

                        for (int i = 0; i < expectedTotalChunks; i++) {
                            byte[] chunk = receivedChunks.get(i);
                            if (chunk != null) {
                                System.arraycopy(chunk, 0, completeMessage, position, chunk.length);
                                position += chunk.length;
                            } else {
                                System.out.println("Error: Missing chunk " + i);
                                break;
                            }
                        }

                        // Process the complete message
                        System.out.println("Reassembled complete message, size: " +
                                completeMessage.length + " bytes");

                        // Reset for the next message
                        receivedChunks.clear();
                        expectedTotalChunks = -1;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}