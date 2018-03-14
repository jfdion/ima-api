package ca.ulaval.gif3101.ima.api.domain.message.filter;


import ca.ulaval.gif3101.ima.api.domain.distance.Distance;
import ca.ulaval.gif3101.ima.api.domain.location.distanceCalculator.DistanceCalculatorStrategy;
import ca.ulaval.gif3101.ima.api.domain.message.Message;

import java.util.ArrayList;
import java.util.List;

public class DistanceFilter implements Filter {

    private DistanceCalculatorStrategy distanceStrategy;

    public DistanceFilter(DistanceCalculatorStrategy distanceStrategy) {
        this.distanceStrategy = distanceStrategy;
    }

    @Override
    public List<Message> filter(List<Message> messages, FilterConfig config) {
        if (config.fromLocation == null) {
            return messages;
        }
        List<Message> results = new ArrayList<>();

        for (Message message : messages) {
            if (message.insideRadius(config.fromLocation, whichRadius(config), distanceStrategy)) {
                results.add(message);
            }
        }

        return results;
    }

    private Distance whichRadius(FilterConfig config) {
        switch (config.locationScope) {
            case CLOSE:
                return config.closeDistance;
            case MEDIUM:
                return config.mediumDistance;
            case GENERAL:
                return config.generalRadius;
            default:
                return config.closeDistance;
        }
    }
}