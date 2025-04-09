package org.example.vertxDemo;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;

public class Main extends AbstractVerticle
{

    public static void main(String[] args)
    {

//        System.out.println("Hello, World!");
        Vertx vertx1=Vertx.vertx();
//        vertx1.deployVerticle(new Main());

//        vertx1.deployVerticle(new TestVerticle());
//        vertx1.deployVerticle(new TestVerticle());

//        DeploymentOptions options=new DeploymentOptions().
//                setInstances(3);

//        vertx1.deployVerticle(TestVerticle.class.getName(),
//                new DeploymentOptions().setInstances(3));
//        System.out.println("hello");


        vertx1.deployVerticle(new SenderVerticle());
        vertx1.deployVerticle(new SenderVerticle());
        vertx1.deployVerticle(new ReceiverVerticle());

    }

    @Override
    public void start() throws Exception {
        vertx.createHttpServer()
                .requestHandler(req->{
                    req.response().end("Hello from vertx server");
                })
                .listen(8888,http->{
                    if(http.succeeded())
                    {
                        System.out.println("Server is started at port 8000");
                    }
                    else
                    {
                        System.out.println("Server is failed to start due to "+
                                http.cause());
                    }
                });
    }
}