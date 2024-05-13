package io.github.guimatech.application.partner;

import io.github.guimatech.application.repository.InMemoryPartnerRepository;
import io.github.guimatech.domain.partner.Partner;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

class GetPartnerByIdUseCaseTest {

    @Test
    @DisplayName("Deve obter um parceiro por id")
    void testGetById() {
        // given
        final var expectedCNPJ = "41.536.538/0001-00";
        final var expectedEmail = "john.doe@gmail.com";
        final var expectedName = "John Doe";

        final var aPartner = Partner.newPartner(expectedName, expectedCNPJ, expectedEmail);

        final var partnerRepository = new InMemoryPartnerRepository();
        partnerRepository.create(aPartner);

        final var expectedID = aPartner.partnerId().value().toString();

        final var input = new GetPartnerByIdUseCase.Input(expectedID);

        // when
        final var useCase = new GetPartnerByIdUseCase(partnerRepository);
        final var output = useCase.execute(input).get();

        // then
        Assertions.assertEquals(expectedID, output.id());
        Assertions.assertEquals(expectedCNPJ, output.cnpj());
        Assertions.assertEquals(expectedEmail, output.email());
        Assertions.assertEquals(expectedName, output.name());
    }

    @Test
    @DisplayName("Deve obter vazio ao tentar recuperar um parceiro não existente por id")
    void testGetByIdWIthInvalidId() {
        // given
        final var expectedID = UUID.randomUUID().toString();

        final var input = new GetPartnerByIdUseCase.Input(expectedID);

        // when
        final var partnerRepository = new InMemoryPartnerRepository();
        final var useCase = new GetPartnerByIdUseCase(partnerRepository);
        final var output = useCase.execute(input);

        // then
        Assertions.assertTrue(output.isEmpty());
    }
}