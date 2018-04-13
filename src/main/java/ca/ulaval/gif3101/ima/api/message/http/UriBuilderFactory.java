package ca.ulaval.gif3101.ima.api.message.http;

import javax.ws.rs.core.UriBuilder;

public class UriBuilderFactory {

    public UriBuilder create(String uri) {
        return UriBuilder.fromUri(uri);
    }
}
