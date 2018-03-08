package ca.ulaval.gif3101.ima.api.domain.date;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

public class Date {

    private DateTime dateTime;

    public Date() {
        dateTime = new DateTime(DateTimeZone.UTC);
    }

    public Date(String timestamp) {
        dateTime = new DateTime(timestamp, DateTimeZone.UTC);
    }

    public String toString() {
        return dateTime.toDateTimeISO().toString();
    }
}
