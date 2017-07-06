package in.yunyul.vertx.console.metrics;

import in.yunyul.vertx.console.base.ConsolePage;
import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.vertx.MetricsHandler;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

@SuppressWarnings("unused")
public class MetricsConsolePage implements ConsolePage {
    private static final String JSON_CONTENT_TYPE = "application/json";

    private CollectorRegistry registry;

    public MetricsConsolePage(CollectorRegistry registry) {
        this.registry = registry;
    }

    @Override
    public void mount(Vertx vertx, Router router, String basePath) {
        router.route(basePath + "/metrics").handler(new MetricsHandler(registry));
        router.route(basePath + "/verticle*").handler(BodyHandler.create());
        router.route(basePath + "/verticle/deploy")
                .consumes(JSON_CONTENT_TYPE).produces(JSON_CONTENT_TYPE)
                .handler(ctx -> {
                    JsonObject body = ctx.getBodyAsJson();
                    String name = body.getString("name");
                    boolean isWorker = body.getBoolean("isWorker");
                    boolean ha = body.getBoolean("ha");
                    int instances = body.getInteger("instances");
                    JsonObject deployConfig = body.getJsonObject("deployConfig");

                    DeploymentOptions deploymentOptions = new DeploymentOptions()
                            .setWorker(isWorker)
                            .setHa(ha)
                            .setInstances(instances);
                    if (deployConfig.size() > 0) {
                        deploymentOptions.setConfig(deployConfig);
                    }

                    // TODO: Completion handler
                    vertx.deployVerticle(name, deploymentOptions,
                            result -> {
                                JsonObject response = new JsonObject();
                                if (result.succeeded()) {
                                    response.put("status", 200);
                                } else {
                                    response.put("status", 400);
                                }
                                ctx.response().putHeader("content-type", JSON_CONTENT_TYPE).end(response.toString());
                            });
                });
    }

    @Override
    public String getLoaderFileName() {
        return "/js/metrics.js";
    }

    private static boolean classExists(String name) {
        try {
            Class.forName(name);
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }
}
