package ca.ulaval.gif3101.ima.api.message.infrastructure.message.dao;

import ca.ulaval.gif3101.ima.api.message.domain.message.MessageDto;
import ca.ulaval.gif3101.ima.api.message.infrastructure.message.dto.MessageEntity;

import java.util.List;

public interface MessageDAO {

    String ID_KEY = "id";

    List<MessageDto> findAll();
    void create(MessageDto dto) throws Exception;
    void update(MessageDto dto) throws Exception;
    void delete(MessageDto dto) throws Exception;
    MessageDto findOneByKey(String key, String value) throws Exception;
}
