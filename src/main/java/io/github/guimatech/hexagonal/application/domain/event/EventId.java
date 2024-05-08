package io.github.guimatech.hexagonal.application.domain.event;

import io.github.guimatech.hexagonal.application.exceptions.ValidationException;

import java.util.Objects;
import java.util.UUID;

public record EventId(String value) {

    public EventId {
        if (Objects.isNull(value)) {
            throw new ValidationException("Invalid value for EventId");
        }
    }

    public static EventId unique() {
        return new EventId(UUID.randomUUID().toString());
    }

    public static EventId with(String value) {
        try {
            return new EventId(UUID.fromString(value).toString());
        } catch (IllegalArgumentException e) {
            throw new ValidationException("Invalid UUID string: " + value);
        }
    }
}
