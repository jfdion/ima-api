package ca.ulaval.gif3101.ima.api.http.context;

import ca.ulaval.gif3101.ima.api.app.config.CorsConfig;
import ca.ulaval.gif3101.ima.api.app.context.AbstractContext;
import ca.ulaval.gif3101.ima.api.app.context.Context;
import ca.ulaval.gif3101.ima.api.app.endpoint.Endpoint;
import ca.ulaval.gif3101.ima.api.app.util.RequestUtil;
import spark.Service;

import java.util.ArrayList;
import java.util.List;

public class RestContext extends AbstractContext implements Context {

    private final CorsConfig corsConfig;

    private List<Endpoint> endpoints = new ArrayList<Endpoint>();

    public RestContext(String basePath, CorsConfig corsConfig) {
        super(basePath);
        this.corsConfig = corsConfig;
    }

    public void run(Service spark) {
        enableCors(spark);
        super.run(spark);
    }

    private void enableCors(Service spark) {
        RequestUtil.enableCORS(
                spark,
                path(),
                origin(),
                methods(),
                headers()
        );
    }

    private String path() {
        return corsConfig.getPath();
    }

    private String origin() {
        return String.join(",", corsConfig.getOrigin());
    }

    private String methods() {
        return String.join(",", corsConfig.getMethods());
    }

    private String headers() {
        return String.join(",", corsConfig.getHeaders());
    }
}
