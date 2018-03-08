package ca.ulaval.gif3101.ima.api.http.json.wrapper;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JsonEntityWrapper<T> extends AbstractJsonWrapper {

    @JsonProperty(value = "data")
    private T entity;

    public JsonEntityWrapper(T entity) {
        this.entity = entity;
    }

}
