package ca.ulaval.gif3101.ima.api.message.domain.message;

import ca.ulaval.gif3101.ima.api.message.domain.VisibilityPeriod.VisibilityPeriod;
import ca.ulaval.gif3101.ima.api.message.domain.date.DateAdapter;
import ca.ulaval.gif3101.ima.api.message.domain.location.Location;

public class MessageBuilder {

    protected String title;
    protected String body;
    protected DateAdapter expires;
    protected Location location;
    protected VisibilityPeriod visibilityPeriod;

    public MessageBuilder createMessage(String title, String body, DateAdapter expires, Location location) {
        this.title = title;
        this.body = body;
        this.expires = expires;
        this.location = location;
        return this;
    }

    public MessageBuilder with(VisibilityPeriod visibilityPeriod) {
        this.visibilityPeriod = visibilityPeriod;
        return this;
    }


    public Message build() {
        Message message;
        if (visibilityPeriod != null) {
            message = new Message(title, body, expires, location, visibilityPeriod);
        } else {
            message = new Message(title, body, expires, location);
        }

        clear();

        return message;
    }

    private void clear() {
        title = null;
        body = null;
        expires = null;
        location = null;
        visibilityPeriod = null;
    }
}
