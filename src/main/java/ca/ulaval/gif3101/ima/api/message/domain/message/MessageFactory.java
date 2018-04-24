package ca.ulaval.gif3101.ima.api.message.domain.message;

import ca.ulaval.gif3101.ima.api.message.domain.VisibilityPeriod.VisibilityPeriod;
import ca.ulaval.gif3101.ima.api.message.domain.author.Author;
import ca.ulaval.gif3101.ima.api.message.domain.location.Location;
import ca.ulaval.gif3101.ima.api.message.domain.location.distanceCalculator.DistanceCalculatorStrategy;
import ca.ulaval.gif3101.ima.api.message.external.date.JodaTimeDateAdapter;
import ca.ulaval.gif3101.ima.api.message.external.time.JodaTimeTimeAdapter;

public class MessageFactory {
    private MessageBuilder messageBuilder;
    private DistanceCalculatorStrategy distanceStrategy;

    public MessageFactory(MessageBuilder messageBuilder, DistanceCalculatorStrategy distanceStrategy) {
        this.messageBuilder = messageBuilder;
        this.distanceStrategy = distanceStrategy;
    }

    public Message create(MessageDto dto) {
        Location location =  new Location(
                dto.latitude,
                dto.longitude
        );

        this.messageBuilder.createMessage(
                dto.title,
                dto.body,
                new JodaTimeDateAdapter(dto.expires),
                location
        );

        if (hasVisibilityPeriod(dto)) {
            this.messageBuilder.with(new VisibilityPeriod(new JodaTimeTimeAdapter(dto.visibilityStartTime), new JodaTimeTimeAdapter(dto.visibilityEndTime)));
        }
        
        if (hasAuthor(dto.author)) {
            this.messageBuilder.with(new Author(dto.author));
        }

        Message message = this.messageBuilder.build();

        message.calculateDistanceFrom(location, distanceStrategy);

        return message;
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
