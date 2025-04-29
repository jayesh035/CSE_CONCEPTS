package org.example.vertxDemo;

import io.vertx.core.Vertx;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpServer;

public class PromiseExample {
    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();

        // Create a Promise
        Promise<HttpServer> promise = Promise.promise();

        // Start the HTTP server and tie it to the Promise
        vertx.createHttpServer()
                .requestHandler(req -> req.response().end("Hello from Vert.x!"))
                .listen(8080, promise);

        // Handle the Future result
        Future<HttpServer> future = promise.future();
        future
                .onSuccess(server -> System.out.println("Server started on port " + server.actualPort()))
                .onFailure(err -> System.err.println("Failed to start server: " + err.getMessage()));

        // Keep the app running
        System.out.println("Starting server...");
    }
}
