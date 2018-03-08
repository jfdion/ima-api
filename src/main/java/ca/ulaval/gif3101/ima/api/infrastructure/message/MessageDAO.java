package ca.ulaval.gif3101.ima.api.infrastructure.message;

import ca.ulaval.gif3101.ima.api.infrastructure.message.dto.MessageEntity;

import java.util.List;

public interface MessageDAO {

    List<MessageEntity> findAll();
    void create(MessageEntity entity) throws Exception;
    void update(MessageEntity entity) throws Exception;
    void delete(MessageEntity entity) throws Exception;
}
