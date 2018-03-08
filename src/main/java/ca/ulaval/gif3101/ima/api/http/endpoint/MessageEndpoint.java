package ca.ulaval.gif3101.ima.api.http.endpoint;

import ca.ulaval.gif3101.ima.api.app.endpoint.AbstractEndpoint;
import ca.ulaval.gif3101.ima.api.controller.message.MessageController;
import spark.Service;

public class MessageEndpoint extends AbstractEndpoint {

    private static final String MESSAGE_LIST_ALL = "/messages";
    private static final String MESSAGE_CREATE = "/messages";

    private MessageController controller;

    public MessageEndpoint(MessageController controller) {
        this.controller = controller;
    }

    public void register(Service spark) {
        spark.get(path(MESSAGE_LIST_ALL), (request, response) -> controller.getAll(request, response));
        spark.post(path(MESSAGE_CREATE), (request, response) -> controller.create(request, response));
    }
}
