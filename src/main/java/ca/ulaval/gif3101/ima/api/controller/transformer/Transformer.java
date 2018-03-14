package ca.ulaval.gif3101.ima.api.controller.transformer;

import ca.ulaval.gif3101.ima.api.controller.message.dto.MessageDtoResponse;
import ca.ulaval.gif3101.ima.api.domain.message.MessageDto;

import java.util.List;

public interface Transformer<U, T> {
    List<U> transform(List<T> inDtos);

    U transform(T inDto);
}
