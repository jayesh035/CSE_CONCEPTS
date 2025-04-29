package org.example.vertxDemo.Database;
import io.vertx.core.Vertx;
import io.vertx.pgclient.PgConnectOptions;
import io.vertx.sqlclient.Pool;
import io.vertx.sqlclient.PoolOptions;
import io.vertx.sqlclient.SqlConnection;
import org.example.vertxDemo.Utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DatabaseClient
{
    private static Pool pool;

    private static final Logger logger = LoggerFactory.getLogger(DatabaseClient.class);

    public static void initialize(Vertx vertx)
    {
        PgConnectOptions connectOptions = new PgConnectOptions()
                .setPort(Constants.DB_PORT)
                .setHost(Constants.DB_HOST)
                .setDatabase(Constants.DB_NAME)
                .setUser(Constants.DB_USERNAME)
                .setPassword(Constants.DB_PASSWORD);

        PoolOptions poolOptions = new PoolOptions().setMaxSize(5);

        pool = Pool.pool(vertx, connectOptions, poolOptions);

        // Try connecting to DB after Vert.x and Pool initialization
        pool.getConnection(res -> {
            if (res.succeeded()) {
                SqlConnection connection = res.result();
                connection.query("SELECT 1").execute(queryRes -> {
                    if (queryRes.succeeded()) {
                        logger.info(" Successfully connected to PostgreSQL!");
                    } else {
                        logger.error(" Query failed: {}", queryRes.cause().getMessage());
                    }
                    connection.close(); // Close connection after query
                });
            } else {
                logger.error(" Failed to connect to PostgreSQL: {}", res.cause().getMessage());
            }
        });
    }

    public static Pool getPool()
    {
        if (pool == null)
        {
            throw new IllegalStateException("DatabaseClient not initialized! Call initialize() first.");
        }
        return pool;
    }
}
