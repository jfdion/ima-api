package ca.ulaval.gif3101.ima.api.message.external.time;

import ca.ulaval.gif3101.ima.api.message.domain.time.TimeAdapter;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalTime;

public class JodaTimeTimeAdapter implements TimeAdapter {

    public static final String TIMEZONE_ID = "America/New_York";
    protected LocalTime localTime;

    public JodaTimeTimeAdapter() {
        localTime = new LocalTime(DateTimeZone.forID(TIMEZONE_ID));
    }

    public JodaTimeTimeAdapter(String time) {
        localTime = new LocalTime(time, DateTimeZone.forID(TIMEZONE_ID));
    }

    @Override
    public Integer hour() {
        return toInt(localTime.hourOfDay());
    }

    @Override
    public boolean between(TimeAdapter start, TimeAdapter end) {
        return start.before(this) && end.after(this);
    }

    @Override
    public TimeAdapter startOfDay() {
        return new JodaTimeTimeAdapter(MIDNIGHT_TIME_START);
    }

    @Override
    public TimeAdapter endOfDay() {
        return new JodaTimeTimeAdapter(MIDNIGHT_TIME_END);
    }

    @Override
    public Integer minute() {
        return toInt(localTime.minuteOfHour());
    }

    @Override
    public boolean before(TimeAdapter time) {
        LocalTime inTime = new LocalTime(time.toString());
        return localTime.isBefore(inTime);
    }

    @Override
    public boolean after(TimeAdapter time) {
        LocalTime inTime = new LocalTime(time.toString());
        return localTime.isAfter(inTime);
    }

    private Integer toInt(LocalTime.Property property) {
        return Integer.valueOf(property.getAsShortText());
    }

    public String toString() {
        return localTime.toString();
    }

    @Override
    public boolean equals(TimeAdapter timeAdapter) {
        return timeAdapter.toString().equals(localTime.toString());
    }
}
