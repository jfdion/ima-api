package ca.ulaval.gif3101.ima.api.message.domain.message.exception;


public class MessageNotFoundException extends Exception {

    public MessageNotFoundException(String message) {
        super(message);
    }

    public MessageNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
