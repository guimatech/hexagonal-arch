package io.github.guimatech.hexagonal.application.usecases;

import io.github.guimatech.hexagonal.application.exceptions.ValidationException;
import io.github.guimatech.hexagonal.models.Event;
import io.github.guimatech.hexagonal.models.Partner;
import io.github.guimatech.hexagonal.services.EventService;
import io.github.guimatech.hexagonal.services.PartnerService;
import io.hypersistence.tsid.TSID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CreateEventUseCaseTest {

    @Test
    @DisplayName("Deve criar um evento")
    void testCreate() {
        // given
        var expectedDate = "2021-12-31";
        var expectedName = "Event Name";
        var expectedTotalSpots = 10;
        var expectedPartnerId = TSID.fast().toLong();

        final var createInput =
                new CreateEventUseCase.Input(expectedDate, expectedName, expectedPartnerId, expectedTotalSpots);

        // when
        final var eventService = mock(EventService.class);
        final var partnerService = mock(PartnerService.class);

        when(partnerService.findById(expectedPartnerId)).thenReturn(Optional.of(new Partner()));
        when(eventService.save(any())).thenAnswer(invocation -> {
            var event = invocation.getArgument(0, Event.class);
            event.setId(TSID.fast().toLong());
            return event;
        });
        final var useCase = new CreateEventUseCase(eventService, partnerService);
        final var output = useCase.execute(createInput);

        // then
        Assertions.assertNotNull(output.id());
        Assertions.assertEquals(expectedDate, output.date());
        Assertions.assertEquals(expectedName, output.name());
        Assertions.assertEquals(expectedTotalSpots, output.totalSpots());
        Assertions.assertEquals(expectedPartnerId, output.partnerId());
    }

    @Test
    @DisplayName("Não deve criar um evento com parceiro inexistente")
    void testCreateWithNonExistentPartnerShouldFail() {
        // given
        var expectedDate = "2021-12-31";
        var expectedName = "Event Name";
        var expectedTotalSpots = 10;
        var expectedPartnerId = TSID.fast().toLong();
        var expectedError = "Partner not found";

        final var createInput =
                new CreateEventUseCase.Input(expectedDate, expectedName, expectedPartnerId, expectedTotalSpots);

        // when
        final var eventService = mock(EventService.class);
        final var partnerService = mock(PartnerService.class);

        when(partnerService.findById(expectedPartnerId)).thenReturn(Optional.empty());

        final var useCase = new CreateEventUseCase(eventService, partnerService);
        final var actualException = Assertions.assertThrows(ValidationException.class, () -> useCase.execute(createInput));
        // then
        Assertions.assertEquals(expectedError, actualException.getMessage());
    }
}