package ca.ulaval.gif3101.ima.api.message.infrastructure.message.dao;

import ca.ulaval.gif3101.ima.api.message.domain.message.Message;
import ca.ulaval.gif3101.ima.api.message.domain.message.MessageDto;

import java.util.List;

public interface MessageDAO {

    String ID_KEY = "id";

    List<Message> findAll();
    void create(Message message) throws Exception;
    void createAll(List<Message> messages);
    void update(Message message) throws Exception;
    void delete(Message message) throws Exception;
    Message findOneByKey(String key, String value) throws Exception;
}
