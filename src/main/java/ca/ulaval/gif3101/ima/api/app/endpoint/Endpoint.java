package ca.ulaval.gif3101.ima.api.app.endpoint;

import spark.Service;

public interface Endpoint {

    void register(Service spark);

    void setBasePath(String basePath);
}
