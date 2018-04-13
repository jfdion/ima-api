package ca.ulaval.gif3101.ima.api.message.domain.location;

public class BoundingBox {
    private Location topLeft;
    private Location bottomRight;

    public BoundingBox(Location topLeft, Location bottomRight) {
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
    }

    public Location topLeft() {
        return topLeft;
    }

    public Location bottomRight() {
        return bottomRight;
    }

    public String toString() {
        return "{"+
                "top-left : {" +
                "lat : " + topLeft.getLatitude() +
                "long : " + topLeft.getLongitude() +
                "}" +
                "bottom-right : {" +
                "lat : " + bottomRight.getLatitude() +
                "long : " + bottomRight.getLongitude() +
                "}" +
                "}";
    }
}
