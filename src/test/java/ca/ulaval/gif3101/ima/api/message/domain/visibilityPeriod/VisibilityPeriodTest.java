package ca.ulaval.gif3101.ima.api.message.domain.visibilityPeriod;

import ca.ulaval.gif3101.ima.api.message.domain.VisibilityPeriod.VisibilityPeriod;
import ca.ulaval.gif3101.ima.api.message.domain.time.TimeAdapter;
import ca.ulaval.gif3101.ima.api.message.external.time.JodaTimeTimeAdapter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class VisibilityPeriodTest {

    private TimeAdapter startTime;

    private TimeAdapter endTime;

    private TimeAdapter currentTime;

    private VisibilityPeriod visibilityPeriodStartLower;

    private VisibilityPeriod visibilityPeriodStartGreater;

    @Before
    public void setUp() {
        visibilityPeriodStartLower = new VisibilityPeriod(new JodaTimeTimeAdapter("10:00"), new JodaTimeTimeAdapter("13:00"));
        visibilityPeriodStartGreater = new VisibilityPeriod(new JodaTimeTimeAdapter("14:00"), new JodaTimeTimeAdapter("9:00"));

    }

    @Test
    public void givenAVisibilityPeriod_whenStartTimeIsLowerThanEndTimeAndCurrentTimeInBetween_willBeVisible() {
        boolean result = visibilityPeriodStartLower.isVisible(new JodaTimeTimeAdapter("12:00"));

        assertTrue(result);
    }

    @Test
    public void givenAVisibilityPeriod_whenStartTimeIsLowerThanEndTimeAndCurrentTimeLowerThanStart_willBeInvisible() {
        boolean result = visibilityPeriodStartLower.isVisible(new JodaTimeTimeAdapter("9:00"));

        assertFalse(result);
    }

    @Test
    public void givenAVisibilityPeriod_whenStartTimeIsLowerThanEndTimeAndCurrentTimeGreaterThanEnd_willBeInvisible() {
        boolean result = visibilityPeriodStartLower.isVisible(new JodaTimeTimeAdapter("18:00"));

        assertFalse(result);
    }

    @Test
    public void givenAVisibilityPeriod_whenStartTimeIsGreaterThanEndTimeAndCurrentTimeInBetweenOnFirstDay_willBeVisible() {
        boolean result = visibilityPeriodStartGreater.isVisible(new JodaTimeTimeAdapter("18:00"));

        assertTrue(result);
    }

    @Test
    public void givenAVisibilityPeriod_whenStartTimeIsGreaterThanEndTimeAndCurrentTimeInBetweenOnSecondDay_willBeVisible() {
        boolean result = visibilityPeriodStartGreater.isVisible(new JodaTimeTimeAdapter("02:00"));

        assertTrue(result);
    }


    @Test
    public void givenAVisibilityPeriod_whenStartTimeIsGreaterThanEndTimeAndCurrentTimeLowerOnFirstDay_willBeInisible() {
        boolean result = visibilityPeriodStartGreater.isVisible(new JodaTimeTimeAdapter("13:00"));

        assertFalse(result);
    }

    @Test
    public void givenAVisibilityPeriod_whenStartTimeIsGreaterThanEndTimeAndCurrentTimeGreaterOnSecondDay_willBeInvisible() {
        boolean result = visibilityPeriodStartGreater.isVisible(new JodaTimeTimeAdapter("12:00"));

        assertFalse(result);
    }


}
