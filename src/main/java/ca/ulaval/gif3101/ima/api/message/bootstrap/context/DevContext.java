package ca.ulaval.gif3101.ima.api.message.bootstrap.context;

import ca.ulaval.gif3101.ima.api.message.domain.message.MessageRepository;
import ca.ulaval.gif3101.ima.api.message.domain.message.filter.Filter;
import ca.ulaval.gif3101.ima.api.message.infrastructure.id.IdGenerator;
import ca.ulaval.gif3101.ima.api.message.infrastructure.message.MessageFakeDataFactory;
import ca.ulaval.gif3101.ima.api.message.infrastructure.message.MessageRepositoryInMemory;
import ca.ulaval.gif3101.ima.api.message.utils.RandomGenerator;
import com.github.javafaker.Faker;

public class DevContext implements Context {

    private static final int NUMBER_OF_ENTRIES = 12500;

    @Override
    public MessageRepository getMessageRepository(IdGenerator idGenerator, Filter filter) {

        MessageRepository repository = new MessageRepositoryInMemory(idGenerator, filter);

        MessageFakeDataFactory messageFakeFactory = new MessageFakeDataFactory(repository, new Faker(), new RandomGenerator());
        messageFakeFactory.create(NUMBER_OF_ENTRIES);

        return repository;

    }
}
