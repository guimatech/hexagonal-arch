package io.github.guimatech.domain.event;

import io.github.guimatech.domain.DomainEvent;
import io.github.guimatech.domain.customer.CustomerId;

import java.time.Instant;
import java.util.UUID;

public record EventTicketReserved(
        String domainEventId,
        String type,
        String eventTicketId,
        String eventId,
        String customerId,
        Instant occurredOn
) implements DomainEvent {

    public EventTicketReserved(EventTicketId eventTicketId, EventId eventId, CustomerId customerId) {
        this(UUID.randomUUID().toString(), "event-ticket.reserved", eventTicketId.value(), eventId.value(), customerId.value(), Instant.now());
    }
}