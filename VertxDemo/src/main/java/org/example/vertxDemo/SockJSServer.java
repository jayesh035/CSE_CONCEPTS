//package org.example.vertxDemo;
//
//import io.vertx.core.AbstractVerticle;
//import io.vertx.ext.web.Router;
//import io.vertx.ext.web.handler.sockjs.SockJSHandler;
//import io.vertx.ext.web.handler.sockjs.BridgeOptions;
//import io.vertx.ext.web.handler.sockjs.PermittedOptions;
//
//public class SockJSServer extends AbstractVerticle {
//    @Override
//    public void start() {
//        Router router = Router.router(vertx);
//
//        // Setup SockJS bridge
//        BridgeOptions options = new BridgeOptions()
//                .addInboundPermitted(new PermittedOptions().setAddress("chat.to.server"))
//                .addOutboundPermitted(new PermittedOptions().setAddress("chat.to.client"));
//
//        SockJSHandler sockJSHandler = SockJSHandler.create(vertx);
//        sockJSHandler.bridge(options);
//
//        router.route("/eventbus/*").handler(sockJSHandler);
//
//        vertx.createHttpServer().requestHandler(router).listen(8888);
//
//        // Listen on the backend
//        vertx.eventBus().consumer("chat.to.server", msg -> {
//            System.out.println("Received: " + msg.body());
//
//            // Broadcast to all clients
//            vertx.eventBus().publish("chat.to.client", "Echo: " + msg.body());
//        });
//    }
//}
