package ca.ulaval.gif3101.ima.api.message.infrastructure.message.query.filter;

import ca.ulaval.gif3101.ima.api.message.domain.message.query.MessageQuery;
import org.mongodb.morphia.query.Query;

public interface QueryFilter <T> {

    Query<T> filter(Query<T> query, MessageQuery messageQuery);
}
