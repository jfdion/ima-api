package ca.ulaval.gif3101.ima.api.domain.message;

import ca.ulaval.gif3101.ima.api.domain.location.Location;

import java.util.List;

public interface MessageRepository {

    void save(Message message) throws Exception;
    List<Message> findAll();
    List<Message> findByLocation(Location location) throws Exception;
}
