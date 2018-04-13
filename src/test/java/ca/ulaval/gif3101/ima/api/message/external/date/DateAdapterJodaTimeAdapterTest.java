package ca.ulaval.gif3101.ima.api.message.external.date;

import ca.ulaval.gif3101.ima.api.message.domain.date.DateAdapter;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DateAdapterJodaTimeAdapterTest {

    @Test
    public void givenADateAdapter_whenCheckingIfBeforeDateIsBefore_willReturnTrue() {
        DateAdapter now = new JodaTimeDateAdapter();
        DateAdapter past = new JodaTimeDateAdapter("1999-01-01");

        boolean result = past.before(now);

        assertTrue(result);
    }

    @Test
    public void givenADateAdapter_whenCheckingIfAfterDateIsBefore_willReturnFalse() {
        DateAdapter now = new JodaTimeDateAdapter();
        DateAdapter past = new JodaTimeDateAdapter("1999-01-01");

        boolean result = now.before(past);

        assertFalse(result);
    }
}
