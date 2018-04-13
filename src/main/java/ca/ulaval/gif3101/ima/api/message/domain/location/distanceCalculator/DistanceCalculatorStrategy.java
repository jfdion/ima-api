package ca.ulaval.gif3101.ima.api.message.domain.location.distanceCalculator;

import ca.ulaval.gif3101.ima.api.message.domain.distance.Distance;
import ca.ulaval.gif3101.ima.api.message.domain.location.Location;

public interface DistanceCalculatorStrategy {

    Distance calculate(Location start, Location end);
}
