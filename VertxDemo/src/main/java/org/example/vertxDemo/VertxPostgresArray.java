package org.example.vertxDemo;
import io.vertx.core.Vertx;
import io.vertx.pgclient.PgConnectOptions;
import io.vertx.sqlclient.*;
import io.vertx.pgclient.PgPool;
import io.vertx.sqlclient.PoolOptions;

public class VertxPostgresArray {
    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();

        // Step 1: Connect to PostgreSQL
        PgConnectOptions connectOptions = new PgConnectOptions()
                .setPort(5432)
                .setHost("localhost")
                .setDatabase("TestDB")
                .setUser("postgres")
                .setPassword("jayesh");

        PoolOptions poolOptions = new PoolOptions().setMaxSize(5);
        PgPool client = PgPool.pool(vertx, connectOptions, poolOptions);

        // Step 2: Create table
        String createTableQuery = "CREATE TABLE IF NOT EXISTS example (" +
                "id SERIAL PRIMARY KEY, " +
                "numbers INTEGER[]);";

        client.query(createTableQuery).execute(ar -> {
            if (ar.succeeded()) {
                System.out.println("✅ Table created.");

                // Step 3: Insert data
                Integer[] numbers = new Integer[]{1, 2, 3, 4};

                client.preparedQuery("INSERT INTO example (numbers) VALUES ($1)")
                        .execute(Tuple.of(numbers), insertAr -> {
                            if (insertAr.succeeded()) {
                                System.out.println("✅ Data inserted successfully!");
                            } else {
                                System.out.println("❌ Insert failed:");
                                insertAr.cause().printStackTrace();
                            }

                            client.close();
                            vertx.close();
                        });

            } else {
                System.out.println("❌ Table creation failed:");
                ar.cause().printStackTrace();
                client.close();
                vertx.close();
            }
        });
    }
}
