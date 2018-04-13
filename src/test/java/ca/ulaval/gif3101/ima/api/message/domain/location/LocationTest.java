package ca.ulaval.gif3101.ima.api.message.domain.location;

import ca.ulaval.gif3101.ima.api.message.domain.distance.Distance;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class LocationTest {

    private static final int INIT_LATITUDE = 50;
    private static final int INIT_LONGITUDE = -75;
    private static final String INIT_LOCATION_STRING = "50.99,-70.78";
    private static final String INIT_LOCATION_STRING_WITH_SPACE = "50.99, -70.78";

    private static final double EXPECTED_LATITUDE = 50.08993216059187;
    private static final double EXPECTED_LONGITUDE = -74.90905875672802;
    private static final double EXPECTED_STRING_LATITUDE_VALUE = 50.99;
    private static final double EXPECTED_STRING_LONGITUDE_VALUE = -70.78;
    private static final double EXPECTED_TOP_LEFT_LATITUDE = 49.91006783940813;
    private static final double EXPECTED_TOP_LEFT_LONGITUDE = -75.09094124327198;
    private static final double EXPECTED_BOTTOM_RIGHT_LATITUDE = 50.08993216059187;
    private static final double EXPECTED_BOTTOM_RIGHT_LONGITUDE = -74.90905875672802;

    @Mock
    private Distance latitudeDistance;

    @Mock
    private Distance longitudeDistance;

    private Location initialLocation;

    @Before
    public void setUp() {
        given(latitudeDistance.toKilometers()).willReturn(10.0);
        given(latitudeDistance.toMeters()).willReturn(10000.0);
        given(longitudeDistance.toKilometers()).willReturn(6.5);
        given(longitudeDistance.toMeters()).willReturn(6500.0);

        initialLocation = new Location(INIT_LATITUDE, INIT_LONGITUDE);
    }


    @Test
    public void givenALocation_whenAddingDistance_newLocationShouldReflectChanges() {
        Location result = initialLocation.add(latitudeDistance, longitudeDistance);

        Location expected = new Location(EXPECTED_LATITUDE, EXPECTED_LONGITUDE);

        assertTrue(result.equals(expected));
    }

    @Test
    public void givenALocation_whenAddingDistance_initialLocationShouldRemainUnchanged() {
        Location result = initialLocation.add(latitudeDistance, longitudeDistance);

        Location expected = new Location(INIT_LATITUDE, INIT_LONGITUDE);
        assertTrue(initialLocation.equals(expected));
    }

    @Test
    public void givenALocationString_whenCreating_shouldBeAValidLocation() {
        Location location = new Location(INIT_LOCATION_STRING);

        Location expected = new Location(EXPECTED_STRING_LATITUDE_VALUE, EXPECTED_STRING_LONGITUDE_VALUE);
        assertTrue(location.equals(expected));
    }

    @Test
    public void givenALocationStringWithSpace_whenCreating_shouldBeAValidLocation() {
        Location location = new Location(INIT_LOCATION_STRING_WITH_SPACE);

        Location expected = new Location(EXPECTED_STRING_LATITUDE_VALUE, EXPECTED_STRING_LONGITUDE_VALUE);
        assertTrue(location.equals(expected));
    }

    @Test
    public void givenALocation_whenCreatingASqureBoundingBox_BoundingBoxShouldBeOfTheRightDimensions() {
        BoundingBox boundingBox = initialLocation.createBoundingBox(latitudeDistance, longitudeDistance);

        Location expectedTopLeft = new Location(EXPECTED_TOP_LEFT_LATITUDE, EXPECTED_TOP_LEFT_LONGITUDE);
        Location expectedBottomRight = new Location(EXPECTED_BOTTOM_RIGHT_LATITUDE, EXPECTED_BOTTOM_RIGHT_LONGITUDE);
        assertTrue(boundingBox.topLeft().equals(expectedTopLeft));
        assertTrue(boundingBox.bottomRight().equals(expectedBottomRight));
    }
}
