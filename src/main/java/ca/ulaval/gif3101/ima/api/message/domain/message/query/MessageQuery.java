package ca.ulaval.gif3101.ima.api.message.domain.message.query;

import ca.ulaval.gif3101.ima.api.message.domain.location.Location;
import ca.ulaval.gif3101.ima.api.message.domain.location.LocationScope;
import ca.ulaval.gif3101.ima.api.message.domain.location.distanceCalculator.DistanceCalculatorStrategy;
import ca.ulaval.gif3101.ima.api.message.domain.message.Message;

public interface MessageQuery {

    void fromLocation(Location fromLocation);

    Location getFromLocation();

    void locationScope(String scope);

    String getLocationScope();

    void created();

    void notCreated();

    void expired();

    void notExpired();

    boolean isCreated();

    boolean isExpired();

    boolean hasFromLocation();

    void timeVisible();

    boolean hasTimeVisibility();
}
