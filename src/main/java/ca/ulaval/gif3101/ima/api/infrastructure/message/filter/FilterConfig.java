package ca.ulaval.gif3101.ima.api.infrastructure.message.filter;

import ca.ulaval.gif3101.ima.api.domain.Distance.Distance;
import ca.ulaval.gif3101.ima.api.domain.location.Location;

public class FilterConfig {

    public enum LocationScope {CLOSE, MEDIUM, GENERAL}

    public boolean isExpired = false;
    public boolean isCreated = true;

    public Location fromLocation;
    public LocationScope locationScope = LocationScope.MEDIUM;
    public Distance closeDistance = Distance.fromMeters(150);
    public Distance mediumDistance = Distance.fromKilometers(5);
    public Distance generalRadius = Distance.fromKilometers(150);
}
