package ca.ulaval.gif3101.ima.api.domain.message;

import ca.ulaval.gif3101.ima.api.domain.location.Location;
import ca.ulaval.gif3101.ima.api.domain.date.DateJodaTimeAdapter;

public class MessageAssembler {

    public MessageDto createDto(Message message) {
        MessageDto dto = new MessageDto();

        dto.id = message.id;
        dto.title = message.title;
        dto.body = message.body;
        dto.expires = message.expires.toString();
        dto.created = message.created.toString();
        dto.latitude = message.location.getLatitude();
        dto.longitude = message.location.getLongitude();

        return dto;
    }

    public Message create(MessageDto dto) {
        Message message = new Message();

        dtoToMessage(dto, message);

        return message;
    }

    public Message updateMessage(Message message, MessageDto dto) {
        dtoToMessage(dto, message);

        return message;
    }

    private void dtoToMessage(MessageDto dto, Message message) {
        message.id = dto.id;
        message.title = dto.title;
        message.body = dto.body;
        message.location = new Location(dto.latitude, dto.longitude);
        message.created = new DateJodaTimeAdapter(dto.created);
        message.expires = new DateJodaTimeAdapter(dto.expires);
    }


}
