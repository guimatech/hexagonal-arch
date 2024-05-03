package io.github.guimatech.hexagonal.application.usecases;

import io.github.guimatech.hexagonal.application.exceptions.ValidationException;
import io.github.guimatech.hexagonal.infraestructure.models.Partner;
import io.github.guimatech.hexagonal.infraestructure.services.PartnerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CreatePartnerUseCaseTest {

    @Test
    @DisplayName("Deve criar um parceiro")
    void testCreate() {
        // given
        final var expectedCNPJ = "12345678901234";
        final var expectedEmail = "john.doe@gmail.com";
        final var expectedName = "John Doe";

        final var createInput = new CreatePartnerUseCase.Input(expectedCNPJ, expectedEmail, expectedName);

        // when
        final var partnerService = mock(PartnerService.class);
        when(partnerService.findByCnpj(expectedCNPJ)).thenReturn(java.util.Optional.empty());
        when(partnerService.findByEmail(expectedEmail)).thenReturn(java.util.Optional.empty());
        when(partnerService.save(any())).thenAnswer(invocation -> {
            var partner = invocation.getArgument(0, Partner.class);
            partner.setId(UUID.randomUUID().getLeastSignificantBits());
            return partner;
        });

        final var useCase = new CreatePartnerUseCase(partnerService);
        final var output = useCase.execute(createInput);

        // then
        Assertions.assertNotNull(output.id());
        Assertions.assertEquals(expectedCNPJ, output.cnpj());
        Assertions.assertEquals(expectedEmail, output.email());
        Assertions.assertEquals(expectedName, output.name());
    }

    @Test
    @DisplayName("Não deve cadastrar um parceiro com CNPJ duplicado")
    void testCreateWithDuplicatedCNPJShouldFail() {
        // given
        final var expectedCNPJ = "12345678901234";
        final var expectedEmail = "john.doe@gmail.com";
        final var expectedName = "John Doe";
        final var expectedError = "Partner already exists";

        final var createInput = new CreatePartnerUseCase.Input(expectedCNPJ, expectedEmail, expectedName);

        final var aPartner = new Partner();
        aPartner.setId(UUID.randomUUID().getMostSignificantBits());
        aPartner.setCnpj(expectedCNPJ);
        aPartner.setName(expectedName);
        aPartner.setEmail(expectedEmail);

        // when
        final var partnerService = mock(PartnerService.class);
        when(partnerService.findByCnpj(expectedCNPJ)).thenReturn(java.util.Optional.of(aPartner));

        final var useCase = new CreatePartnerUseCase(partnerService);
        final var exception = assertThrows(ValidationException.class, () -> useCase.execute(createInput));

        // then
        assertEquals(expectedError, exception.getMessage());
    }

    @Test
    @DisplayName("Não deve cadastrar um parceiro com email duplicado")
    void testCreateWithDuplicatedEmailShouldFail() {
        // given
        final var expectedCNPJ = "12345678901234";
        final var expectedEmail = "john.doe@gmail.com";
        final var expectedName = "John Doe";
        final var expectedError = "Partner already exists";

        final var createInput = new CreatePartnerUseCase.Input(expectedCNPJ, expectedEmail, expectedName);

        final var aPartner = new Partner();
        aPartner.setId(UUID.randomUUID().getMostSignificantBits());
        aPartner.setCnpj(expectedCNPJ);
        aPartner.setName(expectedName);
        aPartner.setEmail(expectedEmail);

        // when
        final var partnerService = mock(PartnerService.class);
        when(partnerService.findByEmail(expectedEmail)).thenReturn(java.util.Optional.of(aPartner));

        final var useCase = new CreatePartnerUseCase(partnerService);
        final var exception = assertThrows(ValidationException.class, () -> useCase.execute(createInput));

        // then
        assertEquals(expectedError, exception.getMessage());
    }

}