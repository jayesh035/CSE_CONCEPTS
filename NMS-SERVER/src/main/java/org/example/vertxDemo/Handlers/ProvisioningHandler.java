package org.example.vertxDemo.Handlers;
import io.vertx.core.Vertx;
import io.vertx.ext.auth.jwt.JWTAuth;
import io.vertx.ext.web.RoutingContext;
import io.vertx.core.json.JsonObject;
import io.vertx.sqlclient.Tuple;
import org.example.vertxDemo.Database.DatabaseClient;

public class ProvisioningHandler
{
    private final JWTAuth jwtAuth;
    private final Vertx vertx;

    public ProvisioningHandler(JWTAuth jwtAuth, Vertx vertx)
    {
        this.jwtAuth = jwtAuth;
        this.vertx = vertx;
    }

    public void handleProvision(RoutingContext context)
    {
        JsonObject body = context.getBodyAsJson();

        // Check if required fields are present
        if (body == null || !body.containsKey("ip_address") || !body.containsKey("discovary_id"))
        {
            context.response().setStatusCode(400)
                    .end(new JsonObject().put("error", "Missing ip_address or discovary_id").encode());
            return;
        }

        String ipAddress = body.getString("ip_address");
        Long discovaryId = body.getLong("discovary_id");

        // Query to fetch the discovery result based on ip_address and discovary_id
        String query = "SELECT * FROM discovery_result WHERE ip_address = $1 AND discovary_id = $2 AND status = true";

        DatabaseClient.
                getPool().
                preparedQuery(query).
                execute(Tuple.of(ipAddress, discovaryId), ar ->
        {
            if (ar.succeeded())
            {
                var resultSet = ar.result();
                if (resultSet.rowCount() == 0)
                {
                    context.response().setStatusCode(404)
                            .end(new JsonObject().put("error", "No discovery result found").encode());
                    return;
                }

                var row = resultSet.iterator().next();
                int credentialId = row.getInteger("credential_id");

                // Fetch SNMP metrics (directly associated with credential_id or some other logic to define metrics)
                JsonObject metrics = new JsonObject();
                // Example: Adding some metrics directly
                metrics.put("sysName", 60);        // sysName with a polling interval of 60 seconds
                metrics.put("sysLocation", 120);   // sysLocation with a polling interval of 120 seconds
                metrics.put("sysDescr", 90);       // sysDescr with a polling interval of 90 seconds

                // Check if the provision record already exists for this ip_address and discovary_id
                String checkProvisionQuery = "SELECT * FROM provision WHERE ip_address = $1 AND discovary_id = $2";

                DatabaseClient.
                        getPool().
                        preparedQuery(checkProvisionQuery).
                        execute(Tuple.of(ipAddress, discovaryId), checkRes ->
                {
                    if (checkRes.succeeded())
                    {
                        var checkResultSet = checkRes.result();

                        if (checkResultSet.rowCount() > 0)
                        {
//                            // Record exists, perform an update instead of insert
//                            String updateQuery = "UPDATE provision SET credential_id = $1, metrics = $2::jsonb " +
//                                    "WHERE ip_address = $3 AND discovary_id = $4";
//
//                            DatabaseClient.getPool().preparedQuery(updateQuery).execute(
//                                    Tuple.of(credentialId, metrics.encode(), ipAddress, discovaryId),
//                                    updateRes -> {
//                                        if (updateRes.succeeded())
//                                        {
                                            context.response()
                                                    .putHeader("Content-Type", "application/json")
                                                    .end(new JsonObject().put("message", "This Ip is already Provisoned").encode());
//                                        }
//                                        else
//                                        {
//                                            context.response().setStatusCode(500)
//                                                    .end(new JsonObject().put("error", "Failed to update provision").encode());
//                                        }
//                                    });
                        }
                        else
                        {
                            // No record exists, insert a new provision record
                            String insertQuery = "INSERT INTO provision (discovary_id, ip_address, credential_id, metrics) " +
                                    "VALUES ($1, $2, $3, $4::jsonb) RETURNING provision_id";

                            DatabaseClient.
                                    getPool().
                                    preparedQuery(insertQuery).
                                    execute(Tuple.of(discovaryId, ipAddress, credentialId, metrics.encode()),
                                    insertRes ->
                                    {
                                        if (insertRes.succeeded())
                                        {
                                            long provisionId = insertRes.result().iterator().next().getLong("provision_id");

                                            JsonObject response = new JsonObject()
                                                    .put("provisioned", true)
                                                    .put("provision_id", provisionId)
                                                    .put("discovary_id", discovaryId)
                                                    .put("ip_address", ipAddress)
                                                    .put("credential_id", credentialId)
                                                    .put("metrics", metrics);

                                            context.response()
                                                    .putHeader("Content-Type", "application/json")
                                                    .end(response.encode());
                                        }
                                        else
                                        {
                                            context.response().setStatusCode(500)
                                                    .end(new JsonObject().put("error", "Failed to insert provision").encode());
                                        }
                                    });
                        }
                    }
                    else
                    {
                        context.response().setStatusCode(500)
                                .end(new JsonObject().put("error", "Database error").encode());
                    }
                });

            }
            else
            {
                context.response().setStatusCode(500)
                        .end(new JsonObject().put("error", "Database error").encode());
            }
        });
    }





    public void handleGetProvisionStatus(RoutingContext context)
    {
        String deviceId = context.pathParam("deviceId");

        // TODO: Query provisioning status from DB

        JsonObject response = new JsonObject()
                .put("deviceId", deviceId)
                .put("status", "Provisioned"); // Replace with actual status

        context.response()
                .putHeader("Content-Type", "application/json")
                .end(response.encode());
    }
}
