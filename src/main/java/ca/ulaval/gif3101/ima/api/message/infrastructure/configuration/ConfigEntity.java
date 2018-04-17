package ca.ulaval.gif3101.ima.api.message.infrastructure.configuration;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity(value="config")
public class ConfigEntity {
    @Id
    public ObjectId id;

    public String key;

    public String value;
}
