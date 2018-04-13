package ca.ulaval.gif3101.ima.api.message.http.json.wrapper;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JsonEmptyWrapper extends AbstractJsonWrapper{

    @JsonProperty(value="data")
    private Object object;
}
