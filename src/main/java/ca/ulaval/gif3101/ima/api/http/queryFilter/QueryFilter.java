package ca.ulaval.gif3101.ima.api.http.queryFilter;

import ca.ulaval.gif3101.ima.api.http.UriBuilderFactory;
import ca.ulaval.gif3101.ima.api.http.queryFilter.exception.EmptyPageException;

import javax.ws.rs.core.UriBuilder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QueryFilter<T> {
    public static final int NB_PER_PAGE = 50;

    private final int nbPerPage = NB_PER_PAGE;
    private UriBuilderFactory uriBuilderFactory;
    private String uri;
    private List<T> collection;

    private Map<String, String> queryParameters = new HashMap<>();

    private int currentPage;


    public QueryFilter(UriBuilderFactory uriBuilderFactory, String uri, int currentPage, List<T> collection) {
        this.currentPage = currentPage;
        this.uri = uri;
        this.uriBuilderFactory = uriBuilderFactory;
        this.collection = collection;
    }

    public String toString() {
        return "{" +
                "\"current-page\":\"" + currentPage + "\"" +
                "\"nb-per-page\":\"" + nbPerPage + "\"" +
                "}";
    }

    public List<T> apply() throws Exception {
        if (currentPageCount() < 0) {
            throw new EmptyPageException("There is no entry in this page");
        }
        int endIndex = Math.min(collection.size() - ((currentPage - 1) * nbPerPage), nbPerPage);

        List<T> objects = new ArrayList<>();
        for (int i = 0; i < endIndex; i++) {
            objects.add(collection.get(i));
        }
        return objects;
    }

    public void addQueryParam(String key, String value) {
        queryParameters.put(key, value);
    }

    public int pageCount() {
        return collection.size() / nbPerPage + ((collection.size() % nbPerPage == 0) ? 0 : 1);
    }

    public int getNbPerPage() {
        return nbPerPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int currentPageCount() {
        return Math.min(collection.size() - ((currentPage - 1) * nbPerPage), nbPerPage);
    }

    public String nextPageUri() {
        if (currentPage >= pageCount()) {
            return null;
        }

        UriBuilder uriBuilder = uriBuilderFactory.create(uri);
        uriBuilder.queryParam("page", currentPage + 1);

        addQueryParamsToBuilder(uriBuilder);

        return uriBuilder.build().toString();
    }

    private void addQueryParamsToBuilder(UriBuilder uriBuilder) {
        for(Map.Entry<String, String> entry : queryParameters.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            uriBuilder.queryParam(key, value);
        }
    }

    public String previousPageUri() {
        if (currentPage <= 1) {
            return null;
        }
        UriBuilder uriBuilder = uriBuilderFactory.create(uri);
        uriBuilder.queryParam("page", currentPage - 1);

        addQueryParamsToBuilder(uriBuilder);

        return uriBuilder.build().toString();
    }
}
