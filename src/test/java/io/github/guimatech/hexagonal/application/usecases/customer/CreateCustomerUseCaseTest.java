package io.github.guimatech.hexagonal.application.usecases.customer;

import io.github.guimatech.hexagonal.application.repository.InMemoryCustomerRepository;
import io.github.guimatech.hexagonal.application.domain.customer.Customer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CreateCustomerUseCaseTest {

    @Test
    @DisplayName("Deve criar um cliente")
    void testCreate() throws Exception {
        // given
        final var expectedCPF = "123.456.789-01";
        final var expectedEmail = "john.doe@gmail.com";
        final var expectedName = "John Doe";

        final var createInput = new CreateCustomerUseCase.Input(expectedCPF, expectedEmail, expectedName);

        // when
        final var customerRepository = new InMemoryCustomerRepository();
        final var useCase = new CreateCustomerUseCase(customerRepository);
        final var output = useCase.execute(createInput);

        // then
        Assertions.assertNotNull(output.id());
        Assertions.assertEquals(expectedCPF, output.cpf());
        Assertions.assertEquals(expectedEmail, output.email());
        Assertions.assertEquals(expectedName, output.name());
    }

    @Test
    @DisplayName("Não deve cadastrar um cliente com CPF duplicado")
    void testCreateWithDuplicatedCPFShouldFail() {
        // given
        final var expectedCPF = "123.456.789-01";
        final var expectedEmail = "john.doe@gmail.com";
        final var expectedName = "John Doe";
        final var expectedError = "Customer already exists";

        final var aCustomer = Customer.newCustomer(expectedName, expectedCPF, expectedEmail);

        final var customerRepository = new InMemoryCustomerRepository();
        customerRepository.create(aCustomer);

        final var createInput = new CreateCustomerUseCase.Input(expectedCPF, expectedEmail, expectedName);

        // when
        final var useCase = new CreateCustomerUseCase(customerRepository);
        final var actualException = Assertions.assertThrows(RuntimeException.class, () -> useCase.execute(createInput));

        // then
        Assertions.assertEquals(expectedError, actualException.getMessage());
    }

    @Test
    @DisplayName("Não deve cadastrar um cliente com e-mail duplicado")
    void testCreateWithDuplicatedEmailShouldFail() {
        // given
        final var expectedCPF = "123.456.789-01";
        final var expectedEmail = "john.doe@gmail.com";
        final var expectedName = "John Doe";
        final var expectedError = "Customer already exists";

        final var aCustomer = Customer.newCustomer(expectedName, expectedCPF, expectedEmail);

        final var customerRepository = new InMemoryCustomerRepository();
        customerRepository.create(aCustomer);

        final var createInput = new CreateCustomerUseCase.Input(expectedCPF, expectedEmail, expectedName);

        // when
        final var useCase = new CreateCustomerUseCase(customerRepository);
        final var actualException = Assertions.assertThrows(RuntimeException.class, () -> useCase.execute(createInput));

        // then
        Assertions.assertEquals(expectedError, actualException.getMessage());
    }
}
