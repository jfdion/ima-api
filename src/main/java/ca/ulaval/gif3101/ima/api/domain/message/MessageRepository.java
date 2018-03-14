package ca.ulaval.gif3101.ima.api.domain.message;

import ca.ulaval.gif3101.ima.api.domain.message.filter.FilterConfig;

import java.util.List;

public interface MessageRepository {

    void save(Message message) throws Exception;
    List<Message> findAll();

    List<Message> findFiltered(FilterConfig config);

    Message findOneById(String id) throws Exception;
}
