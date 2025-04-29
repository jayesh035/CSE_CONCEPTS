package org.example.vertxDemo;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CorsHandler;

import io.vertx.ext.web.FileUpload;
import io.vertx.core.json.JsonObject;
import io.vertx.core.buffer.Buffer;

import java.util.List;
import java.util.Set;

public class AllBodyHandlerVerticle extends AbstractVerticle {

    @Override
    public void start() {
        Router router = Router.router(vertx);

        // Enable CORS for all routes
        router.route().handler(CorsHandler.create("*")
                .allowedMethod(io.vertx.core.http.HttpMethod.GET)
                .allowedMethod(io.vertx.core.http.HttpMethod.POST)
                .allowedMethod(io.vertx.core.http.HttpMethod.PUT)
                .allowedMethod(io.vertx.core.http.HttpMethod.DELETE)
                .allowedHeader("Content-Type")
                .allowedHeader("Authorization"));

        // Enable BodyHandler globally
        router.route().handler(BodyHandler.create().setUploadsDirectory("uploads"));

        // JSON endpoint
        router.post("/json").handler(ctx -> {
            JsonObject json = ctx.getBodyAsJson();
            String name = json.getString("name");
            int age = json.getInteger("age", 0);
            ctx.response().end("JSON Received: " + name + " - Age: " + age);
        });

        // Form-data endpoint
        router.post("/form").handler(ctx -> {
            String username = ctx.request().getFormAttribute("username");
            String password = ctx.request().getFormAttribute("password");
            ctx.response().end("Form Received: " + username + ", Password: " + password);
        });

        // File upload endpoint
        router.post("/upload").handler(ctx -> {
            List<FileUpload> uploads = ctx.fileUploads();
            StringBuilder response = new StringBuilder("Files Received:\n");

            for (FileUpload file : uploads) {
                response.append("Original: ").append(file.fileName())
                        .append(" | Saved as: ").append(file.uploadedFileName())
                        .append("\n");
            }

            ctx.response().end(response.toString());
        });

        // Raw text endpoint
        router.post("/text").handler(ctx -> {
            Buffer raw = ctx.getBody();
            ctx.response().end("Raw text received: " + raw.toString());
        });

        // Serve the HTML page
        router.get("/").handler(ctx ->
                ctx.response()
                        .putHeader("Content-Type", "text/html")
                        .sendFile("web/index.html")
        );

        vertx.createHttpServer().requestHandler(router).listen(8888);
    }

    public static void main(String[] args) {
        Vertx.vertx().deployVerticle(new AllBodyHandlerVerticle());
    }
}
