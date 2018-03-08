package ca.ulaval.gif3101.ima.api.http.json.wrapper.metadata;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Metadata {

    @JsonProperty(value = "total-count")
    public int totalCount;

    @JsonProperty(value="total-page-count")
    public int totalPageCount;

    @JsonProperty(value = "current-page")
    public int currentPage;

    @JsonProperty(value = "in-current-page")
    public int inCurrentPage;

    @JsonProperty
    public String next;

    @JsonProperty
    public String previous;

}
