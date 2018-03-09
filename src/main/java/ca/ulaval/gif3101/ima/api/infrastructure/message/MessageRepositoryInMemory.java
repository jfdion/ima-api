package ca.ulaval.gif3101.ima.api.infrastructure.message;

import ca.ulaval.gif3101.ima.api.domain.location.Location;
import ca.ulaval.gif3101.ima.api.domain.message.Message;
import ca.ulaval.gif3101.ima.api.domain.message.MessageAssembler;
import ca.ulaval.gif3101.ima.api.domain.message.MessageRepository;
import ca.ulaval.gif3101.ima.api.infrastructure.message.dto.MessageEntity;
import ca.ulaval.gif3101.ima.api.infrastructure.message.filter.MessageFilter;
import ca.ulaval.gif3101.ima.api.infrastructure.message.filter.FilterConfig;

import java.util.ArrayList;
import java.util.List;

public class MessageRepositoryInMemory implements MessageRepository {

    private MessageAssembler messageAssembler;
    private MessageDAO messageDAO;

    public MessageRepositoryInMemory(MessageAssembler messageAssembler, MessageDAO messageDAO) {
        this.messageAssembler = messageAssembler;
        this.messageDAO = messageDAO;
    }

    @Override
    public void save(Message message) throws Exception {
        MessageEntity entity = messageAssembler.createEntity(message);
        this.messageDAO.create(entity);
        messageAssembler.updateMessage(message, entity);
    }

    @Override
    public List<Message> findAll() {
        List<MessageEntity> entities = this.messageDAO.findAll();

        List<Message> messages = new ArrayList<>();
        for(MessageEntity entity:entities) {
            messages.add(messageAssembler.create(entity));
        }
        return messages;
    }

    public List<Message> findFiltered(MessageFilter messageFilter, FilterConfig config) {
        List<Message> messages = findAll();
        return messageFilter.filter(messages, config);
    }

    @Override
    public List<Message> findByLocation(Location location) throws Exception {
        return null;
    }
}
