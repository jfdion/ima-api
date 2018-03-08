package ca.ulaval.gif3101.ima.api.domain.message;

import ca.ulaval.gif3101.ima.api.domain.location.Location;
import ca.ulaval.gif3101.ima.api.domain.date.Date;

public class Message {

    protected String id;

    protected String title;
    protected String body;
    protected Date expires;
    protected Date created;
    protected Location location;

    public Message(String title, String body, Date expires, Location location) {
        this.title = title;
        this.body = body;
        this.expires = expires;
        this.location = location;
        this.created = new Date();
    }

    protected Message() {}

    public String getId() {
        return id;
    }
}
