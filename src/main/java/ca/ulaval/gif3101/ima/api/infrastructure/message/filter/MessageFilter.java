package ca.ulaval.gif3101.ima.api.infrastructure.message.filter;

import ca.ulaval.gif3101.ima.api.domain.message.Message;

import java.util.List;

public interface MessageFilter {
    List<Message> filter(List<Message> messages, FilterConfig config);
}
