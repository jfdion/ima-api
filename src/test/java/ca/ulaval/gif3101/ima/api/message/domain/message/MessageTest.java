package ca.ulaval.gif3101.ima.api.message.domain.message;

import ca.ulaval.gif3101.ima.api.message.domain.date.DateAdapter;
import ca.ulaval.gif3101.ima.api.message.domain.distance.Distance;
import ca.ulaval.gif3101.ima.api.message.domain.location.Location;
import ca.ulaval.gif3101.ima.api.message.domain.location.distanceCalculator.DistanceCalculatorStrategy;
import ca.ulaval.gif3101.ima.api.message.infrastructure.id.IdGenerator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class MessageTest {

    public static final String SOME_ID = "1";
    public static final String SOME_TITLE = "Some title";
    public static final String SOME_BODY = "Some body";
    public static final String SOME_OTHER_ID = "2";
    @Mock
    private DateAdapter today;

    @Mock
    private DateAdapter expires;

    @Mock
    private DateAdapter created;

    @Mock
    private Location location;

    @Mock
    private Distance resultDistance;

    @Mock
    private IdGenerator idGenerator;

    @Mock
    private DistanceCalculatorStrategy distanceCalculatorStrategy;

    private Message message;

    @Before
    public void setUp() {
        given(idGenerator.generate()).willReturn(SOME_ID);
        given(distanceCalculatorStrategy.calculate(any(Location.class), any(Location.class))).willReturn(resultDistance);

        message = new Message(SOME_TITLE, SOME_BODY, expires, location);
    }

    @Test
    public void givenAMessageWithExpireDate_whenVerifiyingExpiredWithToday_shouldCompareDateWithToday() {
        message.expired(today);

        verify(expires, times(1)).before(today);
    }

    @Test
    public void givenAMessageWithCreatedDate_whenVerifyingCreatedWithToday_shouldCompareCreatedWithToday() {
        message.changeCreated(created);

        message.created(today);

        verify(created, times(1)).before(today);
    }

    @Test
    public void givenAMessageWithoutId_whenAssigningId_shouldUseIdGenerator() {
        message.assignId(idGenerator);

        verify(idGenerator, times(1)).generate();
    }

    @Test
    public void givenAMessageWithId_whenAssigningId_shouldNotReassignId() {
        message.assignId(idGenerator);

        message.assignId(idGenerator);

        verify(idGenerator, times(1)).generate();
    }

    @Test
    public void givenAMessageWithoutId_whenVerifyingIfNullIdIsEqual_shouldReturnFalse() {
        boolean result = message.idEquals(null);

        assertFalse(result);
    }

    @Test
    public void givenAMEssageWithoutId_whenVerifyingIfIdIsEquals_shouldReturnFalse() {
        boolean result = message.idEquals(SOME_ID);

        assertFalse(result);
    }

    @Test
    public void givenAMessageWithId_whenVerifyingWithSameId_shouldReturnTrue() {
        message.assignId(idGenerator);
        boolean result = message.idEquals(SOME_ID);

        assertTrue(result);
    }

    @Test
    public void givenAMessageWithId_whenVerifyingWithDifferentId_shouldReturnFalse() {
        message.assignId(idGenerator);
        boolean result = message.idEquals(SOME_OTHER_ID);

        assertFalse(result);
    }

    @Test
    public void givenAMessageWithoutId_whenVerifyingIfHasId_shouldReturnFalse() {
        boolean result = message.hasId();

        assertFalse(result);
    }

    @Test
    public void givenAMessageWithId_whenVerifiyingIfHasId_shouldReturnTrue() {
        message.assignId(idGenerator);
        boolean result = message.hasId();

        assertTrue(result);
    }
}
