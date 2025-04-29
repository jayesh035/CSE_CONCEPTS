package org.example.vertxDemo;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;

public class RouteFailureExample extends AbstractVerticle {

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new RouteFailureExample());
    }

    @Override
    public void start() {
        Router router = Router.router(vertx);

        // Route 1: GET /hello
        router.get("/hello").handler(ctx -> {
            ctx.response().end("Hello from Vert.x!");
        });

        // Route 2: POST /submit
        router.post("/submit").handler(ctx -> {
            ctx.response().end("Data submitted!");
        });

        // Route 3: Test route that throws an exception
        router.get("/error").handler(ctx -> {
            throw new RuntimeException("Simulated crash ");
        });

        // Global failure handler
        router.route().failureHandler(ctx -> {
            int statusCode = ctx.statusCode();
            Throwable failure = ctx.failure(); // this could be null

            if (statusCode == 404) {
                ctx.response()
                        .setStatusCode(404)
                        .end("Oops! Route not found.");
            } else if (failure != null) {
                ctx.response()
                        .setStatusCode(500)
                        .end(" Server error: " + failure.getMessage());
            } else {
                ctx.response()
                        .setStatusCode(statusCode)
                        .end("⚠ Something went wrong.");
            }
        });

        // Optional: Fallback route for anything unmatched
        router.route().last().handler(ctx -> {
            ctx.fail(404);  // Manually fail if no route matched
        });

        vertx.createHttpServer().requestHandler(router).listen(8888);
        System.out.println("🚀 Server running at http://localhost:8888");
    }
}
