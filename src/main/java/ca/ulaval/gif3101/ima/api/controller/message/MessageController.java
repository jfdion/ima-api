package ca.ulaval.gif3101.ima.api.controller.message;

import ca.ulaval.gif3101.ima.api.controller.message.dto.CreateMessageDto;
import ca.ulaval.gif3101.ima.api.controller.message.dto.MessageDto;
import ca.ulaval.gif3101.ima.api.domain.location.Location;
import ca.ulaval.gif3101.ima.api.http.json.message.NoContentMessage;
import ca.ulaval.gif3101.ima.api.http.json.message.OkMessage;
import ca.ulaval.gif3101.ima.api.http.json.wrapper.JsonCollectionWrapper;
import ca.ulaval.gif3101.ima.api.http.json.wrapper.JsonEmptyWrapper;
import ca.ulaval.gif3101.ima.api.http.json.wrapper.JsonEntityWrapper;
import ca.ulaval.gif3101.ima.api.http.json.wrapper.JsonWrapper;
import ca.ulaval.gif3101.ima.api.http.json.message.UnexpectedErrorMessage;
import ca.ulaval.gif3101.ima.api.http.queryFilter.QueryFilter;
import ca.ulaval.gif3101.ima.api.http.queryFilter.QueryFilterFactory;
import ca.ulaval.gif3101.ima.api.http.queryFilter.exception.EmptyPageException;
import ca.ulaval.gif3101.ima.api.infrastructure.message.filter.FilterConfig;
import ca.ulaval.gif3101.ima.api.service.MessageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import spark.Request;
import spark.Response;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class MessageController {

    private MessageService messageService;
    private ObjectMapper mapper;
    private QueryFilterFactory queryFilterFactory;

    public MessageController(MessageService messageService, ObjectMapper mapper, QueryFilterFactory queryFilterFactory) {
        this.messageService = messageService;
        this.mapper = mapper;
        this.queryFilterFactory = queryFilterFactory;
    }

    public Object getAll(Request request, Response response) throws Exception {

        JsonWrapper wrapper;
        try {
            FilterConfig filterConfig = initFilterConfig(request);
            List<MessageDto> dtos = messageService.getAllFiltered(filterConfig);
            QueryFilter queryFilter = queryFilterFactory.create(request, dtos, MessageDto.class);
            wrapper = new JsonCollectionWrapper<>(dtos, queryFilter);
            response.status(HttpServletResponse.SC_OK);
        } catch(EmptyPageException e) {
            wrapper = new JsonEmptyWrapper();
            wrapper.addMessage(new NoContentMessage(e.getMessage()));
            response.status(HttpServletResponse.SC_NO_CONTENT);
        } catch (Exception e) {
            wrapper = new JsonEmptyWrapper();
            wrapper.addMessage(new UnexpectedErrorMessage(e.getMessage()));
            response.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

        return mapper.writeValueAsString(wrapper);
    }

    private FilterConfig initFilterConfig(Request request) {
        FilterConfig filterConfig = new FilterConfig();
        if (request.queryParams("location") != null) {
            filterConfig.fromLocation = new Location(request.queryParams("location"));
        }
        if (request.queryParams("location-scope") != null) {
            filterConfig.locationScope = FilterConfig.LocationScope.valueOf(request.queryParams("location-scope").toUpperCase());
        }
        return filterConfig;
    }

    public Object create(Request request, Response response) throws Exception {
        MessageDto inputDto = buildMessageDto(mapper.readValue(request.body(), CreateMessageDto.class));

        JsonWrapper wrapper;
        try {
            MessageDto dto = messageService.createMessage(inputDto);
            wrapper = new JsonEntityWrapper<>(dto);
            wrapper.addMessage(new OkMessage("Message created with success"));
        } catch (Exception e) {
            wrapper = new JsonEmptyWrapper();
            wrapper.addMessage(new UnexpectedErrorMessage(e.getMessage()));
        }

        response.status(HttpServletResponse.SC_CREATED);
        return mapper.writeValueAsString(wrapper);
    }

    private MessageDto buildMessageDto(CreateMessageDto creationDto) {
        MessageDto dto = new MessageDto();
        dto.title = creationDto.title;
        dto.body = creationDto.body;
        dto.expires = creationDto.expires;
        dto.latitude = Double.valueOf(creationDto.latitude);
        dto.longitude = Double.valueOf(creationDto.longitude);
        return dto;
    }
}
