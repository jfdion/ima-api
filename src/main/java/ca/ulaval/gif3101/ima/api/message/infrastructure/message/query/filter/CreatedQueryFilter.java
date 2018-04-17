package ca.ulaval.gif3101.ima.api.message.infrastructure.message.query.filter;

import ca.ulaval.gif3101.ima.api.message.domain.message.query.MessageQuery;
import ca.ulaval.gif3101.ima.api.message.external.date.JodaTimeDateAdapter;
import org.mongodb.morphia.query.Query;

public class CreatedQueryFilter<T> implements QueryFilter<T> {

    @Override
    public Query<T> filter(Query<T> query, MessageQuery messageQuery) {
        if (!messageQuery.isCreated()) {
            return query;
        }
        JodaTimeDateAdapter now = new JodaTimeDateAdapter();
        query.criteria("created").lessThanOrEq(now.toString());
        return query;
    }
}
