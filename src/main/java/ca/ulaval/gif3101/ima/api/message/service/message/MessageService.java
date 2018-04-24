package ca.ulaval.gif3101.ima.api.message.service.message;

import ca.ulaval.gif3101.ima.api.message.domain.message.*;
import ca.ulaval.gif3101.ima.api.message.domain.message.query.MessageQuery;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MessageService {

    private MessageAssembler messageAssembler;
    private MessageFactory messageFactory;
    private MessageRepository messageRepository;

    public MessageService(MessageAssembler messageAssembler, MessageFactory messageFactory, MessageRepository messageRepository) {
        this.messageAssembler = messageAssembler;
        this.messageFactory = messageFactory;
        this.messageRepository = messageRepository;
    }

    public MessageDto createMessage(MessageDto dto) {
        Message message = messageFactory.create(dto);
        messageRepository.save(message);

        return messageAssembler.createDto(message);
    }

    public List<MessageDto> getAll() {
        List<Message> messages = messageRepository.findAll();
        return createDtos(messages);
    }

    public MessageDto getOne(MessageDto dto) throws Exception {
        Message message = messageRepository.findOneById(dto.id);
        return messageAssembler.createDto(message);
    }

    private List<MessageDto> createDtos(List<Message> messages) {
        List<MessageDto> dtos = new ArrayList<>();
        for (Message message : messages) {
            dtos.add(messageAssembler.createDto(message));
        }
        return dtos;
    }

    public List<MessageDto> getAllFiltered(MessageQuery query) {
        List<Message> messages = messageRepository.findFiltered(query);

        messages.sort(new Comparator<Message>() {
            @Override
            public int compare(Message lhs, Message rhs) {
                return lhs.getCalculatedDistance().compare(rhs.getCalculatedDistance());
            }
        });

        return createDtos(messages);
    }


}
