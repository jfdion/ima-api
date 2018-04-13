package ca.ulaval.gif3101.ima.api.message.domain.message.query;

import ca.ulaval.gif3101.ima.api.message.domain.location.Location;
import ca.ulaval.gif3101.ima.api.message.domain.location.distanceCalculator.DistanceCalculatorStrategy;
import ca.ulaval.gif3101.ima.api.message.domain.message.Message;

public interface MessageQuery {

    enum LocationScope {CLOSE, MEDIUM, GENERAL}

    void fromLocation(Location fromLocation);

    void locationScope(MessageQuery.LocationScope scope);

    void created();

    void notCreated();

    void expired();

    void notExpired();

    boolean isCreated();

    boolean isExpired();

    boolean hasFromLocation();

    void timeVisible();

    boolean hasTimeVisibility();

    boolean isInsideScopeRadius(Message message, DistanceCalculatorStrategy strategy);
}
