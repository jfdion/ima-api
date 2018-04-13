package ca.ulaval.gif3101.ima.api.message.domain.message;

import ca.ulaval.gif3101.ima.api.message.domain.VisibilityPeriod.VisibilityPeriod;
import ca.ulaval.gif3101.ima.api.message.domain.date.DateAdapter;
import ca.ulaval.gif3101.ima.api.message.domain.location.Location;
import ca.ulaval.gif3101.ima.api.message.domain.time.TimeAdapter;
import ca.ulaval.gif3101.ima.api.message.external.date.JodaTimeDateAdapter;
import ca.ulaval.gif3101.ima.api.message.infrastructure.id.IdGenerator;

public class Message {

    protected String id = null;

    protected String title;
    protected String body;
    protected DateAdapter expires;
    protected DateAdapter created;
    protected Location location;
    protected VisibilityPeriod visibilityPeriod;

    public Message(String title, String body, DateAdapter expires, Location location) {
        this.title = title;
        this.body = body;
        this.expires = expires;
        this.location = location;
        this.created = new JodaTimeDateAdapter();
    }

    public Message(String title, String body, DateAdapter expires, Location location, VisibilityPeriod visibilityPeriod) {
        this.title = title;
        this.body = body;
        this.expires = expires;
        this.location = location;
        this.created = new JodaTimeDateAdapter();
        this.visibilityPeriod = visibilityPeriod;
    }

    protected Message() {}

    public boolean expired(DateAdapter date) {
        return this.expires.before(date);
    }

    public boolean created(DateAdapter date) {
        return this.created.before(date);
    }

    public void changeCreated(DateAdapter date) {
        this.created = date;
    }

    public boolean isVisible(TimeAdapter time) {
        return visibilityPeriod == null || visibilityPeriod.isVisible(time);
    }

    public boolean hasVisibilityPeriod() {
        return visibilityPeriod != null;
    }

    public boolean idEquals(String id) {
        return this.id != null && this.id.equals(id);
    }

    public boolean hasId() {
        return id != null;
    }

    public void assignId(IdGenerator idGenerator) {
        if (id == null) {
            id = idGenerator.generate();
        }
    }

    public Location location() {
        return location;
    }
}
