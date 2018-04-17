package ca.ulaval.gif3101.ima.api.message.infrastructure.message.dto;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Property;

@Entity(value="message")
public class MessageEntity {

    @Id
    public ObjectId id;

    public String author;

    public String title;

    public String body;

    public String expires;

    public String created;

    public double latitude;

    public double longitude;

    @Property("visibility-start-time")
    public String visibilityStartTime;

    @Property("visibility-end-time")
    public String visibilityEndTime;

}
