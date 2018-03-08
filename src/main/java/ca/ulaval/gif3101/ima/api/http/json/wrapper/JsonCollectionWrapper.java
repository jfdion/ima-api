package ca.ulaval.gif3101.ima.api.http.json.wrapper;

import ca.ulaval.gif3101.ima.api.http.json.wrapper.metadata.Metadata;
import ca.ulaval.gif3101.ima.api.http.queryFilter.QueryFilter;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;


public class JsonCollectionWrapper<T> extends AbstractJsonWrapper {

    private QueryFilter queryFilter;

    @JsonProperty(value = "data")
    private List<T> collection;

    @JsonProperty(value = "metadata")
    private Object metadata;

    public JsonCollectionWrapper(List<T> collection) {
        this.metadata = buildMetadata(collection);
        this.collection = collection;
    }

    public JsonCollectionWrapper(List<T> collection, QueryFilter queryFilter) {
        this.queryFilter = queryFilter;
        this.collection = queryFilter.apply();
        this.metadata = buildMetadata(collection);
    }

    private Metadata buildMetadata(List<T> collection) {
        Metadata metadata = new Metadata();
        if (queryFilter != null) {
            metadata.currentPage = queryFilter.getCurrentPage();
            metadata.totalPageCount = queryFilter.pageCount();
            metadata.inCurrentPage = queryFilter.currentPageCount();
            metadata.next = queryFilter.nextPageUri();
            metadata.previous = queryFilter.previousPageUri();
        }
        metadata.totalCount = collection.size();
        return metadata;
    }
}
