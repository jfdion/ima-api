package ca.ulaval.gif3101.ima.api.message.infrastructure.message.query;

import ca.ulaval.gif3101.ima.api.message.domain.distance.Distance;
import ca.ulaval.gif3101.ima.api.message.domain.location.Location;
import ca.ulaval.gif3101.ima.api.message.domain.location.distanceCalculator.DistanceCalculatorStrategy;
import ca.ulaval.gif3101.ima.api.message.domain.message.Message;
import ca.ulaval.gif3101.ima.api.message.domain.message.query.MessageQuery;

public class MessageQueryInMemory implements MessageQuery {

    private boolean isExpired = false;
    private boolean isCreated = true;

    private boolean timeVisible = false;

    private Location fromLocation;
    private LocationScope locationScope;

    private Distance closeDistance = Distance.fromMeters(150);
    private Distance mediumDistance = Distance.fromKilometers(5);
    private Distance generalRadius = Distance.fromKilometers(150);

    public MessageQueryInMemory() {
        locationScope = LocationScope.MEDIUM;
    }

    @Override
    public void fromLocation(Location fromLocation) {
        this.fromLocation = fromLocation;
    }

    @Override
    public void locationScope(LocationScope scope) {
        this.locationScope = scope;
    }

    @Override
    public void created() {
        isCreated = true;
    }

    @Override
    public void notCreated() {
        isCreated = false;
    }

    @Override
    public void expired() {
        isExpired = true;
    }

    @Override
    public void notExpired() {
        isExpired = false;
    }

    @Override
    public boolean isCreated() {
        return isCreated;
    }

    @Override
    public boolean isExpired() {
        return isExpired;
    }

    @Override
    public boolean hasFromLocation() {
        return fromLocation != null;
    }

    @Override
    public void timeVisible() {
        timeVisible = true;
    }

    @Override
    public boolean hasTimeVisibility() {
        return timeVisible;
    }

    @Override
    public boolean isInsideScopeRadius(Message message, DistanceCalculatorStrategy strategy) {
        return strategy
                .calculate(fromLocation, message.location())
                .lesserOrEqualThan(whichRadius());
    }

    private Distance whichRadius() {
        switch (locationScope) {
            case CLOSE:
                return closeDistance;
            case MEDIUM:
                return mediumDistance;
            case GENERAL:
                return generalRadius;
            default:
                return closeDistance;
        }
    }
}
