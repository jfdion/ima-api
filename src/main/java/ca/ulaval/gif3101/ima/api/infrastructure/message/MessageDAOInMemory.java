package ca.ulaval.gif3101.ima.api.infrastructure.message;

import ca.ulaval.gif3101.ima.api.domain.message.exception.MessageAlreadyExistsException;
import ca.ulaval.gif3101.ima.api.domain.message.exception.MessageNotFoundException;
import ca.ulaval.gif3101.ima.api.infrastructure.id.IdGenerator;
import ca.ulaval.gif3101.ima.api.infrastructure.message.dto.MessageEntity;

import java.util.ArrayList;
import java.util.List;

public class MessageDAOInMemory implements MessageDAO {

    private IdGenerator idGenerator;
    private List<MessageEntity> entities = new ArrayList<>();

    public MessageDAOInMemory(IdGenerator idGenerator) {
        this.idGenerator = idGenerator;
    }

    @Override
    public List<MessageEntity> findAll() {
        return entities;
    }

    @Override
    public void create(MessageEntity entity) throws Exception {
        if (entity.id == null) {
            entity.id = idGenerator.generate();
            entities.add(entity);
            return;
        }
        throw new MessageAlreadyExistsException(String.format("Message with id '%s' already exists", entity.id));
    }

    @Override
    public void update(MessageEntity entity) throws Exception {
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
    public void delete(MessageEntity entity) throws Exception {
        for (int i = 0; i < entities.size(); i++) {
            if (entity.id.equals(entities.get(i).id)) {
                entities.remove(i);
                return;
            }
        }
        throw new MessageNotFoundException(String.format("No message with id '%s' were found.", entity.id));
    }

    @Override
    public MessageEntity findOneByKey(String key, String value) throws Exception {
        switch (key) {
            case ID_KEY:
                for(MessageEntity entity: entities) {
                    if (entity.id.equals(value)) {
                        return entity;
                    }
                }
                break;
        }
        throw new MessageNotFoundException(String.format("No message with value '%s' for key '%s' were found.", value, key));
    }
}
