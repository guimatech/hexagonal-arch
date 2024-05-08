package io.github.guimatech.hexagonal.application.repositories;

import io.github.guimatech.hexagonal.application.domain.event.ticket.Ticket;
import io.github.guimatech.hexagonal.application.domain.event.ticket.TicketId;

import java.util.Optional;

public interface TicketRepository {

    Optional<Ticket> ticketOfId(final TicketId anId);

    Ticket create(final Ticket ticket);

    Ticket update(final Ticket ticket);

    void deleteAll();
}
