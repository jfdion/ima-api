package ca.ulaval.gif3101.ima.api.message.domain.location;

import ca.ulaval.gif3101.ima.api.message.domain.distance.Distance;

public interface LocationScope {

    enum scopes {CLOSE, MEDIUM, GENERAL}

    String CLOSE = "close";
    String MEDIUM = "medium";
    String GENERAL = "general";

    Distance CLOSE_DISTANCE = Distance.fromMeters(150);
    Distance MEDIUM_DISTANCE = Distance.fromKilometers(5);
    Distance GENERAL_DISTANCE = Distance.fromKilometers(150);
}
