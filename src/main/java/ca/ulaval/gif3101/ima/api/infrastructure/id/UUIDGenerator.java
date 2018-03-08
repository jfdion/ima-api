package ca.ulaval.gif3101.ima.api.infrastructure.id;

import java.util.UUID;

public class UUIDGenerator implements IdGenerator {

    @Override
    public String generate() {
        return UUID.randomUUID().toString();
    }
}
