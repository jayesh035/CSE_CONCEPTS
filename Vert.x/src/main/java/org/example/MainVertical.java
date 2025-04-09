package org.example;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.MultiMap;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;

public class MainVertical extends AbstractVerticle {

    @Override
    public void start(Promise<Void>startPromise) throws Exception {


        Router router=Router.router(vertx);

        router.route().handler(context ->{


            String address =  context.request().connection().remoteAddress().toString();

            MultiMap queryParams= context.queryParams();
            String name= queryParams.contains("name")? queryParams.get("name") :"unknown";

            context.json(
              new JsonObject()
                      .put("name",name)
                      .put("address",address)
                      .put("message","Hello "+name + " connected from" + address));
        });


        vertx.createHttpServer()
                .requestHandler(router)
                .listen(8888)
                .onSuccess(server->{

                    System.out.println("Server is started on port "+ server.actualPort());
                    startPromise.complete();
                })
                .onFailure(throwable->{
                    throwable.printStackTrace();
                    startPromise.fail(throwable);
                });


    }


}
