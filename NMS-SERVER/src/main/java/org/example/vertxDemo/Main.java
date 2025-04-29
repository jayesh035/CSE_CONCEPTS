package org.example.vertxDemo;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.auth.jwt.JWTAuth;
import io.vertx.ext.auth.PubSecKeyOptions;
import io.vertx.ext.auth.jwt.JWTAuthOptions;
import io.vertx.ext.web.handler.JWTAuthHandler;
import org.example.vertxDemo.Handlers.CredentialsHandler;
import org.example.vertxDemo.Handlers.DiscoveryHandler;
import org.example.vertxDemo.Routes.Routes;
import org.example.vertxDemo.Database.DatabaseClient;
import org.example.vertxDemo.Handlers.AuthHandler;
import org.example.vertxDemo.Utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main extends AbstractVerticle {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    @Override
    public void start() {
        // Initialize Database
        DatabaseClient.initialize(vertx);

        // Initialize JWTAuth
        JWTAuth jwtAuth = JWTAuth.create(vertx, new JWTAuthOptions()
                .addPubSecKey(new PubSecKeyOptions()
                        .setAlgorithm("HS256")
                        .setBuffer(Constants.JWT_KEY)));

        // Create AuthHandler instance
        AuthHandler authHandler = new AuthHandler(jwtAuth);

        // Create CredentialsHandler instance
        CredentialsHandler credentialsHandler = new CredentialsHandler(jwtAuth);

        //create DiscoveryHandler instance
        DiscoveryHandler discoveryHandler = new DiscoveryHandler(jwtAuth);

        // Create Router
        Router router = Router.router(vertx);

        // Add BodyHandler to parse request bodies
        router.route().handler(BodyHandler.create());

        // Set up routes
        //Route for Authentication module
        Routes.setupAuthRoutes(router, authHandler);

        //Route for Credentials module
        Routes.setupCredentialRoutes(router, credentialsHandler,JWTAuthHandler.create(jwtAuth));

        //Route for Discovery module
        Routes.setupDiscoveryRoutes(router, discoveryHandler, JWTAuthHandler.create(jwtAuth));


        // Start the HTTP server
        vertx.createHttpServer()
                .requestHandler(router)
                .listen(Constants.SERVER_PORT, result -> {
                    if (result.succeeded()) {
                        logger.info("Server started on port {}", Constants.SERVER_PORT);
                    } else {
                        logger.error("Failed to start server: {}", result.cause().getMessage());
                    }
                });


    }

    public static void main(String[] args)
    {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new Main());
    }
}