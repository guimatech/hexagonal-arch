package io.github.guimatech.domain.event.ticket;

import java.util.Optional;

public interface TicketRepository {

    Optional<Ticket> ticketOfId(final TicketId anId);

    Ticket create(final Ticket ticket);

    Ticket update(final Ticket ticket);

    void deleteAll();
}
