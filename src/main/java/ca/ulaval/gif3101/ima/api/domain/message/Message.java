package ca.ulaval.gif3101.ima.api.domain.message;

import ca.ulaval.gif3101.ima.api.domain.Distance.Distance;
import ca.ulaval.gif3101.ima.api.domain.location.Location;
import ca.ulaval.gif3101.ima.api.domain.date.Date;
import ca.ulaval.gif3101.ima.api.domain.location.distanceCalculator.DistanceCalculatorStrategy;

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

    public boolean expired(Date date) {
        return this.expires.before(date);
    }

    public boolean created(Date date) {
        return this.created.before(date);
    }

    public boolean insideRadius(Distance radius, Location center, DistanceCalculatorStrategy strategy) {
        return strategy.calculate(center, location).lesserOrEqualThan(radius);
    }
}
