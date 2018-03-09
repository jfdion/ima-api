package ca.ulaval.gif3101.ima.api.infrastructure.message.filter;

import ca.ulaval.gif3101.ima.api.domain.date.Date;
import ca.ulaval.gif3101.ima.api.domain.message.Message;

import java.util.ArrayList;
import java.util.List;

public class CreatedFilter implements MessageFilter {

    @Override
    public List<Message> filter(List<Message> messages, FilterConfig config) {
        if (config.isCreated == false) {
            return messages;
        }
        List<Message> results = new ArrayList<>();

        Date now = new Date();
        for (Message message : messages) {
            if (message.created(now)) {
                results.add(message);
            }
        }

        return results;
    }
}
