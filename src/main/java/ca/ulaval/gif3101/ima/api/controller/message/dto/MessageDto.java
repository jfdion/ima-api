package ca.ulaval.gif3101.ima.api.controller.message.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MessageDto {

    private final String GOOGLE_MAP_SEARCH = "https://www.google.ca/maps/search/%s,%s";

    public String id;
    public String title;
    public String body;

    @JsonProperty(value="expires-at")
    public String expires;

    @JsonProperty(value="created-at")
    public String created;

    public double latitude;
    public double longitude;

    @JsonProperty(value="view-on-google-maps")
    public String googleMaps;

    public void updateGoogleMaps() {
        googleMaps = String.format(GOOGLE_MAP_SEARCH, latitude, longitude);
    }

}
