package ca.ulaval.gif3101.ima.api.http.json.wrapper;

import ca.ulaval.gif3101.ima.api.http.json.message.AbstractJsonMessage;

public interface JsonWrapper {

    void addMessage(AbstractJsonMessage jsonMessage);
}
