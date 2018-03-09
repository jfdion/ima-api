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

    public <T> QueryFilter create(Request request, List<T> collection, Class type) {
      QueryFilter queryFilter = new QueryFilter<>(
                uriBuilderFactory,
                request.url(),
                intValueOrDefault(request.queryParams("page"), DEFAULT_VALUE_PAGE),
                collection
        );
        if (request.queryParams("location") != null) {
            queryFilter.addQueryParam("location", request.queryParams("location"));
        }
        if (request.queryParams("location-scope") != null) {
            queryFilter.addQueryParam("location-scope", request.queryParams("location-scope"));
        }

        return queryFilter;
    }

    private int intValueOrDefault(String value, int defaultValue) {
        if (value == null) {
            return defaultValue;
        }
        return Integer.valueOf(value);
    }
}
