package io.github.guimatech.hexagonal.application.usecases;

import io.github.guimatech.hexagonal.application.UseCase;
import io.github.guimatech.hexagonal.application.exceptions.ValidationException;
import io.github.guimatech.hexagonal.infraestructure.models.Ticket;
import io.github.guimatech.hexagonal.infraestructure.models.TicketStatus;
import io.github.guimatech.hexagonal.infraestructure.services.CustomerService;
import io.github.guimatech.hexagonal.infraestructure.services.EventService;

import java.time.Instant;
import java.util.Objects;

public class SubscribeCustomerToEventUseCase
        extends UseCase<SubscribeCustomerToEventUseCase.Input, SubscribeCustomerToEventUseCase.Output> {

    private final CustomerService customerService;
    private final EventService eventService;

    public SubscribeCustomerToEventUseCase(final CustomerService customerService, final EventService eventService) {
        this.customerService = Objects.requireNonNull(customerService);
        this.eventService = Objects.requireNonNull(eventService);
    }

    @Override
    public Output execute(final Input input) {
        var customer = customerService.findById(input.customerId())
                .orElseThrow(() -> new ValidationException("Customer not found"));

        var event = eventService.findById(input.eventId())
                .orElseThrow(() -> new ValidationException("Event not found"));

        eventService.findTicketByEventIdAndCustomerId(input.eventId, input.customerId)
                .ifPresent(ticket -> {
                    throw new ValidationException("Customer already subscribed to this event");
                });

        if (event.getTotalSpots() < event.getTickets().size() + 1) {
            throw new ValidationException("Event sold out");
        }

        var ticket = new Ticket();
        ticket.setEvent(event);
        ticket.setCustomer(customer);
        ticket.setReservedAt(Instant.now());
        ticket.setStatus(TicketStatus.PENDING);

        event.getTickets().add(ticket);

        eventService.save(event);

        return new Output(event.getId(), ticket.getStatus().name(), ticket.getReservedAt());
    }

    public record Input(Long eventId, Long customerId) {
    }

    public record Output(Long eventId, String ticketStatus, Instant reservationDate) {
    }
}
