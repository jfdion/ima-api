package ca.ulaval.gif3101.ima.api.domain.date;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

public class DateJodaTimeAdapter implements Date {

    private DateTime dateTime;

    public DateJodaTimeAdapter() {
        dateTime = new DateTime(DateTimeZone.UTC);
    }

    public DateJodaTimeAdapter(String timestamp) {
        dateTime = new DateTime(timestamp, DateTimeZone.UTC);
    }

    @Override
    public String toString() {
        return dateTime.toDateTimeISO().toString();
    }

    @Override
    public boolean before(DateJodaTimeAdapter date) {
        return dateTime.isBefore(date.dateTime);
    }
}
