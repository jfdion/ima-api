package ca.ulaval.gif3101.ima.api.message.bootstrap;

import ca.ulaval.gif3101.ima.api.message.bootstrap.context.Context;
import ca.ulaval.gif3101.ima.api.message.controller.MessageController;
import ca.ulaval.gif3101.ima.api.message.domain.location.distanceCalculator.HaversineDistanceCalculatorStrategy;
import ca.ulaval.gif3101.ima.api.message.domain.message.MessageAssembler;
import ca.ulaval.gif3101.ima.api.message.domain.message.MessageBuilder;
import ca.ulaval.gif3101.ima.api.message.domain.message.MessageFactory;
import ca.ulaval.gif3101.ima.api.message.domain.message.MessageRepository;
import ca.ulaval.gif3101.ima.api.message.domain.message.filter.*;
import ca.ulaval.gif3101.ima.api.message.http.UriBuilderFactory;
import ca.ulaval.gif3101.ima.api.message.http.queryFilter.QueryFilterFactory;
import ca.ulaval.gif3101.ima.api.message.infrastructure.id.IdGenerator;
import ca.ulaval.gif3101.ima.api.message.infrastructure.id.UUIDGenerator;
import ca.ulaval.gif3101.ima.api.message.infrastructure.message.filter.CreatedFilter;
import ca.ulaval.gif3101.ima.api.message.infrastructure.message.filter.DistanceFilter;
import ca.ulaval.gif3101.ima.api.message.infrastructure.message.filter.FilterTimeVisibility;
import ca.ulaval.gif3101.ima.api.message.infrastructure.message.filter.NotExpiredFilter;
import ca.ulaval.gif3101.ima.api.message.service.message.MessageService;
import ca.ulaval.gif3101.ima.api.message.service.transformer.MessageTransformer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Bootstrap {

    private Context context;

    private MessageController messageController;

    private MessageService messageService;
    private ObjectMapper objectMapper;
    private MessageTransformer messageTransformer;

    private MessageAssembler messageAssembler;
    private MessageRepository messageRepository;
    private MessageFactory messageFactory;

    private MessageBuilder messageBuilder;

    private QueryFilterFactory queryFilterFactory;


    private UriBuilderFactory uriBuilderFactory;

    public Bootstrap(Context context) {
        this.context = context;
    }

    public MessageController messageController() {
        if (messageController == null) {
            messageController = new MessageController(messageService(), objectMapper(), queryFilterFactory(), messageTransformer());
        }
        return messageController;
    }

    private MessageService messageService() {
        if (messageService == null) {
            messageService = new MessageService(messageAssembler(), messageFactory(), messageRepository());
        }
        return messageService;
    }

    private MessageBuilder messageBuilder() {
        if (messageBuilder == null) {
            messageBuilder = new MessageBuilder();
        }
        return messageBuilder;
    }

    private MessageFactory messageFactory() {
        if (messageFactory == null) {
            messageFactory = new MessageFactory(messageBuilder(), new HaversineDistanceCalculatorStrategy());
        }
        return messageFactory;
    }

    private MessageTransformer messageTransformer() {
        if (messageTransformer == null) {
            messageTransformer = new MessageTransformer();
        }
        return messageTransformer;
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

    private MessageRepository messageRepository() {
        if (messageRepository == null) {
            messageRepository = context.getMessageRepository(messageFactory());
        }
        return messageRepository;
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
