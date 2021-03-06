package ca.ulaval.gif3101.ima.api.message.domain.message;

public class MessageDto {
    public String id;
    public String author;
    public String title;
    public String body;
    public String readableBody;
    public boolean readable;
    public double readableDistance;
    public String expires;
    public String created;
    public double latitude;
    public double longitude;

    public double calculatedDistance;
    public String distanceCalculatedFromLocation;

    public String visibilityStartTime;
    public String visibilityEndTime;
}
