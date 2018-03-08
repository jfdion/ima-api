package ca.ulaval.gif3101.ima.api.http.queryFilter;

import ca.ulaval.gif3101.ima.api.http.UriBuilderFactory;
import spark.Request;

import java.util.List;

public class QueryFilterFactory {

    public static final int DEFAULT_VALUE_PAGE = 1;
    private UriBuilderFactory uriBuilderFactory;

    public QueryFilterFactory(UriBuilderFactory uriBUilder) {
        this.uriBuilderFactory = uriBUilder;
    }

    public <T> QueryFilter create(Request request, List<T> collection) {
        return new QueryFilter<>(
                uriBuilderFactory,
                request.url(),
                intValueOrDefault(request.queryParams("page"), DEFAULT_VALUE_PAGE),
                collection
        );
    }

    private int intValueOrDefault(String value, int defaultValue) {
        if (value == null) {
            return defaultValue;
        }
        return Integer.valueOf(value);
    }
}
