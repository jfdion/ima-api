package ca.ulaval.gif3101.ima.api.service.transformer;

import ca.ulaval.gif3101.ima.api.controller.message.dto.MessageDtoResponse;
import ca.ulaval.gif3101.ima.api.domain.message.MessageDto;

import java.util.ArrayList;
import java.util.List;

public class MessageTransformer implements Transformer<MessageDtoResponse, MessageDto> {


    @Override
    public List<MessageDtoResponse> transform(List<MessageDto> inDtos) {
        List<MessageDtoResponse> outDtos = new ArrayList<>();
        for (MessageDto dto: inDtos) {
            outDtos.add(transform(dto));
        }
        return outDtos;
    }

    @Override
    public MessageDtoResponse transform(MessageDto inDto) {
        MessageDtoResponse outDto = new MessageDtoResponse();

        outDto.id = inDto.id;
        outDto.title = inDto.title;
        outDto.body = inDto.body;
        outDto.created = inDto.created;
        outDto.expires = inDto.expires;
        outDto.latitude = inDto.latitude;
        outDto.longitude = inDto.longitude;
        outDto.updateGoogleMaps();

        return outDto;
    }

}
