package io.github.guimatech.domain.event.ticket;

import io.github.guimatech.domain.exceptions.ValidationException;

import java.util.UUID;

public record TicketId(String value) {

    public TicketId {
        if (value == null) {
            throw new ValidationException("Invalid value for TicketId");
        }
    }

    public static TicketId unique() {
        return new TicketId(UUID.randomUUID().toString());
    }

    public static TicketId with(final String value) {
        try {
            return new TicketId(UUID.fromString(value).toString());
        } catch (IllegalArgumentException ex) {
            throw new ValidationException("Invalid value for TicketId");
        }
    }
}
