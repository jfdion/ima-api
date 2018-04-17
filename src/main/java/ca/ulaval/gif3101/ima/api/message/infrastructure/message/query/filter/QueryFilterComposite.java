package ca.ulaval.gif3101.ima.api.message.infrastructure.message.query.filter;

import ca.ulaval.gif3101.ima.api.message.domain.message.query.MessageQuery;
import org.mongodb.morphia.query.Query;

import java.util.ArrayList;
import java.util.List;

public class QueryFilterComposite <T> implements QueryFilter<T> {

    private List<QueryFilter<T>> queryFilters = new ArrayList<>();

    public void addFilter(QueryFilter<T> queryFilter) {
        queryFilters.add(queryFilter);
    }

    @Override
    public Query<T> filter(Query<T> query, MessageQuery messageQuery) {
        for (QueryFilter filter : queryFilters) {
            query = filter.filter(query, messageQuery);
        }
        return query;
    }
}
