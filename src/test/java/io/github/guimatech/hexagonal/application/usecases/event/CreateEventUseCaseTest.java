package io.github.guimatech.hexagonal.application.usecases.event;

import io.github.guimatech.hexagonal.application.repository.InMemoryEventRepository;
import io.github.guimatech.hexagonal.application.repository.InMemoryPartnerRepository;
import io.github.guimatech.hexagonal.application.domain.partner.Partner;
import io.github.guimatech.hexagonal.application.domain.partner.PartnerId;
import io.github.guimatech.hexagonal.application.exceptions.ValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CreateEventUseCaseTest {

    @Test
    @DisplayName("Deve criar um evento")
    void testCreate() {
        // given
        final var aPartner =
                Partner.newPartner("John Doe", "41.000.000/0001-00", "john.doe@gmail.com");
        final var expectedDate = "2021-12-31";
        final var expectedName = "Disney World Trip";
        final var expectedTotalSpots = 10;
        final var expectedPartnerId = aPartner.partnerId().value();

        final var createInput =
                new CreateEventUseCase.Input(expectedDate, expectedName, expectedPartnerId, expectedTotalSpots);

        final var eventRepository = new InMemoryEventRepository();
        final var partnerRepository = new InMemoryPartnerRepository();

        partnerRepository.create(aPartner);

        // when
        final var useCase = new CreateEventUseCase(eventRepository, partnerRepository);
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
        var expectedName = "Disney World Trip";
        var expectedTotalSpots = 10;
        var expectedPartnerId = PartnerId.unique().value();
        var expectedError = "Partner not found";

        final var createInput =
                new CreateEventUseCase.Input(expectedDate, expectedName, expectedPartnerId, expectedTotalSpots);

        final var eventRepository = new InMemoryEventRepository();
        final var partnerRepository = new InMemoryPartnerRepository();

        // when
        final var useCase = new CreateEventUseCase(eventRepository, partnerRepository);
        final var actualException = Assertions.assertThrows(ValidationException.class, () -> useCase.execute(createInput));

        // then
        Assertions.assertEquals(expectedError, actualException.getMessage());
    }
}