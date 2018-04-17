package ca.ulaval.gif3101.ima.api.message.bootstrap.context;

import ca.ulaval.gif3101.ima.api.message.domain.location.distanceCalculator.HaversineDistanceCalculatorStrategy;
import ca.ulaval.gif3101.ima.api.message.domain.message.MessageFactory;
import ca.ulaval.gif3101.ima.api.message.domain.message.MessageRepository;
import ca.ulaval.gif3101.ima.api.message.domain.message.filter.Filter;
import ca.ulaval.gif3101.ima.api.message.domain.message.filter.FilterComposite;
import ca.ulaval.gif3101.ima.api.message.infrastructure.id.IdGenerator;
import ca.ulaval.gif3101.ima.api.message.infrastructure.id.UUIDGenerator;
import ca.ulaval.gif3101.ima.api.message.infrastructure.message.MessageFakeDataFactory;
import ca.ulaval.gif3101.ima.api.message.infrastructure.message.MessageRepositoryInMemory;
import ca.ulaval.gif3101.ima.api.message.infrastructure.message.filter.CreatedFilter;
import ca.ulaval.gif3101.ima.api.message.infrastructure.message.filter.DistanceFilter;
import ca.ulaval.gif3101.ima.api.message.infrastructure.message.filter.FilterTimeVisibility;
import ca.ulaval.gif3101.ima.api.message.infrastructure.message.filter.NotExpiredFilter;
import ca.ulaval.gif3101.ima.api.message.utils.RandomGenerator;
import com.github.javafaker.Faker;

public class DevContext implements Context {

    private static final int NUMBER_OF_ENTRIES = 12500;

    private IdGenerator idGenerator;

    private FilterComposite messageFilter;


    private IdGenerator idGenerator() {
        if (idGenerator == null) {
            idGenerator = new UUIDGenerator();
        }
        return idGenerator;
    }

    @Override
    public MessageRepository getMessageRepository(MessageFactory messageFactory) {

        MessageRepository repository = new MessageRepositoryInMemory(idGenerator(), messageFilter());

        MessageFakeDataFactory messageFakeFactory = new MessageFakeDataFactory(repository, new Faker(), new RandomGenerator());
        messageFakeFactory.create(NUMBER_OF_ENTRIES);

        return repository;

    }

    private Filter messageFilter() {
        if (messageFilter == null) {
            messageFilter = new FilterComposite();
            messageFilter.addFilter(new CreatedFilter());
            messageFilter.addFilter(new NotExpiredFilter());
            messageFilter.addFilter(new DistanceFilter(new HaversineDistanceCalculatorStrategy()));
            messageFilter.addFilter(new FilterTimeVisibility());
        }
        return messageFilter;
    }
}
