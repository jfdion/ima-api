package ca.ulaval.gif3101.ima.api.message.infrastructure.message.query;

import ca.ulaval.gif3101.ima.api.message.domain.location.Location;
import ca.ulaval.gif3101.ima.api.message.domain.message.query.MessageQuery;
import ca.ulaval.gif3101.ima.api.message.domain.message.query.MessageQueryBuilder;

public class MessageQueryBuilderInMemory implements MessageQueryBuilder {

    private boolean isExpired;
    private boolean isCreated;
    private boolean timeVisible;

    private Location fromLocation;
    private MessageQuery.LocationScope locationScope;

    public MessageQueryBuilderInMemory() {
        setDefaultValues();
    }

    @Override
    public MessageQueryBuilder fromLocation(String fromLocation) {
        this.fromLocation = new Location(fromLocation);
        return this;
    }

    @Override
    public MessageQueryBuilder fromLocation(Double latitude, Double longitude) {
        this.fromLocation = new Location(latitude, longitude);
        return this;
    }

    @Override
    public MessageQueryBuilder withLocationScope(String scope) {
        if (scope != null) {
            this.locationScope = MessageQuery.LocationScope.valueOf(scope.toUpperCase());
        }
        return this;
    }

    @Override
    public MessageQueryBuilder withExpiredMessages(boolean expired) {
        isExpired = expired;
        return this;
    }

    @Override
    public MessageQueryBuilder withCreatedMessages(boolean created) {
        isCreated = created;
        return this;
    }

    @Override
    public MessageQueryBuilder withTimeVisibility(boolean visibility) {
        timeVisible = visibility;
        return this;
    }

    @Override
    public MessageQuery build() {
        MessageQuery query = new MessageQueryInMemory();

        fromLocation(query);
        locationScope(query);
        created(query);
        expired(query);
        timeVisible(query);

        setDefaultValues();

        return query;
    }

    private void expired(MessageQuery query) {
        if (isExpired) {
            query.expired();
        } else {
            query.notExpired();
        }
    }

    private void created(MessageQuery query) {
        if (isCreated) {
            query.created();
        } else {
            query.notCreated();
        }
    }

    private void locationScope(MessageQuery query) {
        if (locationScope != null) {
            query.locationScope(locationScope);
        }
    }

    private void fromLocation(MessageQuery query) {
        if (fromLocation != null) {
            query.fromLocation(fromLocation);
        }
    }

    private void timeVisible(MessageQuery query) {
        if (timeVisible) {
            query.timeVisible();
        }
    }

    private void setDefaultValues() {
        fromLocation = null;
        locationScope = null;
        isCreated = true;
        isExpired = false;
    }
}
