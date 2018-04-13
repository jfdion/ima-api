package ca.ulaval.gif3101.ima.api.message.infrastructure.message.dao;

import ca.ulaval.gif3101.ima.api.message.domain.message.MessageDto;
import ca.ulaval.gif3101.ima.api.message.domain.message.exception.MessageAlreadyExistsException;
import ca.ulaval.gif3101.ima.api.message.domain.message.exception.MessageNotFoundException;
import ca.ulaval.gif3101.ima.api.message.infrastructure.id.IdGenerator;
import ca.ulaval.gif3101.ima.api.message.infrastructure.message.dto.MessageEntity;

import java.util.ArrayList;
import java.util.List;

public class MessageDAOInMemory implements MessageDAO {

    private IdGenerator idGenerator;
    private List<MessageEntity> entities = new ArrayList<>();

    public MessageDAOInMemory(IdGenerator idGenerator) {
        this.idGenerator = idGenerator;
    }

    @Override
    public List<MessageDto> findAll() {
        List<MessageDto> dtos = new ArrayList<>();
        for (MessageEntity entity: entities) {
            dtos.add(messageEntityToDto((entity)));
        }
        return dtos;
    }

    @Override
    public void create(MessageDto dto) throws Exception {
        MessageEntity entity = messageDtoToEntity(dto);
        if (entity.id == null) {
            entity.id = idGenerator.generate();
            entities.add(entity);
            return;
        }
        throw new MessageAlreadyExistsException(String.format("Message with id '%s' already exists", entity.id));
    }

    @Override
    public void update(MessageDto dto) throws Exception {
        MessageEntity entity = messageDtoToEntity(dto);
        for (int i = 0; i < entities.size(); i++) {
            if (entity.id.equals(entities.get(i).id)) {
                entities.remove(i);
                entities.add(entity);
                return;
            }
        }
        throw new MessageNotFoundException(String.format("No message with id '%s' were found.", entity.id));
    }

    @Override
    public void delete(MessageDto dto) throws Exception {
        for (int i = 0; i < entities.size(); i++) {
            if (entities.get(i).id.equals(dto.id)) {
                entities.remove(i);
                return;
            }
        }
        throw new MessageNotFoundException(String.format("No message with id '%s' were found.", dto.id));
    }

    @Override
    public MessageDto findOneByKey(String key, String value) throws Exception {
        switch (key) {
            case ID_KEY:
                for(MessageEntity entity: entities) {
                    if (entity.id.equals(value)) {
                        return messageEntityToDto(entity);
                    }
                }
                break;
        }
        throw new MessageNotFoundException(String.format("No message with value '%s' for key '%s' were found.", value, key));
    }

    private MessageEntity messageDtoToEntity(MessageDto dto) {
        MessageEntity entity = new MessageEntity();

        entity.id = dto.id;
        entity.title = dto.title;
        entity.body = dto.body;
        entity.longitude = dto.longitude;
        entity.latitude = dto.latitude;
        entity.created = dto.created;
        entity.expires = dto.expires;

        return entity;
    }

    private MessageDto messageEntityToDto(MessageEntity entity) {
        MessageDto dto = new MessageDto();

        dto.id = entity.id;
        dto.title = entity.title;
        dto.body = entity.body;
        dto.longitude = entity.longitude;
        dto.latitude = entity.latitude;
        dto.created = entity.created;
        dto.expires = entity.expires;

        return dto;
    }
}
