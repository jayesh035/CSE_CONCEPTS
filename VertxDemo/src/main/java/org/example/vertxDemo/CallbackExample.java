package org.example.vertxDemo;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;

public class CallbackExample {
    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();

        // Create an HTTP server and start it
        vertx.createHttpServer()
                .requestHandler(req -> req.response().end("Hello from Vert.x!"))
                .listen(8080, result -> {
                    // Callback handler for the listen operation
                    if (result.succeeded()) {
                        HttpServer server = result.result();
                        System.out.println("Server started on port " + server.actualPort());
                    } else {
                        System.err.println("Failed to start server: " + result.cause().getMessage());
                    }
                });

        // Keep the app running
        System.out.println("Starting server...");
    }
}