package ca.ulaval.gif3101.ima.api.message.external.date;

import ca.ulaval.gif3101.ima.api.message.domain.date.DateAdapter;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

public class JodaTimeDateAdapter implements DateAdapter {

    protected DateTime dateTime;

    public JodaTimeDateAdapter() {
        dateTime = new DateTime(DateTimeZone.UTC);
    }

    public JodaTimeDateAdapter(java.util.Date date) {
        dateTime = new DateTime(date, DateTimeZone.UTC);
    }

    public JodaTimeDateAdapter(String timestamp) {
        dateTime = new DateTime(timestamp, DateTimeZone.UTC);
    }

    @Override
    public String toString() {
        return dateTime.toDateTimeISO().toString();
    }

    @Override
    public boolean before(DateAdapter dateAdapter) {
        JodaTimeDateAdapter djta = (JodaTimeDateAdapter) dateAdapter;
        return dateTime.isBefore(djta.dateTime);
    }

    @Override
    public java.util.Date toDate() {
        return dateTime.toDate();
    }
}
