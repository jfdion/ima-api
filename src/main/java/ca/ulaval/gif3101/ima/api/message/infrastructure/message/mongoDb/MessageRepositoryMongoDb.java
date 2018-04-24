package ca.ulaval.gif3101.ima.api.message.infrastructure.message.mongoDb;

import ca.ulaval.gif3101.ima.api.message.domain.message.Message;
import ca.ulaval.gif3101.ima.api.message.domain.message.MessageRepository;
import ca.ulaval.gif3101.ima.api.message.domain.message.exception.MessageNotFoundException;
import ca.ulaval.gif3101.ima.api.message.domain.message.filter.Filter;
import ca.ulaval.gif3101.ima.api.message.domain.message.query.MessageQuery;
import ca.ulaval.gif3101.ima.api.message.infrastructure.message.dao.MessageDAO;

import java.util.List;

public class MessageRepositoryMongoDb implements MessageRepository {

    private MessageDAO messageDAO;
    private Filter filter;

    public MessageRepositoryMongoDb(MessageDAO messageDAO, Filter filter) {
        this.messageDAO = messageDAO;
        this.filter = filter;
    }

    @Override
    public void save(Message message) {
        try {
            this.messageDAO.create(message);
        } catch (Exception e) {
            System.out.println("Exception : " + e.getMessage());
        }
    }

    @Override
    public void saveAll(List<Message> messages) {
        this.messageDAO.createAll(messages);
    }

    @Override
    public List<Message> findAll() {
        return messageDAO.findAll();
    }

    @Override
    public List<Message> findFiltered(MessageQuery query) {
        return filter.filter(messageDAO.findAllFiltered(query), query);
    }

    @Override
    public Message findOneById(String id) throws MessageNotFoundException {
        try {
            return messageDAO.findOneByKey(MessageDAO.ID_KEY, id);
        } catch (Exception e) {
            throw new MessageNotFoundException(e.getMessage(), e);
        }
    }
}
