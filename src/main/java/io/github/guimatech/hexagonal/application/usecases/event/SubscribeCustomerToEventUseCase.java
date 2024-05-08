package io.github.guimatech.hexagonal.application.usecases.event;

import io.github.guimatech.hexagonal.application.UseCase;
import io.github.guimatech.hexagonal.application.domain.customer.CustomerId;
import io.github.guimatech.hexagonal.application.domain.event.EventId;
import io.github.guimatech.hexagonal.application.domain.event.ticket.Ticket;
import io.github.guimatech.hexagonal.application.exceptions.ValidationException;
import io.github.guimatech.hexagonal.application.repositories.CustomerRepository;
import io.github.guimatech.hexagonal.application.repositories.EventRepository;
import io.github.guimatech.hexagonal.application.repositories.TicketRepository;

import java.time.Instant;
import java.util.Objects;

public class SubscribeCustomerToEventUseCase extends UseCase<SubscribeCustomerToEventUseCase.Input, SubscribeCustomerToEventUseCase.Output> {

    private final CustomerRepository customerRepository;
    private final EventRepository eventRepository;
    private final TicketRepository ticketRepository;

    public SubscribeCustomerToEventUseCase(
            final CustomerRepository customerRepository,
            final EventRepository eventRepository,
            final TicketRepository ticketRepository
    ) {
        this.customerRepository = Objects.requireNonNull(customerRepository);
        this.eventRepository = Objects.requireNonNull(eventRepository);
        this.ticketRepository = Objects.requireNonNull(ticketRepository);
    }

    @Override
    public Output execute(final Input input) {
        var aCustomer = customerRepository.customerOfId(CustomerId.with(input.customerId()))
                .orElseThrow(() -> new ValidationException("Customer not found"));

        var anEvent = eventRepository.eventOfId(EventId.with(input.eventId()))
                .orElseThrow(() -> new ValidationException("Event not found"));

        final Ticket ticket = anEvent.reserveTicket(aCustomer.customerId());

        ticketRepository.create(ticket);
        eventRepository.update(anEvent);

        return new Output(anEvent.eventId().value(), ticket.ticketId().value(), ticket.status().name(), ticket.reservedAt());
    }

    public record Input(String customerId, String eventId) {
    }

    public record Output(String eventId, String ticketId, String ticketStatus, Instant reservationDate) {
    }
}