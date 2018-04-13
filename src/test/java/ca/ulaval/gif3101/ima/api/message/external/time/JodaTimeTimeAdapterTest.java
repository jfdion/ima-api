package ca.ulaval.gif3101.ima.api.message.external.time;

import ca.ulaval.gif3101.ima.api.message.domain.time.TimeAdapter;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalTime;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class JodaTimeTimeAdapterTest {

    @Test
    public void givenATimeAdapter_whenCreatedWithEmptyTime_currentTimeIsUsed() {
        TimeAdapter timeAdapter = new JodaTimeTimeAdapter();

        LocalTime expected = new LocalTime(DateTimeZone.forID(JodaTimeTimeAdapter.TIMEZONE_ID));

        assertEquals(toInt(expected.hourOfDay().getAsShortText()), timeAdapter.hour());
        assertEquals(toInt(expected.minuteOfHour().getAsShortText()), timeAdapter.minute());
    }

    private Integer toInt(String string) {
        return Integer.valueOf(string);
    }

    @Test
    public void givenATimeAdapter_whenCreatedWithTime_providedTimeIsUsed() {
        TimeAdapter timeAdapter = new JodaTimeTimeAdapter("18:23");

        LocalTime expected = new LocalTime("18:23");

        assertEquals(toInt(expected.hourOfDay().getAsShortText()), timeAdapter.hour());
        assertEquals(toInt(expected.minuteOfHour().getAsShortText()), timeAdapter.minute());
    }

    @Test
    public void givenATimeAdapter_whenComparingIfBeforeTimeIsBefore_willReturnTrue() {
        TimeAdapter beforeTime = new JodaTimeTimeAdapter("11:51");
        TimeAdapter laterTime = new JodaTimeTimeAdapter("22:12");

        assertTrue(beforeTime.before(laterTime));
    }

    @Test
    public void givenATimeAdapter_whenComparingIfLaterTimeIsBefore_willReturnFalse() {
        TimeAdapter beforeTime = new JodaTimeTimeAdapter("11:51");
        TimeAdapter laterTime = new JodaTimeTimeAdapter("22:12");

        assertFalse(laterTime.before(beforeTime));
    }

    @Test
    public void givenATimeAdapter_whenComparingIfEqualTimeIsBefore_willReturnTrue() {
        TimeAdapter beforeTime = new JodaTimeTimeAdapter("11:51");
        TimeAdapter equalTime = new JodaTimeTimeAdapter("11:51");

        assertFalse(beforeTime.before(equalTime));
    }

    @Test
    public void givenATimeAdapter_whenComparingIfLaterTimeIsAfter_willReturnTrue() {
        TimeAdapter beforeTime = new JodaTimeTimeAdapter("11:51");
        TimeAdapter laterTime = new JodaTimeTimeAdapter("22:12");

        assertTrue(laterTime.after(beforeTime));
    }

    @Test
    public void givenATimeAdapter_whenComparingIfBeforeTimeIsLater_willReturnFalse() {
        TimeAdapter beforeTime = new JodaTimeTimeAdapter("11:51");
        TimeAdapter laterTime = new JodaTimeTimeAdapter("22:12");

        assertFalse(beforeTime.after(laterTime));
    }

    @Test
    public void givenATimeAdapter_whenComparingIfEqualTimeIsAfter_willReturnTrue() {
        TimeAdapter afterTime = new JodaTimeTimeAdapter("11:51");
        TimeAdapter equalTime = new JodaTimeTimeAdapter("11:51");

        assertFalse(afterTime.after(equalTime));
    }
}
