package ca.ulaval.gif3101.ima.api.domain.location;

public class Location {

    private double longitude;
    private double latitude;

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
}
