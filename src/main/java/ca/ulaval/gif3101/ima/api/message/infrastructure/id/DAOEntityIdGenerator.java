package ca.ulaval.gif3101.ima.api.message.infrastructure.id;

import ca.ulaval.gif3101.ima.api.message.infrastructure.message.dto.MessageEntity;

public class DAOEntityIdGenerator implements IdGenerator {

    private String id;

    private DAOEntityIdGenerator(String id) {
        this.id = id;
    }

    public static DAOEntityIdGenerator createFromEntity(MessageEntity entity) {
        return new DAOEntityIdGenerator(entity.id.toString());
    }

    @Override
    public String generate() {
        return id;
    }
}
