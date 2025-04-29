//package org.example.vertxDemo;
//
//import io.vertx.core.Vertx;
//import io.vertx.core.AbstractVerticle;
//import io.vertx.core.http.Cookie;
//import io.vertx.ext.web.Router;
//public class CookieExampleVerticle extends AbstractVerticle {
//
//    @Override
//    public void start() {
//        Router router = Router.router(vertx);
//
//        // Enable cookie parsing
//       // router.route().handler(CookieHandler.create());
//
//        // Set a cookie
//        router.get("/set").handler(ctx -> {
//            Cookie cookie = Cookie.cookie("user", "Bob");
//            cookie.setMaxAge(3600); // 1 hour
//            cookie.setPath("/");
//            ctx.response().addCookie(cookie);
//            ctx.response().end("✅ Cookie set: user=Bob");
//        });
//
//        // Get a cookie
//        router.get("/get").handler(ctx -> {
//            Cookie cookie = ctx.cookie("user");
//            if (cookie != null) {
//                ctx.response().end("👋 Hello, " + cookie.getValue());
//            } else {
//                ctx.response().end("⚠️ No cookie found.");
//            }
//        });
//
//        // Delete a cookie
//        router.get("/delete").handler(ctx -> {
//            Cookie cookie = Cookie.cookie("user", "");
//            cookie.setMaxAge(0); // Expire it immediately
//            cookie.setPath("/");
//            ctx.response().addCookie(cookie);
//            ctx.response().end("🗑️ Cookie deleted.");
//        });
//
//        // Start the server
//        vertx.createHttpServer()
//                .requestHandler(router)
//                .listen(8888, http -> {
//                    if (http.succeeded()) {
//                        System.out.println("✅ Server started on http://localhost:8888");
//                    } else {
//                        System.out.println("❌ Failed to start server: " + http.cause().getMessage());
//                    }
//                });
//    }
//
//    public static void main(String[] args) {
//        Vertx vertx = Vertx.vertx();
//        vertx.deployVerticle(new CookieExampleVerticle());
//    }
//}
