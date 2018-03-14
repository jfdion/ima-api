package ca.ulaval.gif3101.ima.api.domain.message.filter;

import ca.ulaval.gif3101.ima.api.domain.date.DateJodaTimeAdapter;
import ca.ulaval.gif3101.ima.api.domain.message.Message;

import java.util.ArrayList;
import java.util.List;

public class CreatedFilter implements Filter {

    @Override
    public List<Message> filter(List<Message> messages, FilterConfig config) {
        if (config.isCreated == false) {
            return messages;
        }
        List<Message> results = new ArrayList<>();

        DateJodaTimeAdapter now = new DateJodaTimeAdapter();
        for (Message message : messages) {
            if (message.created(now)) {
                results.add(message);
            }
        }

        return results;
    }
}
