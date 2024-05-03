package io.github.guimatech.hexagonal.application.usecases;

import io.github.guimatech.hexagonal.infraestructure.models.Customer;
import io.github.guimatech.hexagonal.infraestructure.services.CustomerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class CreateCustomerUseCaseTest {

    @Test
    @DisplayName("Deve criar um cliente")
    void testCreate() throws Exception {
        // given
        final var expectedCPF = "12345678901";
        final var expectedEmail = "john.doe@gmail.com";
        final var expectedName = "John Doe";

        final var createInput = new CreateCustomerUseCase.Input(expectedCPF, expectedEmail, expectedName);

        // when
        final var customerService = Mockito.mock(CustomerService.class);
        when(customerService.findByCpf(expectedCPF)).thenReturn(java.util.Optional.empty());
        when(customerService.findByEmail(expectedCPF)).thenReturn(java.util.Optional.empty());
        when(customerService.save(any())).thenAnswer(invocation -> {
            var customer = invocation.getArgument(0, Customer.class);
            customer.setId(UUID.randomUUID().getLeastSignificantBits());
            return customer;
        });

        final var useCase = new CreateCustomerUseCase(customerService);
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
        final var expectedCPF = "12345678901";
        final var expectedEmail = "john.doe@gmail.com";
        final var expectedName = "John Doe";
        final var expectedError = "Customer already exists";

        final var createInput = new CreateCustomerUseCase.Input(expectedCPF, expectedEmail, expectedName);

        final var aCustomer = new Customer();
        aCustomer.setId(UUID.randomUUID().getMostSignificantBits());
        aCustomer.setCpf(expectedCPF);
        aCustomer.setName(expectedName);
        aCustomer.setEmail(expectedEmail);
        // when
        final var customerService = Mockito.mock(CustomerService.class);
        when(customerService.findByCpf(expectedCPF)).thenReturn(java.util.Optional.of(aCustomer));

        final var useCase = new CreateCustomerUseCase(customerService);
        final var actualException = Assertions.assertThrows(RuntimeException.class, () -> useCase.execute(createInput));

        // then
        Assertions.assertEquals(expectedError, actualException.getMessage());
    }

    @Test
    @DisplayName("Não deve cadastrar um cliente com e-mail duplicado")
    void testCreateWithDuplicatedEmailShouldFail() {
        // given
        final var expectedCPF = "12345678901";
        final var expectedEmail = "john.doe@gmail.com";
        final var expectedName = "John Doe";
        final var expectedError = "Customer already exists";

        final var createInput = new CreateCustomerUseCase.Input(expectedCPF, expectedEmail, expectedName);

        final var aCustomer = new Customer();
        aCustomer.setId(UUID.randomUUID().getMostSignificantBits());
        aCustomer.setCpf(expectedCPF);
        aCustomer.setName(expectedName);
        aCustomer.setEmail(expectedEmail);
        // when
        final var customerService = Mockito.mock(CustomerService.class);
        when(customerService.findByEmail(expectedEmail)).thenReturn(java.util.Optional.of(aCustomer));

        final var useCase = new CreateCustomerUseCase(customerService);
        final var actualException = Assertions.assertThrows(RuntimeException.class, () -> useCase.execute(createInput));

        // then
        Assertions.assertEquals(expectedError, actualException.getMessage());
    }
}
