package io.github.guimatech.hexagonal.application.usecases.partner;

import io.github.guimatech.hexagonal.application.repository.InMemoryPartnerRepository;
import io.github.guimatech.hexagonal.application.domain.partner.Partner;
import io.github.guimatech.hexagonal.application.exceptions.ValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CreatePartnerUseCaseTest {

    @Test
    @DisplayName("Deve criar um parceiro")
    void testCreatePartner() {
        // given
        final var expectedCNPJ = "41.536.538/0001-00";
        final var expectedEmail = "john.doe@gmail.com";
        final var expectedName = "John Doe";

        final var createInput = new CreatePartnerUseCase.Input(expectedCNPJ, expectedEmail, expectedName);

        // when
        final var partnerRepository = new InMemoryPartnerRepository();
        final var useCase = new CreatePartnerUseCase(partnerRepository);
        final var output = useCase.execute(createInput);

        // then
        Assertions.assertNotNull(output.id());
        Assertions.assertEquals(expectedCNPJ, output.cnpj());
        Assertions.assertEquals(expectedEmail, output.email());
        Assertions.assertEquals(expectedName, output.name());
    }

    @Test
    @DisplayName("Não deve cadastrar um parceiro com CNPJ duplicado")
    void testCreateWithDuplicatedCNPJShouldFail() throws Exception {
        // given
        final var expectedCNPJ = "41.536.538/0001-00";
        final var expectedEmail = "john.doe@gmail.com";
        final var expectedName = "John Doe";
        final var expectedError = "Partner already exists";

        final var aPartner = Partner.newPartner(expectedName, "41.536.538/0002-00", expectedEmail);

        final var partnerRepository = new InMemoryPartnerRepository();
        partnerRepository.create(aPartner);

        final var createInput = new CreatePartnerUseCase.Input(expectedCNPJ, expectedEmail, expectedName);

        // when
        final var useCase = new CreatePartnerUseCase(partnerRepository);
        final var actualException = Assertions.assertThrows(ValidationException.class, () -> useCase.execute(createInput));

        // then
        Assertions.assertEquals(expectedError, actualException.getMessage());
    }

    @Test
    @DisplayName("Não deve cadastrar um parceiro com e-mail duplicado")
    void testCreateWithDuplicatedEmailShouldFail() throws Exception {
        // given
        final var expectedCNPJ = "41.536.538/0001-00";
        final var expectedEmail = "john.doe@gmail.com";
        final var expectedName = "John Doe";
        final var expectedError = "Partner already exists";

        final var aPartner = Partner.newPartner(expectedName, "41.536.538/0002-00", expectedEmail);

        final var partnerRepository = new InMemoryPartnerRepository();
        partnerRepository.create(aPartner);

        final var createInput = new CreatePartnerUseCase.Input(expectedCNPJ, expectedEmail, expectedName);

        // when
        final var useCase = new CreatePartnerUseCase(partnerRepository);
        final var actualException = Assertions.assertThrows(ValidationException.class, () -> useCase.execute(createInput));

        // then
        Assertions.assertEquals(expectedError, actualException.getMessage());
    }
}