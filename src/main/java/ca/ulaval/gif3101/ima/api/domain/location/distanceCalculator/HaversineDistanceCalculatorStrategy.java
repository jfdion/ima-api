package ca.ulaval.gif3101.ima.api.domain.location.distanceCalculator;

import ca.ulaval.gif3101.ima.api.domain.distance.Distance;
import ca.ulaval.gif3101.ima.api.domain.location.Earth;
import ca.ulaval.gif3101.ima.api.domain.location.Location;

public class HaversineDistanceCalculatorStrategy implements DistanceCalculatorStrategy {

    private static final int EARTH_RADIUS_KM = 6371;

    @Override
    public Distance calculate(Location start, Location end) {
        double dLat = Math.toRadians((end.getLatitude() - start.getLatitude()));
        double dLong = Math.toRadians((end.getLongitude() - start.getLongitude()));

        double startLat = Math.toRadians(start.getLatitude());
        double endLat = Math.toRadians(end.getLatitude());

        double a = haversine(dLat) + Math.cos(startLat) * Math.cos(endLat) * haversine(dLong);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return Distance.fromKilometers(Earth.RADIUS_KM * c);
    }

    public double haversine(double val) {
        return Math.pow(Math.sin(val / 2), 2);
    }
}
