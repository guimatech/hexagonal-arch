package io.github.guimatech.application.ticket;

import io.github.guimatech.application.UseCase;
import io.github.guimatech.domain.customer.CustomerId;
import io.github.guimatech.domain.event.EventId;
import io.github.guimatech.domain.event.EventTicketId;
import io.github.guimatech.domain.event.ticket.Ticket;
import io.github.guimatech.domain.event.ticket.TicketRepository;

import java.util.Objects;

public class CreateTicketForCustomerUseCase
        extends UseCase<CreateTicketForCustomerUseCase.Input, CreateTicketForCustomerUseCase.Output> {

    private final TicketRepository ticketRepository;

    public CreateTicketForCustomerUseCase(final TicketRepository ticketRepository) {
        this.ticketRepository = Objects.requireNonNull(ticketRepository);
    }

    @Override
    public Output execute(final Input input) {

        final var aTicket =
                Ticket.newTicket(EventTicketId.with(input.eventTicketId), CustomerId.with(input.customerId), EventId.with(input.eventId));

        this.ticketRepository.create(aTicket);

        return new Output(aTicket.ticketId().value());
    }

    public record Input(String eventTicketId, String eventId, String customerId) {}

    public record Output(String ticketId) {}
}
