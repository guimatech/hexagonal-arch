package io.github.guimatech.domain.event.ticket;

import io.github.guimatech.domain.DomainEvent;
import io.github.guimatech.domain.customer.CustomerId;
import io.github.guimatech.domain.event.EventId;
import io.github.guimatech.domain.event.EventTicketId;

import java.time.Instant;
import java.util.UUID;

public record TicketCreated(
        String domainEventId,
        String type,
        String ticketId,
        String eventTicketId,
        String eventId,
        String customerId,
        Instant occurredOn
) implements DomainEvent {

    public TicketCreated(TicketId ticketId, EventTicketId eventTicketId, EventId eventId, CustomerId customerId) {
        this(UUID.randomUUID().toString(), "ticket.created", ticketId.value(), eventTicketId.value(), eventId.value(), customerId.value(), Instant.now());
    }
}