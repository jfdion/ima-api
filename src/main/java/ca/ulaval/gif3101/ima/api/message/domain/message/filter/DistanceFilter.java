package ca.ulaval.gif3101.ima.api.message.domain.message.filter;


import ca.ulaval.gif3101.ima.api.message.domain.location.distanceCalculator.DistanceCalculatorStrategy;
import ca.ulaval.gif3101.ima.api.message.domain.message.Message;
import ca.ulaval.gif3101.ima.api.message.domain.message.query.MessageQuery;

import java.util.ArrayList;
import java.util.List;

public class DistanceFilter implements Filter {

    private DistanceCalculatorStrategy distanceStrategy;

    public DistanceFilter(DistanceCalculatorStrategy distanceStrategy) {
        this.distanceStrategy = distanceStrategy;
    }

    @Override
    public List<Message> filter(List<Message> messages, MessageQuery query) {
        if (!query.hasFromLocation()) {
            return messages;
        }
        List<Message> results = new ArrayList<>();

        for (Message message : messages) {

            if (query.isInsideScopeRadius(message, distanceStrategy)) {
                results.add(message);
            }
        }

        return results;
    }


}
