package ca.ulaval.gif3101.ima.api.message.infrastructure.message;

import ca.ulaval.gif3101.ima.api.message.domain.message.Message;
import ca.ulaval.gif3101.ima.api.message.domain.message.MessageRepository;
import ca.ulaval.gif3101.ima.api.message.domain.message.exception.MessageNotFoundException;
import ca.ulaval.gif3101.ima.api.message.domain.message.filter.Filter;
import ca.ulaval.gif3101.ima.api.message.domain.message.query.MessageQuery;
import ca.ulaval.gif3101.ima.api.message.infrastructure.id.IdGenerator;

import java.util.ArrayList;
import java.util.List;

public class MessageRepositoryInMemory implements MessageRepository {

    private IdGenerator idGenerator;
    private Filter filter;
    private List<Message> messages = new ArrayList<>();

    public MessageRepositoryInMemory(IdGenerator idGenerator, Filter filter) {
        this.idGenerator = idGenerator;
        this.filter = filter;
    }

    @Override
    public void save(Message message) {
        if (!message.hasId()) {
            message.assignId(idGenerator);
        }
        if (!messages.contains(message)) {
            messages.add(message);
        }
    }

    @Override
    public void saveAll(List<Message> messages) {
        for (Message message : messages) {
            if (!this.messages.contains(message)) {
                this.messages.add(message);
            }
        }
    }

    @Override
    public List<Message> findAll() {
        return messages;
    }

    public List<Message> findFiltered(MessageQuery query) {
        return filter.filter(messages, query);
    }

    @Override
    public Message findOneById(String id) throws MessageNotFoundException {
        for (Message message : messages) {
            if (message.idEquals(id)) {
                return message;
            }
        }
        throw new MessageNotFoundException(String.format("No message with id '%s' were found.", id));
    }
}
