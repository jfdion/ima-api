package ca.ulaval.gif3101.ima.api.domain.location.distanceCalculator;

import ca.ulaval.gif3101.ima.api.domain.distance.Distance;
import ca.ulaval.gif3101.ima.api.domain.location.Location;

public interface DistanceCalculatorStrategy {

    Distance calculate(Location start, Location end);
}
