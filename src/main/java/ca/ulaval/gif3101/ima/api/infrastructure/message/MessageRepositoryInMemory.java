package ca.ulaval.gif3101.ima.api.infrastructure.message;

import ca.ulaval.gif3101.ima.api.domain.message.Message;
import ca.ulaval.gif3101.ima.api.domain.message.MessageAssembler;
import ca.ulaval.gif3101.ima.api.domain.message.MessageDto;
import ca.ulaval.gif3101.ima.api.domain.message.MessageRepository;
import ca.ulaval.gif3101.ima.api.infrastructure.message.dao.MessageDAO;
import ca.ulaval.gif3101.ima.api.domain.message.filter.Filter;
import ca.ulaval.gif3101.ima.api.domain.message.filter.FilterConfig;

import java.util.ArrayList;
import java.util.List;

public class MessageRepositoryInMemory implements MessageRepository {

    private MessageAssembler messageAssembler;
    private MessageDAO messageDAO;
    private Filter filter;

    public MessageRepositoryInMemory(MessageAssembler messageAssembler, MessageDAO messageDAO, Filter filter) {
        this.messageAssembler = messageAssembler;
        this.messageDAO = messageDAO;
        this.filter = filter;
    }

    @Override
    public void save(Message message) throws Exception {
        MessageDto dto = messageAssembler.createDto(message);
        this.messageDAO.create(dto);
        messageAssembler.updateMessage(message, dto);
    }

    @Override
    public List<Message> findAll() {
        List<MessageDto> dtos = this.messageDAO.findAll();

        List<Message> messages = new ArrayList<>();
        for(MessageDto dto:dtos) {
            messages.add(messageAssembler.create(dto));
        }
        return messages;
    }

    public List<Message> findFiltered(FilterConfig config) {
        List<Message> messages = findAll();
        return filter.filter(messages, config);
    }

    @Override
    public Message findOneById(String id) throws Exception {
        MessageDto dto = messageDAO.findOneByKey(MessageDAO.ID_KEY, id);
        return messageAssembler.create(dto);
    }
}
