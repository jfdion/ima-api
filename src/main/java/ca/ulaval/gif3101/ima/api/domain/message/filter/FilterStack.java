package ca.ulaval.gif3101.ima.api.domain.message.filter;

import ca.ulaval.gif3101.ima.api.domain.message.Message;

import java.util.ArrayList;
import java.util.List;

public class FilterStack implements FilterComposite {

    private List<Filter> filters = new ArrayList<>();

    public void addFilter(Filter filter) {
        if (!filters.contains(filter)) {
            filters.add(filter);
        }
    }

    @Override
    public List<Message> filter(List<Message> messages, FilterConfig config) {

        for (Filter filter : filters) {
            messages = filter.filter(messages, config);
        }

        return messages;
    }
}
