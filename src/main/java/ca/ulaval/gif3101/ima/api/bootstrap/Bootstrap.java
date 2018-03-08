package ca.ulaval.gif3101.ima.api.bootstrap;

import ca.ulaval.gif3101.ima.api.controller.message.MessageController;
import ca.ulaval.gif3101.ima.api.domain.message.MessageAssembler;
import ca.ulaval.gif3101.ima.api.domain.message.MessageRepository;
import ca.ulaval.gif3101.ima.api.http.UriBuilderFactory;
import ca.ulaval.gif3101.ima.api.http.queryFilter.QueryFilterFactory;
import ca.ulaval.gif3101.ima.api.infrastructure.id.IdGenerator;
import ca.ulaval.gif3101.ima.api.infrastructure.id.UUIDGenerator;
import ca.ulaval.gif3101.ima.api.infrastructure.message.MessageDAO;
import ca.ulaval.gif3101.ima.api.infrastructure.message.MessageDAOInMemory;
import ca.ulaval.gif3101.ima.api.infrastructure.message.MessageFakeDataFactory;
import ca.ulaval.gif3101.ima.api.infrastructure.message.MessageRepositoryInMemory;
import ca.ulaval.gif3101.ima.api.service.MessageService;
import ca.ulaval.gif3101.ima.api.utils.RandomGenerator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;

import javax.ws.rs.core.UriBuilder;

public class Bootstrap {

    public static final String ENV_DEV = "dev";
    private String env;

    private MessageController messageController;

    private MessageService messageService;
    private ObjectMapper objectMapper;

    private MessageAssembler messageAssembler;
    private MessageRepository messageRepository;

    private MessageDAO messageDAO;
    private QueryFilterFactory queryFilterFactory;

    private IdGenerator idGenerator;
    private UriBuilderFactory uriBuilderFactory;

    public Bootstrap(String env) {
        this.env = env;
    }

    public MessageController messageController() throws Exception {
        if (messageController == null) {
            messageController = new MessageController(messageService(), objectMapper(), queryFilterFactory());
        }
        return messageController;
    }

    private MessageService messageService() throws Exception {
        if (messageService == null) {
            messageService = new MessageService(messageAssembler(), messageRepository());
        }
        return messageService;
    }

    private ObjectMapper objectMapper() {
        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        }
        return objectMapper;
    }

    private MessageAssembler messageAssembler() {
        if (messageAssembler == null) {
            messageAssembler = new MessageAssembler();
        }
        return messageAssembler;
    }

    private MessageRepository messageRepository() throws Exception {
        if (messageRepository == null) {
            messageRepository = new MessageRepositoryInMemory(messageAssembler(), messageDAO());
        }
        return messageRepository;
    }

    private MessageDAO messageDAO() throws Exception {
        if (messageDAO == null) {
            messageDAO = new MessageDAOInMemory(idGenerator());

            MessageFakeDataFactory messageFakeFactory = new MessageFakeDataFactory(messageDAO, new Faker(), new RandomGenerator());
            messageFakeFactory.create(123);
        }
        return messageDAO;
    }

    private IdGenerator idGenerator() {
        if (idGenerator == null) {
            idGenerator = new UUIDGenerator();
        }
        return idGenerator;
    }

    private QueryFilterFactory queryFilterFactory() {
        if (queryFilterFactory == null) {
            queryFilterFactory = new QueryFilterFactory(uriBuilderFactory());
        }
        return queryFilterFactory;
    }

    private UriBuilderFactory uriBuilderFactory() {
        if (uriBuilderFactory == null) {
            uriBuilderFactory = new UriBuilderFactory();
        }
        return uriBuilderFactory;
    }

}