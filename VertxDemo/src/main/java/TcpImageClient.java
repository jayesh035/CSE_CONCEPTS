import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;

import java.nio.file.Files;
import java.nio.file.Paths;

public class TcpImageClient extends AbstractVerticle {

    @Override
    public void start() {
        vertx.createNetClient().connect(1234, "10.20.41.10", socketResult -> {
            if (socketResult.succeeded()) {
                System.out.println("Connected to server.");
                var socket = socketResult.result();

                try {
                    byte[] imageBytes = Files.readAllBytes(Paths.get("/home/jayesh/Screenshot from 2025-03-28 17-10-54.png"));
                    Buffer buffer = Buffer.buffer(imageBytes);

                    socket.write(buffer);
                    socket.end(); // Close connection after sending
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Failed to connect: " + socketResult.cause());
            }
        });
    }

    public static void main(String[] args) {
        Vertx.vertx().deployVerticle(new TcpImageClient());
    }
}
