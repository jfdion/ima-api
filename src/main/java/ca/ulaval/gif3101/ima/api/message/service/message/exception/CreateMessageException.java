package ca.ulaval.gif3101.ima.api.message.service.message.exception;

public class CreateMessageException extends Exception {
    public CreateMessageException(String message) {
        super(message);
    }

    public CreateMessageException(String message, Throwable cause) {
        super(message, cause);
    }
}
