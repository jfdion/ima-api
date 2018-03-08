package ca.ulaval.gif3101.ima.api.controller.message.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateMessageDto extends MessageDto {

    public String title;

    public String body;

    @JsonProperty(value="expires-at")
    public String expires;

    public String latitude;

    public String longitude;

}
