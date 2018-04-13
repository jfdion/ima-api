package ca.ulaval.gif3101.ima.api.message.http.json.wrapper;

import ca.ulaval.gif3101.ima.api.message.http.json.message.AbstractJsonMessage;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class AbstractJsonWrapper implements JsonWrapper{

    @JsonProperty(value = "messages")
    protected List<AbstractJsonMessage> messages = new ArrayList<>();

    public void addMessage(AbstractJsonMessage jsonMessage) {
        messages.add(jsonMessage);
    }
}
