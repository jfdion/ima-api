package ca.ulaval.gif3101.ima.api.controller.message;

import ca.ulaval.gif3101.ima.api.controller.message.dto.CreateMessageDto;
import ca.ulaval.gif3101.ima.api.controller.message.dto.MessageDto;
import ca.ulaval.gif3101.ima.api.http.json.message.OkMessage;
import ca.ulaval.gif3101.ima.api.http.json.wrapper.JsonCollectionWrapper;
import ca.ulaval.gif3101.ima.api.http.json.wrapper.JsonEmptyWrapper;
import ca.ulaval.gif3101.ima.api.http.json.wrapper.JsonEntityWrapper;
import ca.ulaval.gif3101.ima.api.http.json.wrapper.JsonWrapper;
import ca.ulaval.gif3101.ima.api.http.json.message.UnexpectedErrorMessage;
import ca.ulaval.gif3101.ima.api.http.queryFilter.QueryFilter;
import ca.ulaval.gif3101.ima.api.http.queryFilter.QueryFilterFactory;
import ca.ulaval.gif3101.ima.api.service.MessageService;
import ca.ulaval.gif3101.ima.api.utils.Utils;
import com.fasterxml.jackson.databind.ObjectMapper;
import spark.Request;
import spark.Response;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
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

    public Object getAll(Request request, Response response) throws Exception{

        JsonWrapper wrapper;
        try {
            List<MessageDto> dtos = messageService.getAll();
            QueryFilter queryFilter = queryFilterFactory.create(request, dtos, MessageDto.class);
            wrapper = new JsonCollectionWrapper<>(dtos, queryFilter);
        } catch (Exception e) {
            wrapper = new JsonEmptyWrapper();
            wrapper.addMessage(new UnexpectedErrorMessage(e.getMessage()));
        }

        response.status(HttpServletResponse.SC_OK);
        return mapper.writeValueAsString(wrapper);
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
