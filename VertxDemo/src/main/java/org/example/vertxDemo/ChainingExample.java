package org.example.vertxDemo;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;

public class ChainingExample
{
    public static void main(String[] args)
    {
        Vertx vertx = Vertx.vertx();

        Future<HttpServer> startServer = Future.future(promise ->
                vertx.createHttpServer()
                        .requestHandler(req -> req.response().end("Hello"))
                        .listen(8089, promise)
        );

        startServer
                .compose(server -> {
                    System.out.println("Server started on port " + server.actualPort());
                    return vertx.deployVerticle(new MyVerticle());
                })
                .onSuccess(deploymentId -> System.out.println("Verticle deployed: " + deploymentId))
                .onFailure(err -> System.err.println("Something failed: " + err.getMessage()));

        System.out.println("Starting server...");
    }

    static class MyVerticle extends AbstractVerticle
    {
        @Override
        public void start()
        {
            System.out.println("Verticle started!");
        }
    }
}
