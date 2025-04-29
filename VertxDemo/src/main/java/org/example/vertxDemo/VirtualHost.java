package org.example.vertxDemo;

import io.vertx.core.AbstractVerticle;
import io.vertx.ext.web.Router;

public class VirtualHost extends AbstractVerticle {
    @Override
    public void start() throws Exception
    {
        Router mainRouter = Router.router(vertx);

        // Create separate routers for each domain
        Router shopRouter = Router.router(vertx);
        Router blogRouter = Router.router(vertx);

// Set up routes for the shop
        shopRouter.get("/").handler(ctx -> {
            ctx.response().end("Welcome to My Shop!");
        });

// Set up routes for the blog
        blogRouter.get("/").handler(ctx -> {
            ctx.response().end("Welcome to My Blog!");
        });

// Connect domain-specific routers to the main router using virtualHost
        mainRouter.route().virtualHost("myshop.com").subRouter(shopRouter);
        mainRouter.route().virtualHost("myblog.com").subRouter(blogRouter);

// Start the HTTP server with the main router
        vertx.createHttpServer()
                .requestHandler(mainRouter)
                .listen(8080);
    }


    public static void main(String[] args) {
        io.vertx.core.Vertx vertx = io.vertx.core.Vertx.vertx();
        vertx.deployVerticle(new VirtualHost());
        System.out.println("Server started on port 8080");
    }


}
