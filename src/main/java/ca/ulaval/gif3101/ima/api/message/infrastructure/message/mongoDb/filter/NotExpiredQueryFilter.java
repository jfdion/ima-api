package ca.ulaval.gif3101.ima.api.message.infrastructure.message.mongoDb.filter;

import ca.ulaval.gif3101.ima.api.message.domain.message.query.MessageQuery;
import ca.ulaval.gif3101.ima.api.message.external.date.JodaTimeDateAdapter;
import org.mongodb.morphia.query.Query;

public class NotExpiredQueryFilter<T> implements QueryFilter<T> {

    @Override
    public Query<T> filter(Query<T> query, MessageQuery messageQuery) {
        if (messageQuery.isExpired()) {
            return query;
        }
        JodaTimeDateAdapter now = new JodaTimeDateAdapter();
        query.criteria("expires").greaterThanOrEq(now.toString());
        return query;
    }
}
