package ca.ulaval.gif3101.ima.api.message.infrastructure.message.filter;

import ca.ulaval.gif3101.ima.api.message.domain.message.Message;
import ca.ulaval.gif3101.ima.api.message.domain.message.filter.Filter;
import ca.ulaval.gif3101.ima.api.message.domain.message.query.MessageQuery;

import java.util.List;

public class FilterStub implements Filter {

    @Override
    public List<Message> filter(List<Message> messages, MessageQuery query) {
        return messages;
    }
}
