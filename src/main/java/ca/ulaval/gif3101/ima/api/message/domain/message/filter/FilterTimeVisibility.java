package ca.ulaval.gif3101.ima.api.message.domain.message.filter;

import ca.ulaval.gif3101.ima.api.message.domain.message.Message;
import ca.ulaval.gif3101.ima.api.message.domain.message.query.MessageQuery;
import ca.ulaval.gif3101.ima.api.message.external.date.JodaTimeDateAdapter;
import ca.ulaval.gif3101.ima.api.message.external.time.JodaTimeTimeAdapter;

import java.util.ArrayList;
import java.util.List;

public class FilterTimeVisibility  implements Filter {

    @Override
    public List<Message> filter(List<Message> messages, MessageQuery query) {
        if (!query.hasTimeVisibility()) {
            return messages;
        }
        List<Message> results = new ArrayList<>();

        JodaTimeTimeAdapter now = new JodaTimeTimeAdapter();
        for (Message message : messages) {
            if (message.isVisible(now)) {
                results.add(message);
            }
        }

        return results;
    }
}