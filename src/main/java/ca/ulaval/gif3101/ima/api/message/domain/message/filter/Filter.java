package ca.ulaval.gif3101.ima.api.message.domain.message.filter;

import ca.ulaval.gif3101.ima.api.message.domain.message.Message;
import ca.ulaval.gif3101.ima.api.message.domain.message.query.MessageQuery;

import java.util.List;

public interface Filter {
    List<Message> filter(List<Message> messages, MessageQuery query);
}
