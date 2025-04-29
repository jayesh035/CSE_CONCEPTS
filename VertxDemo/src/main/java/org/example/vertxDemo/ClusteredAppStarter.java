package org.example.vertxDemo;

import com.hazelcast.config.Config;
import com.hazelcast.config.JoinConfig;
import com.hazelcast.config.TcpIpConfig;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.spi.cluster.hazelcast.HazelcastClusterManager;

public class ClusteredAppStarter {

    public static void main(String[] args) {
        Config hazelcastConfig = new Config();

//        hazelcastConfig.getNetworkConfig().setPublicAddress("10.20.41.66"); // <--- Add this
        JoinConfig join = hazelcastConfig.getNetworkConfig().getJoin();
        join.getMulticastConfig().setEnabled(false);
        TcpIpConfig tcp = join.getTcpIpConfig().setEnabled(true);
//        tcp.addMember("10.20.40.123"); // Friend's IP
        tcp.addMember("0.0.0.0");

        HazelcastClusterManager mgr = new HazelcastClusterManager(hazelcastConfig);
        VertxOptions options = new VertxOptions().setClusterManager(mgr);
        options.getEventBusOptions().setHost("10.20.41.66"); // Your IP

        Vertx.clusteredVertx(options, res -> {
            if (res.succeeded()) {
                Vertx vertx = res.result();
                vertx.deployVerticle(new PersonSenderVerticle());
            } else {
                System.err.println("Cluster failed: " + res.cause());
            }
        });
    }
}
