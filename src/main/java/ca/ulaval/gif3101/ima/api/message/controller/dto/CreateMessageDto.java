package ca.ulaval.gif3101.ima.api.message.controller.dto;

import ca.ulaval.gif3101.ima.api.message.domain.message.MessageDto;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateMessageDto extends MessageDto {

    public String title;

    public String body;

    @JsonProperty(value="expires-at")
    public String expires;

    public String latitude;

    public String longitude;

    @JsonProperty(value="visibility-start-time")
    public String visibilityStartTime;

    @JsonProperty(value="visibility-end-time")
    public String visibilityEndTime;

}
