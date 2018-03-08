package ca.ulaval.gif3101.ima.api.http.json.message;

import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class AbstractJsonMessage {

    @JsonProperty(value = "message")
    public String message;

    @JsonProperty(value = "internal-code")
    public String internalCode;

    @JsonProperty(value = "description")
    public String description;

    @JsonProperty(value = "code")
    public int httpCode;
}
