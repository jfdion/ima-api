package ca.ulaval.gif3101.ima.api.message.controller;

import ca.ulaval.gif3101.ima.api.message.controller.dto.CreateMessageDto;
import ca.ulaval.gif3101.ima.api.message.controller.dto.MessageDtoResponse;
import ca.ulaval.gif3101.ima.api.message.controller.dto.MessageSearchDto;
import ca.ulaval.gif3101.ima.api.message.domain.message.MessageDto;
import ca.ulaval.gif3101.ima.api.message.domain.message.exception.MessageNotFoundException;
import ca.ulaval.gif3101.ima.api.message.domain.message.query.MessageQuery;
import ca.ulaval.gif3101.ima.api.message.domain.message.query.MessageQueryBuilder;
import ca.ulaval.gif3101.ima.api.message.http.json.message.CreatedMessage;
import ca.ulaval.gif3101.ima.api.message.http.json.message.NoContentMessage;
import ca.ulaval.gif3101.ima.api.message.http.json.message.NotFoundMessage;
import ca.ulaval.gif3101.ima.api.message.http.json.message.UnexpectedErrorMessage;
import ca.ulaval.gif3101.ima.api.message.http.json.wrapper.JsonCollectionWrapper;
import ca.ulaval.gif3101.ima.api.message.http.json.wrapper.JsonEmptyWrapper;
import ca.ulaval.gif3101.ima.api.message.http.json.wrapper.JsonEntityWrapper;
import ca.ulaval.gif3101.ima.api.message.http.json.wrapper.JsonWrapper;
import ca.ulaval.gif3101.ima.api.message.http.queryFilter.QueryFilter;
import ca.ulaval.gif3101.ima.api.message.http.queryFilter.QueryFilterFactory;
import ca.ulaval.gif3101.ima.api.message.http.queryFilter.exception.EmptyPageException;
import ca.ulaval.gif3101.ima.api.message.infrastructure.message.query.MessageQueryBuilderInMemory;
import ca.ulaval.gif3101.ima.api.message.service.message.MessageService;
import ca.ulaval.gif3101.ima.api.message.service.transformer.Transformer;
import ca.ulaval.gif3101.ima.api.message.utils.Utils;
import com.fasterxml.jackson.databind.ObjectMapper;
import spark.Request;
import spark.Response;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class MessageController {

    private MessageService messageService;
    private ObjectMapper mapper;
    private QueryFilterFactory queryFilterFactory;
    private Transformer<MessageDtoResponse, MessageDto> transformer;

    public MessageController(MessageService messageService, ObjectMapper mapper, QueryFilterFactory queryFilterFactory, Transformer<MessageDtoResponse, MessageDto> messageTransformer) {
        this.messageService = messageService;
        this.mapper = mapper;
        this.queryFilterFactory = queryFilterFactory;
        this.transformer = messageTransformer;
    }

    public Object getAll(Request request, Response response) throws Exception {

        JsonWrapper wrapper;
        try {
            MessageQuery messageQuery = initFilterConfig(request);
            List<MessageDtoResponse> dtos = transformer.transform(messageService.getAllFiltered(messageQuery));
            QueryFilter queryFilter = queryFilterFactory.create(request, dtos, MessageDto.class);
            wrapper = new JsonCollectionWrapper<>(dtos, queryFilter);
            response.status(HttpServletResponse.SC_OK);
        } catch (EmptyPageException e) {
            wrapper = noContentWrapper(response, e);
        } catch (Exception e) {
            wrapper = unexpectedErrorWrapper(response, e);
        }

        return mapper.writeValueAsString(wrapper);
    }

    private JsonWrapper noContentWrapper(Response response, EmptyPageException e) {
        JsonWrapper wrapper;
        wrapper = new JsonEmptyWrapper();
        wrapper.addMessage(new NoContentMessage(e.getMessage()));
        response.status(HttpServletResponse.SC_NO_CONTENT);
        return wrapper;
    }

    public Object getOne(Request request, Response response) throws Exception {
        JsonWrapper wrapper;

        try {
            MessageDto dto = new MessageDto();
            dto.id = request.params("message-id");
            MessageDtoResponse messageDto = transformer.transform(messageService.getOne(dto));
            wrapper = new JsonEntityWrapper<>(messageDto);
            response.status(HttpServletResponse.SC_OK);
        } catch (MessageNotFoundException e) {
            wrapper = notFoundWrapper(response, e);
        } catch (Exception e) {
            wrapper = unexpectedErrorWrapper(response, e);
        }

        return mapper.writeValueAsString(wrapper);
    }

    public Object search(Request request, Response response) throws Exception {
        JsonWrapper wrapper;
        try {
            MessageSearchDto searchDto = mapper.readValue(request.body(), MessageSearchDto.class);

            MessageQuery messageQuery = initSearchFilterConfig(searchDto);
            List<MessageDtoResponse> dtos = transformer.transform(messageService.getAllFiltered(messageQuery));
            QueryFilter queryFilter = queryFilterFactory.create(request, dtos, MessageDto.class);
            wrapper = new JsonCollectionWrapper<>(dtos, queryFilter);
            response.status(HttpServletResponse.SC_OK);
        } catch (EmptyPageException e) {
            wrapper = noContentWrapper(response, e);
        } catch (Exception e) {
            wrapper = unexpectedErrorWrapper(response, e);
        }

        return mapper.writeValueAsString(wrapper);
    }

    private JsonWrapper unexpectedErrorWrapper(Response response, Exception e) {
        JsonWrapper wrapper;
        wrapper = new JsonEmptyWrapper();
        Utils.stackTrace(e);
        wrapper.addMessage(new UnexpectedErrorMessage(e.getMessage()));
        response.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        return wrapper;
    }

    private JsonWrapper notFoundWrapper(Response response, MessageNotFoundException e) {
        JsonWrapper wrapper;
        wrapper = new JsonEmptyWrapper();
        wrapper.addMessage(new NotFoundMessage(e.getMessage()));
        response.status(HttpServletResponse.SC_NOT_FOUND);
        return wrapper;
    }

    private MessageQuery initFilterConfig(Request request) {
        MessageQueryBuilder queryBuilder = new MessageQueryBuilderInMemory();

        queryBuilder = queryBuilder
                .fromLocation(request.queryParams("location"));
        queryBuilder = queryBuilder.withLocationScope(request.queryParams("location-scope"));

        return queryBuilder.build();
    }

    private MessageQuery initSearchFilterConfig(MessageSearchDto searchDto) {
        if (searchDto.latitude == null || searchDto.longitude == null) {
            throw new IllegalArgumentException("latitude and longitude are required to perform seach");
        }
        MessageDto dto = buildMessageFromSearchDto(searchDto);

        MessageQueryBuilder queryBuilder = new MessageQueryBuilderInMemory();

        queryBuilder = queryBuilder.fromLocation(dto.latitude, dto.longitude)
                .withTimeVisibility(true)
                .withCreatedMessages(true)
                .withExpiredMessages(false);

        return queryBuilder.build();
    }

    public Object create(Request request, Response response) throws Exception {
        MessageDto inputDto = buildMessageFromCreationDto(mapper.readValue(request.body(), CreateMessageDto.class));

        JsonWrapper wrapper; 
        try {
            MessageDtoResponse dto = transformer.transform(messageService.createMessage(inputDto));
            wrapper = new JsonEntityWrapper<>(dto);
            wrapper.addMessage(new CreatedMessage("Message created with success"));
        } catch (Exception e) {
            wrapper = unexpectedErrorWrapper(response, e);
        }

        response.status(HttpServletResponse.SC_CREATED);
        return mapper.writeValueAsString(wrapper);
    }

    private MessageDto buildMessageFromCreationDto(CreateMessageDto creationDto) {
        MessageDto dto = new MessageDto();

        dto.title = creationDto.title;
        dto.body = creationDto.body;
        dto.expires = creationDto.expires;
        dto.latitude = Double.valueOf(creationDto.latitude);
        dto.longitude = Double.valueOf(creationDto.longitude);
        dto.visibilityStartTime = creationDto.visibilityStartTime;
        dto.visibilityEndTime = creationDto.visibilityEndTime;

        return dto;
    }

    private MessageDto buildMessageFromSearchDto(MessageSearchDto searchDto) {
        MessageDto dto = new MessageDto();

        dto.latitude = Double.valueOf(searchDto.latitude);
        dto.longitude = Double.valueOf(searchDto.longitude);

        return dto;
    }


}
