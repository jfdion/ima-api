package ca.ulaval.gif3101.ima.api.message.domain.message;

import ca.ulaval.gif3101.ima.api.message.domain.VisibilityPeriod.VisibilityPeriod;
import ca.ulaval.gif3101.ima.api.message.domain.author.Author;
import ca.ulaval.gif3101.ima.api.message.domain.date.DateAdapter;
import ca.ulaval.gif3101.ima.api.message.domain.distance.Distance;
import ca.ulaval.gif3101.ima.api.message.domain.location.Location;
import ca.ulaval.gif3101.ima.api.message.domain.location.LocationScope;
import ca.ulaval.gif3101.ima.api.message.domain.location.distanceCalculator.DistanceCalculatorStrategy;
import ca.ulaval.gif3101.ima.api.message.domain.time.TimeAdapter;
import ca.ulaval.gif3101.ima.api.message.external.date.JodaTimeDateAdapter;
import ca.ulaval.gif3101.ima.api.message.infrastructure.id.IdGenerator;

public class Message {

    public static final String UNREADABLE_MESSAGE = "< unreadable message, move closer to view >";
    protected String id = null;

    protected Author author;
    protected String title;
    protected String body;
    protected DateAdapter expires;
    protected DateAdapter created;
    protected Location location;
    protected VisibilityPeriod visibilityPeriod;

    protected Distance distanceFrom = Distance.fromKilometers(Double.POSITIVE_INFINITY);
    protected Location distanceCalculatedFrom;

    private Distance readableDistance = LocationScope.CLOSE_DISTANCE;

    public Message(Author author, String title, String body, DateAdapter expires, Location location) {
        this.author = author;
        this.title = title;
        this.body = body;
        this.expires = expires;
        this.location = location;
        this.created = new JodaTimeDateAdapter();
    }

    public Message(Author author, String title, String body, DateAdapter expires, Location location, VisibilityPeriod visibilityPeriod) {
        this.author = author;
        this.title = title;
        this.body = body;
        this.expires = expires;
        this.location = location;
        this.created = new JodaTimeDateAdapter();
        this.visibilityPeriod = visibilityPeriod;
    }

    protected Message() {
    }

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

    public String getId() {
        return id;
    }

    public Author getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public DateAdapter getExpires() {
        return expires;
    }

    public DateAdapter getCreated() {
        return created;
    }

    public Location getLocation() {
        return location;
    }

    public VisibilityPeriod getVisibilityPeriod() {
        return visibilityPeriod;
    }

    public void calculateDistanceFrom(Location location, DistanceCalculatorStrategy strategy) {
        distanceFrom = strategy.calculate(location, this.location);
        distanceCalculatedFrom = location;
    }

    protected boolean hasCalculatedDistance() {
        return distanceCalculatedFrom != null;
    }

    public Location getCalculatedDistanceLocation() {
        return distanceCalculatedFrom;
    }

    public Distance getCalculatedDistance() {
        return distanceFrom;
    }

    public boolean isReadable() {
        return distanceFrom.lesserOrEqualThan(this.readableDistance);
    }

    public Distance getReadableDistance() {
        return this.readableDistance;
    }

    public String getReadableBody() {
        if (isReadable()) {
            return body;
        } else {
            return UNREADABLE_MESSAGE;
        }
    }

    public boolean isInsideScope(String scope) {
        switch (scope) {
            case LocationScope.CLOSE:
                return distanceFrom.lesserOrEqualThan(LocationScope.CLOSE_DISTANCE);
            case LocationScope.MEDIUM:
                return distanceFrom.lesserOrEqualThan(LocationScope.MEDIUM_DISTANCE);
            case LocationScope.GENERAL:
                return distanceFrom.lesserOrEqualThan(LocationScope.GENERAL_DISTANCE);
            default:
                return distanceFrom.lesserOrEqualThan(LocationScope.MEDIUM_DISTANCE);

        }
    }
}
