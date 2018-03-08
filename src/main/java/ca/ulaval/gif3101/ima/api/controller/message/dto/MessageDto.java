package ca.ulaval.gif3101.ima.api.controller.message.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MessageDto {

    public String id;
    public String title;
    public String body;

    @JsonProperty(value="expires-at")
    public String expires;

    @JsonProperty(value="created-at")
    public String created;
    public double latitude;
    public double longitude;

}
