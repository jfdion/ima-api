package ca.ulaval.gif3101.ima.api.app.context;

import ca.ulaval.gif3101.ima.api.app.endpoint.Endpoint;
import spark.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AbstractContext {

    private List<Endpoint> endpoints = new ArrayList<>();
    private String basePath;

    public AbstractContext(String basePath) {
        this.basePath = basePath;
    }

    public void addEndpoint(Endpoint endpoint) {
        if (!endpoints.contains(endpoint)) {
            endpoints.add(endpoint);
            endpoint.setBasePath(basePath);
        }
    }

    public void run(Service spark) {
        for (Iterator<Endpoint> i = endpoints.iterator(); i.hasNext(); ) {
            Endpoint item = i.next();
            item.register(spark);
        }
    }
}
