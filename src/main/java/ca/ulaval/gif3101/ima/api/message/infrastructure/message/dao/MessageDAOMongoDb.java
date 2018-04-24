package ca.ulaval.gif3101.ima.api.message.infrastructure.message.dao;

import ca.ulaval.gif3101.ima.api.message.domain.message.Message;
import ca.ulaval.gif3101.ima.api.message.domain.message.MessageDto;
import ca.ulaval.gif3101.ima.api.message.domain.message.MessageFactory;
import ca.ulaval.gif3101.ima.api.message.domain.message.query.MessageQuery;
import ca.ulaval.gif3101.ima.api.message.external.date.JodaTimeDateAdapter;
import ca.ulaval.gif3101.ima.api.message.infrastructure.id.DAOEntityIdGenerator;
import ca.ulaval.gif3101.ima.api.message.infrastructure.message.dao.Exception.InvalidOperationException;
import ca.ulaval.gif3101.ima.api.message.infrastructure.message.dto.MessageEntity;
import ca.ulaval.gif3101.ima.api.message.infrastructure.message.mongoDb.filter.QueryFilter;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;

import java.util.ArrayList;
import java.util.List;

public class MessageDAOMongoDb implements MessageDAO {

    private Datastore datastore;
    private QueryFilter queryFilter;
    private MessageFactory messageFactory;

    public MessageDAOMongoDb(Datastore datastore, QueryFilter queryFilter, MessageFactory messageFactory) {
        this.datastore = datastore;
        this.queryFilter = queryFilter;
        this.messageFactory = messageFactory;
    }

    @Override
    public List<Message> findAll() {
        List<MessageEntity> messageEntities = datastore.find(MessageEntity.class).asList();

        return entityListToMessageList(messageEntities);
    }

    private List<Message> entityListToMessageList(List<MessageEntity> messageEntities) {
        List<Message> messages = new ArrayList<>();
        for (MessageEntity entity : messageEntities) {
            messages.add(toMessage(entity));
        }
        return messages;
    }

    @Override
    public List<Message> findAllFiltered(MessageQuery messageQuery) {

        Query<MessageEntity> query = queryFilter.filter(datastore.find(MessageEntity.class), messageQuery);
        List<MessageEntity> entities = query.asList();
        return entityListToMessageList(entities);
    }

    @Override
    public void create(Message message) throws Exception {
        MessageEntity entity = toEntity(message);
        datastore.save(entity);
        message.assignId(DAOEntityIdGenerator.createFromEntity(entity));
    }

    @Override
    public void createAll(List<Message> messages) {
        List<MessageEntity> entities = new ArrayList<>();
        for (Message message : messages) {
            entities.add(toEntity(message));
        }
        datastore.save(entities);
    }

    @Override
    public void update(Message message) throws Exception {
        throw new InvalidOperationException("Cannot update message");
    }

    @Override
    public void delete(Message message) throws Exception {
        throw new InvalidOperationException("Cannot delete message");
    }

    @Override
    public Message findOneByKey(String key, String value) throws Exception {
        MessageEntity entity;
        if (isId(key)) {
            entity = datastore.get(MessageEntity.class, new ObjectId(value));
        } else {
            entity = datastore.find(MessageEntity.class).filter(key, value).get();
        }

        return toMessage(entity);
    }

    private boolean isId(String key) {
        return key.equals(ID_KEY);
    }

    private MessageEntity toEntity(Message message) {
        MessageEntity entity = new MessageEntity();

        if (message.hasId()) {
            entity.id = new ObjectId(message.getId());
        }
        entity.author = message.getAuthor().getUsername();
        entity.title = message.getTitle();
        entity.body = message.getBody();
        entity.latitude = message.getLocation().getLatitude();
        entity.longitude = message.getLocation().getLongitude();
        entity.created = message.getCreated().toString();
        entity.expires = message.getExpires().toString();
        if (message.getVisibilityPeriod() != null) {
            entity.visibilityStartTime = message.getVisibilityPeriod().start().toString();
            entity.visibilityEndTime = message.getVisibilityPeriod().end().toString();
        }

        return entity;
    }

    private Message toMessage(MessageEntity entity) {
        Message message = messageFactory.create(toMessageDto(entity));
        if (!message.hasId()) {
            message.assignId(DAOEntityIdGenerator.createFromEntity(entity));
        }
        message.changeCreated(new JodaTimeDateAdapter(entity.created));
        return message;
    }

    private MessageDto toMessageDto(MessageEntity entity) {
        MessageDto dto = new MessageDto();

        dto.id = entity.id.toString();
        dto.author = entity.author;
        dto.title = entity.title;
        dto.body = entity.body;
        dto.latitude = entity.latitude;
        dto.longitude = entity.longitude;
        dto.created = entity.created;
        dto.expires = entity.expires;
        dto.visibilityStartTime = entity.visibilityStartTime;
        dto.visibilityEndTime = entity.visibilityEndTime;

        return dto;
    }
}
