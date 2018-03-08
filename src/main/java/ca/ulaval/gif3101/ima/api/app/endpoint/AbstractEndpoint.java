package ca.ulaval.gif3101.ima.api.app.endpoint;

public abstract class AbstractEndpoint implements Endpoint{
    private String basePath = "/";

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    protected String path(String path) {
        return basePath + path;
    }
}
