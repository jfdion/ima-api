package ca.ulaval.gif3101.ima.api.infrastructure.message.filter;

import ca.ulaval.gif3101.ima.api.domain.message.Message;

import java.util.ArrayList;
import java.util.List;

public class MessageFilterStack implements MessageFilterComposite {

    private List<MessageFilter> messageFilters = new ArrayList<>();

    public void addFilter(MessageFilter messageFilter) {
        if (!messageFilters.contains(messageFilter)) {
            messageFilters.add(messageFilter);
        }
    }

    @Override
    public List<Message> filter(List<Message> messages, FilterConfig config) {

        for (MessageFilter messageFilter : messageFilters) {
            messages = messageFilter.filter(messages, config);
        }

        return messages;
    }
}
