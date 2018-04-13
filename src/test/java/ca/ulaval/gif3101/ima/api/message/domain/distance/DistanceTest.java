package ca.ulaval.gif3101.ima.api.message.domain.distance;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DistanceTest {

    public static final int METERS = 100;
    public static final int KILOMETERS = 10;

    public static final int METERS_TO_METERS_EXPECTED_RESULT = METERS;
    public static final double METERS_TO_KILOMETERS_EXPECTED_RESULT = 0.1;
    public static final double KILOMETERS_TO_KILOMETERS_EXPECTED_RESULT = 10.0;
    public static final double KILOMETERS_TO_METERS_EXPECTED_RESULT = 10000.0;

    public static final double ASSERT_DELTA = 0.0;


    @Test
    public void givenADistanceInMeter_whenGettingDistanceInMeter_shouldBeTheSame() {
        Distance distance = Distance.fromMeters(METERS);

        double result = distance.toMeters();

        assertEquals(METERS_TO_METERS_EXPECTED_RESULT, result, ASSERT_DELTA);
    }

    @Test
    public void givenADistanceInMeter_whenGettingDistanceInKilometer_shouldBeEquivalent() {
        Distance distance = Distance.fromMeters(METERS);

        double result = distance.toKilometers();

        assertEquals(METERS_TO_KILOMETERS_EXPECTED_RESULT, result, ASSERT_DELTA);
    }

    @Test
    public void givenADistanceInKilometer_whenGettingDistanceInKilometer_shouldBeTheSame() {
        Distance distance = Distance.fromKilometers(KILOMETERS);

        double result = distance.toKilometers();

        assertEquals(KILOMETERS_TO_KILOMETERS_EXPECTED_RESULT, result, ASSERT_DELTA);
    }

    @Test
    public void givenADistanceInKilometer_whenGettingDistanceInMeter_shouldBeEquivalent() {
        Distance distance = Distance.fromKilometers(KILOMETERS);

        double result = distance.toMeters();

        assertEquals(KILOMETERS_TO_METERS_EXPECTED_RESULT, result, ASSERT_DELTA);
    }

    @Test
    public void givenADistance_whenCheckingIfShorterDistanceIsShorterOrEqual_willReturnTrue() {
        Distance shortDistance = Distance.fromMeters(10);
        Distance longDistance = Distance.fromMeters(2000);

        boolean result = shortDistance.lesserOrEqualThan(longDistance);

        assertTrue(result);
    }

    @Test
    public void givenADistance_whenCheckingIfSameDistanceIsShorterOrEqual_willReturnTrue() {
        Distance distanceA = Distance.fromMeters(10);
        Distance distanceB = Distance.fromMeters(10);

        boolean result = distanceA.lesserOrEqualThan(distanceB);

        assertTrue(result);
    }

    @Test
    public void givenADistance_whenCheckingIfLongerDistanceIsShorterOrEqual_willReturnFalse() {
        Distance shortDistance = Distance.fromMeters(10);
        Distance longDistance = Distance.fromMeters(2000);

        boolean result = longDistance.lesserOrEqualThan(shortDistance);

        assertFalse(result);
    }
}
