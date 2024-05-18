package io.github.guimatech.domain.event;

import io.github.guimatech.domain.exceptions.ValidationException;

import java.util.Objects;
import java.util.UUID;

public record EventTicketId(String value) {

    public EventTicketId {
        if (Objects.isNull(value)) {
            throw new ValidationException("Invalid value for EventTicketId");
        }
    }

    public static EventTicketId unique() {
        return new EventTicketId(UUID.randomUUID().toString());
    }

    public static EventTicketId with(String value) {
        try {
            return new EventTicketId(UUID.fromString(value).toString());
        } catch (IllegalArgumentException e) {
            throw new ValidationException("Invalid UUID string: " + value);
        }
    }
}
