package io.github.guimatech.domain.event;

import io.github.guimatech.domain.customer.CustomerId;
import io.github.guimatech.domain.event.ticket.TicketId;
import io.github.guimatech.domain.exceptions.ValidationException;

public class EventTicket {

    private final EventTicketId eventTicketId;
    private final EventId eventId;
    private final CustomerId customerId;
    private TicketId ticketId;
    private int ordering;

    public EventTicket(final EventTicketId eventTicketId, final EventId eventId, final CustomerId customerId,
                       final TicketId ticketId,final Integer ordering) {
        if (eventTicketId == null) {
            throw new ValidationException("Invalid eventTicketId for EventTicket");
        }

        if (eventId == null) {
            throw new ValidationException("Invalid eventId for EventTicket");
        }

        if (customerId == null) {
            throw new ValidationException("Invalid customerId for EventTicket");
        }

        this.eventTicketId = eventTicketId;
        this.eventId = eventId;
        this.customerId = customerId;
        this.ticketId = ticketId;
        this.setOrdering(ordering);
    }

    public static EventTicket newTicket(final EventId eventId, final CustomerId customerId, final Integer ordering) {
        return new EventTicket(EventTicketId.unique(), eventId, customerId, null, ordering);
    }

    public EventTicket associateTicket(final TicketId ticketId) {
        this.ticketId = ticketId;
        return this;
    }

    public EventTicketId eventTicketId() {
        return eventTicketId;
    }

    public EventId eventId() {
        return eventId;
    }

    public TicketId ticketId() {
        return ticketId;
    }

    public int ordering() {
        return ordering;
    }

    public CustomerId customerId() {
        return customerId;
    }

    private void setOrdering(final Integer ordering) {
        if (ordering == null) {
            throw new ValidationException("Invalid ordering for EventTicket");
        }

        this.ordering = ordering;
    }
}
