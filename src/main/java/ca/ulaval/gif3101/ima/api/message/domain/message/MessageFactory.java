package ca.ulaval.gif3101.ima.api.message.domain.message;

import ca.ulaval.gif3101.ima.api.message.domain.VisibilityPeriod.VisibilityPeriod;
import ca.ulaval.gif3101.ima.api.message.domain.author.Author;
import ca.ulaval.gif3101.ima.api.message.domain.location.Location;
import ca.ulaval.gif3101.ima.api.message.external.date.JodaTimeDateAdapter;
import ca.ulaval.gif3101.ima.api.message.external.time.JodaTimeTimeAdapter;

public class MessageFactory {
    private MessageBuilder messageBuilder;

    public MessageFactory(MessageBuilder messageBuilder) {
        this.messageBuilder = messageBuilder;
    }

    public Message create(MessageDto dto) {
        this.messageBuilder.createMessage(
                dto.title,
                dto.body,
                new JodaTimeDateAdapter(dto.expires),
                new Location(
                        dto.latitude,
                        dto.longitude
                )
        );

        if (hasVisibilityPeriod(dto)) {
            this.messageBuilder.with(new VisibilityPeriod(new JodaTimeTimeAdapter(dto.visibilityStartTime), new JodaTimeTimeAdapter(dto.visibilityEndTime)));
        }
        
        if (hasAuthor(dto.author)) {
            this.messageBuilder.with(new Author(dto.author));
        }

        return this.messageBuilder.build();
    }

    private boolean hasAuthor(String author) {
        return author != null && !author.isEmpty();
    }

    private boolean hasVisibilityPeriod(MessageDto dto) {
        return hasVisibilityStartTime(dto) && hasVisibilityEndTime(dto) && startAndEndAreDifferent(dto);
    }

    private boolean startAndEndAreDifferent(MessageDto dto) {
        return !dto.visibilityStartTime.equals(dto.visibilityEndTime);
    }

    private boolean hasVisibilityEndTime(MessageDto dto) {
        return dto.visibilityEndTime != null && !dto.visibilityEndTime.isEmpty();
    }

    private boolean hasVisibilityStartTime(MessageDto dto) {
        return dto.visibilityStartTime != null && !dto.visibilityStartTime.isEmpty();
    }
}
