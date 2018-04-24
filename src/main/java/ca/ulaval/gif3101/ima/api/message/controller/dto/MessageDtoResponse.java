package ca.ulaval.gif3101.ima.api.message.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MessageDtoResponse {

    private final String GOOGLE_MAP_SEARCH = "https://www.google.ca/maps/search/%s,%s";

    public String id;

    public String author;
    public String title;
    public String body;

    public boolean readable;

    @JsonProperty(value="readable-distance-in-meters")
    public double readableDistance;

    @JsonProperty(value="expires-at")
    public String expires;

    @JsonProperty(value="created-at")
    public String created;

    public double latitude;
    public double longitude;

    @JsonProperty(value="calculated-distance-in-meters")
    public double calculatedDistance;

    @JsonProperty(value="calculated-distance-from")
    public String distanceCalculatedFromLocation;

    @JsonProperty(value="visibility-start-time")
    public String visibilityStartTime;

    @JsonProperty(value="visibility-end-time")
    public String visibilityEndTime;

    @JsonProperty(value="view-on-google-maps")
    public String googleMaps;

    public void updateGoogleMaps() {
        googleMaps = String.format(GOOGLE_MAP_SEARCH, latitude, longitude);
    }
}
