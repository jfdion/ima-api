package ca.ulaval.gif3101.ima.api.domain.message.exception;

import ca.ulaval.gif3101.ima.api.domain.DomainException;

public class MessageAlreadyExistsException extends DomainException {

    public MessageAlreadyExistsException(String message) {
        super(message);
    }
}
