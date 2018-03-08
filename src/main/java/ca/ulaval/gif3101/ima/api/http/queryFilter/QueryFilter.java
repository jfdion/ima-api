package ca.ulaval.gif3101.ima.api.http.queryFilter;

import ca.ulaval.gif3101.ima.api.http.UriBuilderFactory;

import javax.ws.rs.core.UriBuilder;
import java.util.ArrayList;
import java.util.List;

public class QueryFilter<T> {
    public static final int NB_PER_PAGE = 50;

    private final int nbPerPage = NB_PER_PAGE;
    private UriBuilderFactory uriBuilderFactory;
    private String uri;
    private List<T> collection;

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

    public List<T> apply() {
        int endIndex = Math.min(collection.size() - ((currentPage - 1) * nbPerPage), nbPerPage);

        List<T> objects = new ArrayList<>();
        for (int i = 0; i < endIndex; i++) {
            objects.add(collection.get(i));
        }
        return objects;
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

        return uriBuilder.build().toString();
    }

    public String previousPageUri() {
        if (currentPage <= 1) {
            return null;
        }
        UriBuilder uriBuilder = uriBuilderFactory.create(uri);
        uriBuilder.queryParam("page", currentPage - 1);

        return uriBuilder.build().toString();
    }
}
