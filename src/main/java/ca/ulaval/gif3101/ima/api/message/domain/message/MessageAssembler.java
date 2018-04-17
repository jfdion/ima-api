package ca.ulaval.gif3101.ima.api.message.domain.message;

import ca.ulaval.gif3101.ima.api.message.domain.VisibilityPeriod.VisibilityPeriod;
import ca.ulaval.gif3101.ima.api.message.domain.author.Author;
import ca.ulaval.gif3101.ima.api.message.domain.location.Location;
import ca.ulaval.gif3101.ima.api.message.external.date.JodaTimeDateAdapter;
import ca.ulaval.gif3101.ima.api.message.external.time.JodaTimeTimeAdapter;

public class MessageAssembler {

    public MessageDto createDto(Message message) {
        MessageDto dto = new MessageDto();

        dto.id = message.id;
        dto.author = message.author.getUsername();
        dto.title = message.title;
        dto.body = message.body;
        dto.expires = message.expires.toString();
        dto.created = message.created.toString();
        dto.latitude = message.location.getLatitude();
        dto.longitude = message.location.getLongitude();
        if (message.visibilityPeriod != null) {
            dto.visibilityEndTime = message.visibilityPeriod.end().toString();
            dto.visibilityStartTime = message.visibilityPeriod.start().toString();
        }

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
        message.author = new Author(dto.author);
        message.title = dto.title;
        message.body = dto.body;
        message.location = new Location(dto.latitude, dto.longitude);
        message.created = new JodaTimeDateAdapter(dto.created);
        message.expires = new JodaTimeDateAdapter(dto.expires);
        if (dto.visibilityStartTime != null && dto.visibilityEndTime != null) {
            message.visibilityPeriod = new VisibilityPeriod(new JodaTimeTimeAdapter(dto.visibilityStartTime), new JodaTimeTimeAdapter(dto.visibilityEndTime));
        }
    }
}
