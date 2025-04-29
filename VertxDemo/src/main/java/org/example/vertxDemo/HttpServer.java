package org.example.vertxDemo;

import io.vertx.core.AbstractVerticle;

public class HttpServer  extends AbstractVerticle {

    @Override
    public void start() throws Exception {

        vertx.createHttpServer().
                requestHandler(httpServerRequest ->
                {
//                    System.out.println("Request "+ httpServerRequest.body());
                    httpServerRequest.response().end("Hii from server");
                })
                .listen(8888).
                onSuccess(res->{
                    System.out.println("Server is started at port 8888");
                }).
                onFailure(res->{
                    System.out.println("Server is not able to start due to "+res.getCause());
                });

    }
}
