package org.example.vertxDemo;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;

import java.io.FileOutputStream;

public class TcpImageServer extends AbstractVerticle {

    @Override
    public void start() {
        vertx.createNetServer().connectHandler(socket -> {
            System.out.println("Client connected!");

            Buffer totalBuffer = Buffer.buffer();

            socket.handler(buffer -> {
                totalBuffer.appendBuffer(buffer);
            });

            socket.closeHandler(v -> {
                System.out.println("Client disconnected. Saving image...");
                try (FileOutputStream out = new FileOutputStream("received_image.jpg")) {
                    out.write(totalBuffer.getBytes());
                    System.out.println("Image saved as received_image.jpg");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }).listen(1234, res -> {
            if (res.succeeded()) {
                System.out.println("Server is listening on port 1234");
            } else {
                System.out.println("Failed to start server: " + res.cause());
            }
        });
    }

    public static void main(String[] args) {
        Vertx.vertx().deployVerticle(new TcpImageServer());
    }
}
