package org.example.vertxDemo;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServer;

class MyVerticle extends AbstractVerticle {

    @Override
    public void start() {
        HttpServer server = vertx.createHttpServer();

        server.requestHandler(req -> {
            // Non-blocking file read
            Future<Buffer> fileFuture = vertx.fileSystem().readFile("/home/jayesh/Desktop/temp1.txt");

            fileFuture
                    .onFailure(err -> {
                        System.err.println("Failed to read data.txt: " + err.getMessage());
                        // Recover with default content
                        req.response().end("Default content");
                    })
                    .onSuccess(content -> {
                        // Blocking operation (e.g., process content into a PDF)
                        vertx.executeBlocking(promise -> {
                            try {
                                String pdf = "Generated PDF from: " + content.toString(); // Simulate blocking
                                Thread.sleep(1000); // Simulate slow work
                                promise.complete(pdf);
                            } catch (Exception e) {
                                promise.fail(e);
                            }
                        }, result -> {
                            if (result.succeeded()) {
                                req.response().end(result.result().toString());
                            } else {
                                System.err.println("PDF generation failed: " + result.cause());
                                req.response().setStatusCode(500).end("Error generating PDF");
                            }
                        });
                    });
        });

        server.listen(8080, res -> {
            if (res.succeeded()) {
                System.out.println("Server running on port 8080");
            } else {
                System.err.println("Failed to start server: " + res.cause());
            }
        });
    }


}
public class ExecuteBlocking
{
    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new MyVerticle());
    }
}
