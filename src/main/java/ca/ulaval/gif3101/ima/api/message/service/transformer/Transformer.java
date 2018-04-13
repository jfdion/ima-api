package ca.ulaval.gif3101.ima.api.message.service.transformer;

import java.util.List;

public interface Transformer<U, T> {
    List<U> transform(List<T> inDtos);

    U transform(T inDto);
}
