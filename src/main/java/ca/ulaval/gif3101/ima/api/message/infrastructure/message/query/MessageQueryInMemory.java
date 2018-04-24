package ca.ulaval.gif3101.ima.api.message.infrastructure.message.query;

import ca.ulaval.gif3101.ima.api.message.domain.location.Location;
import ca.ulaval.gif3101.ima.api.message.domain.location.LocationScope;
import ca.ulaval.gif3101.ima.api.message.domain.location.distanceCalculator.DistanceCalculatorStrategy;
import ca.ulaval.gif3101.ima.api.message.domain.message.Message;
import ca.ulaval.gif3101.ima.api.message.domain.message.query.MessageQuery;

public class MessageQueryInMemory implements MessageQuery {

    private boolean isExpired = false;
    private boolean isCreated = true;

    private boolean timeVisible = false;

    private Location fromLocation;
    private String locationScope;


    public MessageQueryInMemory() {
        locationScope = LocationScope.MEDIUM;
    }

    @Override
    public void fromLocation(Location fromLocation) {
        this.fromLocation = fromLocation;
    }

    @Override
    public Location getFromLocation() {return this.fromLocation;}

    @Override
    public void locationScope(String scope) {
        this.locationScope = scope;
    }

    @Override
    public String getLocationScope() {return this.locationScope;}

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
}
