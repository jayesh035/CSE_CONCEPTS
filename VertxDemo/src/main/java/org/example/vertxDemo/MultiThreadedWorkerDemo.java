package org.example.vertxDemo;

import io.vertx.core.*;

public class MultiThreadedWorkerDemo extends AbstractVerticle
{
    @Override
    public void start(Promise<Void> startPromise) throws Exception
    {

        vertx.eventBus().consumer("worker.address", message -> {

            vertx.executeBlocking(promise -> {

                System.out.println("Started task on:"+ Thread.currentThread().getName());
                try
                {
                    Thread.sleep(2000);
                    promise.complete("Task Done by: "+Thread.currentThread().getName());
                }
                catch (InterruptedException e)
                {
                    promise.fail(e);
                }
            },
                    res->{
                if(res.succeeded())
                {
                    message.reply(res.result());
                }
                else
                {

                    message.fail(1,res.cause().getMessage());
                }
                    });



        });

        startPromise.complete();
    }

    public static void main(String[] args)
    {
        Vertx vertx= Vertx.vertx();

        vertx.deployVerticle(
                MultiThreadedWorkerDemo.class.getName()
                ,
                new DeploymentOptions().setInstances(40)
                .setThreadingModel(ThreadingModel.WORKER).setWorkerPoolSize(40),
                res->{
                    if(res.succeeded())
                    {
                        System.out.println("Multithreaded worker deployed");


                        for(int i=0;i<140;i++)
                        {
                            vertx.eventBus().request("worker.address",
                                    "Task-"+ i,result->{

                                if(result.succeeded())
                                {
                                    System.out.println("Replay: "+ result.result().body());
                                }
                                else
                                {
                                    System.out.println("Replay: "+ result.cause());
                                }
                            });
                        }
                    }
                });
    }
}
