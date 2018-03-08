package ca.ulaval.gif3101.ima.api.app.config;

import java.util.ArrayList;
import java.util.List;

public class CorsConfig {

    private String path;
    private List<String> origins = new ArrayList<String>();
    private List<String> methods = new ArrayList<String>();
    private List<String> headers = new ArrayList<String>();

    public String getPath() {
        return path;
    }

    public List<String> getOrigin() {
        return origins;
    }

    public void addOrigin(String origin) {
        if (!origins.contains(origin)) {
            this.origins.add(origin);
        }
    }

    public List<String> getMethods() {
        return methods;
    }

    public void addMethod(String method) {
        if (!methods.contains(method)) {
            methods.add(method);
        }
    }

    public List<String> getHeaders() {
        return headers;
    }

    public void addHeader(String header) {
        if (!headers.contains(header)) {
            headers.add(header);
        }
    }

    public void setPath(String path) {
        this.path = path;
    }
}
