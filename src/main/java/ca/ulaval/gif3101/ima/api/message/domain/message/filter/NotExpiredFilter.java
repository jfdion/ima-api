package ca.ulaval.gif3101.ima.api.message.domain.message.filter;

import ca.ulaval.gif3101.ima.api.message.domain.message.query.MessageQuery;
import ca.ulaval.gif3101.ima.api.message.external.date.JodaTimeDateAdapter;
import ca.ulaval.gif3101.ima.api.message.domain.message.Message;

import java.util.ArrayList;
import java.util.List;

public class NotExpiredFilter implements Filter {

    @Override
    public List<Message> filter(List<Message> messages, MessageQuery query) {
        if (query.isExpired()) {
            return messages;
        }

        List<Message> results = new ArrayList<>();
        JodaTimeDateAdapter now = new JodaTimeDateAdapter();

        for (Message message : messages) {
            if (!message.expired(now)) {
                results.add(message);
            }
        }

        return results;
    }
}
