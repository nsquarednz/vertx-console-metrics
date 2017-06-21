package in.yunyul.vertx.console.metrics;

import in.yunyul.vertx.console.base.ConsolePage;
import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.vertx.MetricsHandler;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;

@SuppressWarnings("unused")
public class MetricsConsolePage implements ConsolePage {
    private CollectorRegistry registry;

    public MetricsConsolePage(CollectorRegistry registry) {
        this.registry = registry;
    }

    @Override
    public void mount(Vertx vertx, Router router, String basePath) {
        router.route(basePath + "/metrics").handler(new MetricsHandler(registry));
    }

    @Override
    public String getLoaderFileName() {
        return "/js/metrics.js";
    }
}
