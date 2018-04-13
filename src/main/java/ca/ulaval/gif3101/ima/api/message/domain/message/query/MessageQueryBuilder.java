package ca.ulaval.gif3101.ima.api.message.domain.message.query;

public interface MessageQueryBuilder {

    MessageQueryBuilder fromLocation(String fromLocation);

    MessageQueryBuilder fromLocation(Double latitude, Double longitude);

    MessageQueryBuilder withLocationScope(String locationScope);

    MessageQueryBuilder withExpiredMessages(boolean expired);

    MessageQueryBuilder withCreatedMessages(boolean created);

    MessageQueryBuilder withTimeVisibility(boolean visibility);

    MessageQuery build();
}
