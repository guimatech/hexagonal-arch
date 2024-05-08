package io.github.guimatech.hexagonal.application.usecases.event;

import io.hypersistence.tsid.TSID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SubscribeCustomerToEventUseCaseTest {

    @Test
    @DisplayName("Deve inscrever um cliente em um evento")
    void testSubscribe() {
        // given
        final var expectedTicketSize = 1;
        final var customerID = TSID.fast().toLong();
        final var eventID = TSID.fast().toLong();

        final var anEvent = new Event();
        anEvent.setId(eventID);
        anEvent.setName("Event Name");
        anEvent.setTotalSpots(10);

        final var subscribeInput =
                new SubscribeCustomerToEventUseCase.Input(anEvent.getId(), customerID);

        // when
        final var customerService = mock(CustomerService.class);
        final var eventService = mock(EventService.class);

        when(customerService.findById(customerID)).thenReturn(Optional.of(new Customer()));
        when(eventService.findById(eventID)).thenReturn(Optional.of(anEvent));
        when(eventService.findTicketByEventIdAndCustomerId(eventID, customerID)).thenReturn(Optional.empty());
        when(eventService.save(anEvent)).thenAnswer(invocation -> {
            var event = invocation.getArgument(0, Event.class);
            assertEquals(expectedTicketSize, event.getTickets().size());
            return event;
        });

        final var useCase = new SubscribeCustomerToEventUseCase(customerService, eventService);
        final var output = useCase.execute(subscribeInput);

        // then
        Assertions.assertEquals(eventID, output.eventId());
        Assertions.assertNotNull(output.reservationDate());
        Assertions.assertEquals(TicketStatus.PENDING.name(), output.ticketStatus());
    }

    @Test
    @DisplayName("Não deve comprar um ingresso para um evento inexistente")
    void testSubscribeToNonExistentEvent() {
        // given
        final var customerID = TSID.fast().toLong();
        final var eventID = TSID.fast().toLong();

        final var subscribeInput =
                new SubscribeCustomerToEventUseCase.Input(eventID, customerID);

        // when
        final var customerService = mock(CustomerService.class);
        final var eventService = mock(EventService.class);

        when(customerService.findById(customerID)).thenReturn(Optional.of(new Customer()));
        when(eventService.findById(eventID)).thenReturn(Optional.empty());

        final var useCase = new SubscribeCustomerToEventUseCase(customerService, eventService);
        final var actualException = Assertions.assertThrows(RuntimeException.class, () -> useCase.execute(subscribeInput));

        // then
        Assertions.assertEquals("Event not found", actualException.getMessage());
    }

    @Test
    @DisplayName("Não deve comprar um ingresso para um cliente inexistente")
    void testSubscribeToNonExistentCustomer() {
        // given
        final var customerID = TSID.fast().toLong();
        final var eventID = TSID.fast().toLong();

        final var subscribeInput =
                new SubscribeCustomerToEventUseCase.Input(eventID, customerID);

        // when
        final var customerService = mock(CustomerService.class);
        final var eventService = mock(EventService.class);

        when(customerService.findById(customerID)).thenReturn(Optional.empty());

        final var useCase = new SubscribeCustomerToEventUseCase(customerService, eventService);
        final var actualException = Assertions.assertThrows(RuntimeException.class, () -> useCase.execute(subscribeInput));

        // then
        Assertions.assertEquals("Customer not found", actualException.getMessage());
    }

    @Test
    @DisplayName("Não deve comprar um ingresso para um evento lotado")
    void testSubscribeToSoldOutEvent() {
        // given
        final var customerID = TSID.fast().toLong();
        final var eventID = TSID.fast().toLong();

        final var anEvent = new Event();
        anEvent.setId(eventID);
        anEvent.setName("Event Name");
        anEvent.setTotalSpots(0);

        final var subscribeInput =
                new SubscribeCustomerToEventUseCase.Input(eventID, customerID);

        // when
        final var customerService = mock(CustomerService.class);
        final var eventService = mock(EventService.class);

        when(customerService.findById(customerID)).thenReturn(Optional.of(new Customer()));
        when(eventService.findById(eventID)).thenReturn(Optional.of(anEvent));

        final var useCase = new SubscribeCustomerToEventUseCase(customerService, eventService);
        final var actualException = Assertions.assertThrows(RuntimeException.class, () -> useCase.execute(subscribeInput));

        // then
        Assertions.assertEquals("Event sold out", actualException.getMessage());
    }

    @Test
    @DisplayName("Não deve comprar um ingresso para um evento que o cliente já está inscrito")
    void testSubscribeToAlreadySubscribedEvent() {
        // given
        final var customerID = TSID.fast().toLong();
        final var eventID = TSID.fast().toLong();

        final var anEvent = new Event();
        anEvent.setId(eventID);
        anEvent.setName("Event Name");
        anEvent.setTotalSpots(10);

        final var subscribeInput =
                new SubscribeCustomerToEventUseCase.Input(eventID, customerID);

        // when
        final var customerService = mock(CustomerService.class);
        final var eventService = mock(EventService.class);

        when(customerService.findById(customerID)).thenReturn(Optional.of(new Customer()));
        when(eventService.findById(eventID)).thenReturn(Optional.of(anEvent));
        when(eventService.findTicketByEventIdAndCustomerId(eventID, customerID)).thenReturn(Optional.of(new Ticket()));

        final var useCase = new SubscribeCustomerToEventUseCase(customerService, eventService);
        final var actualException = Assertions.assertThrows(RuntimeException.class, () -> useCase.execute(subscribeInput));

        // then
        Assertions.assertEquals("Customer already subscribed to this event", actualException.getMessage());
    }
}