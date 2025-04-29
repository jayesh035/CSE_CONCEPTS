package org.example.vertxDemo.Handlers;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.jwt.JWTAuth;
import io.vertx.ext.web.RoutingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DiscoveryHandler {

    private final JWTAuth jwtAuth;
    private static final Logger logger = LoggerFactory.getLogger(DiscoveryHandler.class);

    public DiscoveryHandler(JWTAuth jwtAuth) {
        this.jwtAuth = jwtAuth;
    }

    public void handleCreate(RoutingContext context) {
        JsonObject body = context.getBodyAsJson();
        logger.info("Creating new discovery: {}", body.encode());

        // TODO: Validate fields: discoveryName, ipAddress, port, credentials_ids (array)
        // TODO: Insert into DB (discovary table and discovary_credentials junction)

        context.response().setStatusCode(201).end("Discovery created.");
    }

    public void handleRead(RoutingContext context) {
        String id = context.pathParam("id");

        // TODO: Query discovery and its credentials
        context.response().end("Discovery details for ID: " + id);
    }

    public void handleList(RoutingContext context) {
        // TODO: Return list of all discoveries
        context.response().end("List of discoveries.");
    }

    public void handleUpdate(RoutingContext context) {
        String id = context.pathParam("id");
        JsonObject body = context.getBodyAsJson();

        // TODO: Update the discovery entry
        context.response().end("Updated discovery " + id);
    }

    public void handleDelete(RoutingContext context) {
        String id = context.pathParam("id");

        // TODO: Delete from discovary and discovary_credentials
        context.response().end("Deleted discovery " + id);
    }

    public void handleRunDiscovery(RoutingContext context) {
        String id = context.pathParam("id");

        logger.info("Running discovery with ID: {}", id);

        // TODO:
        // 1. Fetch discovery info by ID
        // 2. Fetch credentials for this discovery
        // 3. For each credential, attempt SNMP ping / login / etc.
        // 4. Insert results into discovary_result table

        context.response().end("Discovery " + id + " started.");
    }
}
