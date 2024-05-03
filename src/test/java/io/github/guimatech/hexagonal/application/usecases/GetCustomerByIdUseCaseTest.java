package io.github.guimatech.hexagonal.application.usecases;

import io.github.guimatech.hexagonal.infraestructure.models.Customer;
import io.github.guimatech.hexagonal.infraestructure.services.CustomerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.when;

class GetCustomerByIdUseCaseTest {

    @Test
    @DisplayName("Deve obter um cliente por id")
    void testGetById() {
        // given
        final var expectedID = UUID.randomUUID().getMostSignificantBits();
        final var expectedCPF = "12345678901";
        final var expectedEmail = "john.doe@gmail.com";
        final var expectedName = "John Doe";

        final var aCostumer = new Customer();
        aCostumer.setId(expectedID);
        aCostumer.setCpf(expectedCPF);
        aCostumer.setEmail(expectedEmail);
        aCostumer.setName(expectedName);

        final var input = new GetCustomerByIdUseCase.Input(expectedID);

        // when
        final var customerService = Mockito.mock(CustomerService.class);
        when(customerService.findById(expectedID)).thenReturn(Optional.of(aCostumer));

        final var useCase = new GetCustomerByIdUseCase(customerService);
        final var output = useCase.execute(input).get();

        // then
        Assertions.assertEquals(expectedID, output.id());
        Assertions.assertEquals(expectedCPF, output.cpf());
        Assertions.assertEquals(expectedEmail, output.email());
        Assertions.assertEquals(expectedName, output.name());
    }

    @Test
    @DisplayName("Deve obter vázio ao tentar recuperar um cliente não existente por id")
    void testGetByIdWithInvalidId() {
        // given
        final var expectedID = UUID.randomUUID().getMostSignificantBits();

        final var input = new GetCustomerByIdUseCase.Input(expectedID);

        // when
        final var customerService = Mockito.mock(CustomerService.class);
        when(customerService.findById(expectedID)).thenReturn(Optional.empty());

        final var useCase = new GetCustomerByIdUseCase(customerService);
        final var output = useCase.execute(input);

        // then
        Assertions.assertTrue(output.isEmpty());
    }
}