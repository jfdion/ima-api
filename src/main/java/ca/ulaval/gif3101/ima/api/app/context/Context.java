package ca.ulaval.gif3101.ima.api.app.context;

import ca.ulaval.gif3101.ima.api.app.endpoint.Endpoint;
import spark.Service;

public interface Context {

    void addEndpoint(Endpoint endpoint);

    void run(Service spark);
}
