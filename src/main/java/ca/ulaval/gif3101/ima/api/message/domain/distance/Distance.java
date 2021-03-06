package ca.ulaval.gif3101.ima.api.message.domain.distance;

public class Distance {

    public static final double METERS_IN_KM = 1000;
    public static final double METERS_IN_FOOT = 0.3048;
    public static final double METERS_IN_MILES = 1609.34;

    private double distance;

    private Distance(double distance) {
        this.distance = distance;
    }

    public static Distance fromMeters(double meters) {
        return new Distance(meters);
    }

    public static Distance fromKilometers(double km) {
        return new Distance(km * METERS_IN_KM);
    }

      public double toMeters() {
        return distance;
    }

    public double toKilometers() {
        return distance / METERS_IN_KM;
    }

    public boolean lesserOrEqualThan(Distance distance) {
        return this.distance <= distance.distance;
    }

    public int compare(Distance distance) {
       return Double.compare(this.distance, distance.distance);
    }

}
