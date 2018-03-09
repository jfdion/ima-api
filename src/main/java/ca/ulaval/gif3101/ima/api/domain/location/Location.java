package ca.ulaval.gif3101.ima.api.domain.location;

import ca.ulaval.gif3101.ima.api.domain.Distance.Distance;

public class Location {

    private double latitude;
    private double longitude;

    public Location(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return this.latitude;
    }

    public double getLongitude() {
        return this.longitude;
    }

    public Location add(Distance latitudeDistance, Distance longitudeDistance) {

        double deltaLatitude = latitudeDistance.toKilometers() / Earth.RADIUS_KM;
        double deltaLongitude = longitudeDistance.toKilometers() / (Earth.RADIUS_KM * Math.cos(Math.toRadians(latitude)));

        return new Location(latitude + Math.toDegrees(deltaLatitude), longitude + Math.toDegrees(deltaLongitude));
    }

    public BoundingBox createBoundingBox(Distance latitudeDistance, Distance longitudeDistance) {
        double latitude = Math.abs(latitudeDistance.toMeters());
        double longitude = Math.abs(longitudeDistance.toMeters());
        Location min = add(Distance.fromMeters(latitude * -1), Distance.fromMeters(longitude * -1));
        Location max = add(Distance.fromMeters(latitude), Distance.fromMeters(longitude));
        return new BoundingBox(min, max);
    }
}
