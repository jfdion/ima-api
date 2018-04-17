package ca.ulaval.gif3101.ima.api.message.bootstrap.context;

import ca.ulaval.gif3101.ima.api.message.domain.message.MessageFactory;
import ca.ulaval.gif3101.ima.api.message.domain.message.MessageRepository;
import ca.ulaval.gif3101.ima.api.message.domain.message.filter.Filter;
import ca.ulaval.gif3101.ima.api.message.infrastructure.id.IdGenerator;

public interface Context {

    MessageRepository getMessageRepository(MessageFactory messageFactory, Filter filter);

}
