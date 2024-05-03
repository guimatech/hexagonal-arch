package io.github.guimatech.hexagonal.application.usecases;

import io.github.guimatech.hexagonal.infraestructure.models.Partner;
import io.github.guimatech.hexagonal.infraestructure.services.PartnerService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GetPartnerByIdUseCaseTest {

    @Test
    @DisplayName("Deve retornar um parceiro")
    void testGetPartnerById() {
        // given
        final var expectedId = 1L;
        final var expectedCnpj = "12345678901234";
        final var expectedEmail = "john.doe@gmail.com";
        final var expectedName = "John Doe";

        final var aPartner = new Partner();
        aPartner.setId(expectedId);
        aPartner.setCnpj(expectedCnpj);
        aPartner.setEmail(expectedEmail);
        aPartner.setName(expectedName);

        final var input = new GetPartnerByIdUseCase.Input(expectedId);

        // when
        final var partnerService = mock(PartnerService.class);
        when(partnerService.findById(expectedId)).thenReturn(Optional.of(aPartner));
        
        final var useCase = new GetPartnerByIdUseCase(partnerService);
        final var output = useCase.execute(input).get();

        // then
        assertEquals(expectedId, output.id());
        assertEquals(expectedCnpj, output.cnpj());
        assertEquals(expectedEmail, output.email());
        assertEquals(expectedName, output.name());
    }

    @Test
    @DisplayName("Deve retornar vazio quando não encontrar um parceiro")
    void testGetPartnerByIdNotFound() {
        // given
        final var expectedId = UUID.randomUUID().getMostSignificantBits();

        final var input = new GetPartnerByIdUseCase.Input(expectedId);

        // when
        final var partnerService = mock(PartnerService.class);
        when(partnerService.findById(expectedId)).thenReturn(Optional.empty());

        final var useCase = new GetPartnerByIdUseCase(partnerService);
        final var output = useCase.execute(input);

        // then
        assertTrue(output.isEmpty());
    }

}