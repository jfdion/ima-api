package ca.ulaval.gif3101.ima.api.domain.message;

import ca.ulaval.gif3101.ima.api.controller.message.dto.MessageDto;
import ca.ulaval.gif3101.ima.api.domain.location.Location;
import ca.ulaval.gif3101.ima.api.domain.date.Date;
import ca.ulaval.gif3101.ima.api.infrastructure.message.dto.MessageEntity;

public class MessageAssembler {

    public MessageEntity createEntity(Message message) {
        MessageEntity entity = new MessageEntity();

        entity.id = message.id;
        entity.title = message.title;
        entity.body = message.body;
        entity.expires = message.expires.toString();
        entity.created = message.created.toString();
        entity.latitude = message.location.getLatitude();
        entity.longitude = message.location.getLongitude();

        return entity;
    }

    public Message create(MessageEntity entity) {
        Message message = new Message();

        entityToMessage(entity, message);

        return message;
    }

    public MessageDto createDto(Message message) {
        MessageDto dto = new MessageDto();

        dto.id = message.id;
        dto.title = message.title;
        dto.body = message.body;
        dto.expires = message.expires.toString();
        dto.created = message.created.toString();
        dto.latitude = message.location.getLatitude();
        dto.longitude = message.location.getLongitude();

        dto.updateGoogleMaps();

        return dto;
    }

    public Message create(MessageDto dto) {
        Message message = new Message();

        dtoToMessage(dto, message);

        return message;
    }

    public Message updateMessage(Message message, MessageEntity entity) {
        entityToMessage(entity, message);

        return message;
    }

    private void entityToMessage(MessageEntity entity, Message message) {
        message.id = entity.id;
        message.title = entity.title;
        message.body = entity.body;
        message.location = new Location(entity.latitude, entity.longitude);
        message.created = new Date(entity.created);
        message.expires = new Date(entity.expires);
    }



    private void dtoToMessage(MessageDto dto, Message message) {
        message.id = dto.id;
        message.title = dto.title;
        message.body = dto.body;
        message.location = new Location(dto.latitude, dto.longitude);
        message.created = new Date(dto.created);
        message.expires = new Date(dto.expires);
    }


}
