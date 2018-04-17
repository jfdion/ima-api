package ca.ulaval.gif3101.ima.api.message.bootstrap.context;

import ca.ulaval.gif3101.ima.api.message.domain.message.MessageFactory;
import ca.ulaval.gif3101.ima.api.message.domain.message.MessageRepository;
import ca.ulaval.gif3101.ima.api.message.domain.message.filter.Filter;
import ca.ulaval.gif3101.ima.api.message.infrastructure.message.MessageFakeDataFactory;
import ca.ulaval.gif3101.ima.api.message.infrastructure.message.MessageRepositoryMongoDb;
import ca.ulaval.gif3101.ima.api.message.infrastructure.message.dao.MessageDAO;
import ca.ulaval.gif3101.ima.api.message.infrastructure.message.dao.MessageDAOMongoDb;
import ca.ulaval.gif3101.ima.api.message.infrastructure.configuration.ConfigEntity;
import ca.ulaval.gif3101.ima.api.message.utils.RandomGenerator;
import com.github.javafaker.Faker;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

public class ProdContext implements Context {

    public static final String MESSAGE_FIXTURE_LOADED = "message-fixture-loaded";
    private static final String DB_URI = "mongodb://ima-api:ima-api@ds259778.mlab.com:59778/ima-api";
    private static final String DB_NAME = "ima-api";
    private static final int NUMBER_OF_ENTRIES = 12500;

    private MessageDAO messageDAO;
    private Datastore datastore;
    private MongoClient mongoClient;

    public MessageDAO getMessageDAO(MessageFactory messageFactory) {
        if (messageDAO == null) {
            messageDAO = new MessageDAOMongoDb(datastore(), messageFactory);
        }
        return messageDAO;
    }

    private Datastore datastore() {
        if (datastore == null) {
            Morphia morphia = new Morphia();
            datastore = morphia.createDatastore(getMongoClient(), DB_NAME);
        }
        return datastore;
    }

    private MongoClient getMongoClient() {
        if (mongoClient == null) {
            mongoClient = new MongoClient(
                    new MongoClientURI(DB_URI)
            );
        }
        return mongoClient;
    }

    @Override
    public MessageRepository getMessageRepository(MessageFactory messageFactory, Filter filter) {
        MessageRepository repository = new MessageRepositoryMongoDb(getMessageDAO(messageFactory), filter);

        Datastore datastore =  datastore();
        ConfigEntity config = datastore.find(ConfigEntity.class).filter("key", MESSAGE_FIXTURE_LOADED).get();
        if (config == null || !Boolean.valueOf(config.value)) {
            MessageFakeDataFactory messageFakeFactory = new MessageFakeDataFactory(repository, new Faker(), new RandomGenerator());
            messageFakeFactory.create(NUMBER_OF_ENTRIES);
            if (config == null) {
                config = new ConfigEntity();
                config.key = MESSAGE_FIXTURE_LOADED;
            }
            config.value = String.valueOf(true);
            datastore.save(config);
        }

        return repository;
    }
}
