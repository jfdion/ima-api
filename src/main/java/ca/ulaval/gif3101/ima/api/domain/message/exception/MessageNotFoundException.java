package ca.ulaval.gif3101.ima.api.domain.message.exception;

import ca.ulaval.gif3101.ima.api.domain.DomainException;

public class MessageNotFoundException extends DomainException {

    public MessageNotFoundException(String message) {
        super(message);
    }
}
