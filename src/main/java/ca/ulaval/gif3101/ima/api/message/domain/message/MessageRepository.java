package ca.ulaval.gif3101.ima.api.message.domain.message;

import ca.ulaval.gif3101.ima.api.message.domain.message.exception.MessageNotFoundException;
import ca.ulaval.gif3101.ima.api.message.domain.message.query.MessageQuery;

import java.util.List;

public interface MessageRepository {

    void save(Message message);

    void saveAll(List<Message> messages);

    List<Message> findAll();

    List<Message> findFiltered(MessageQuery query);

    Message findOneById(String id) throws MessageNotFoundException;
}
