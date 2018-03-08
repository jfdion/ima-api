package ca.ulaval.gif3101.ima.api.service;

import ca.ulaval.gif3101.ima.api.controller.message.dto.MessageDto;
import ca.ulaval.gif3101.ima.api.domain.message.Message;
import ca.ulaval.gif3101.ima.api.domain.message.MessageAssembler;
import ca.ulaval.gif3101.ima.api.domain.message.MessageRepository;

import java.util.ArrayList;
import java.util.List;

public class MessageService {

    private MessageAssembler messageAssembler;
    private MessageRepository messageRepository;

    public MessageService(MessageAssembler messageAssembler, MessageRepository messageRepository) {
        this.messageAssembler = messageAssembler;
        this.messageRepository = messageRepository;
    }

    public MessageDto createMessage(MessageDto dto) throws Exception {
        Message message = messageAssembler.create(dto);
        messageRepository.save(message);

        return messageAssembler.createDto(message);
    }

    public List<MessageDto> getAll() {
        List<Message> messages = messageRepository.findAll();

        List<MessageDto> dtos = new ArrayList<>();
        for(Message message : messages) {
            dtos.add(messageAssembler.createDto(message));
        }
        return dtos;
    }


}
