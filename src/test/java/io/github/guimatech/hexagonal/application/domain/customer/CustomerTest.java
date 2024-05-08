package io.github.guimatech.hexagonal.application.domain.customer;

import io.github.guimatech.hexagonal.application.exceptions.ValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CustomerTest {

    @Test
    @DisplayName("Deve instanciar um cliente")
    void testCreateCustomer() {
        // given
        final var expectedCPF = "123.456.789-01";
        final var expectedEmail = "john.doe@gmail.com";
        final var expectedName = "John Doe";

        // when
        final var actualCustomer = Customer.newCustomer(expectedName, expectedCPF, expectedEmail);

        // then
        Assertions.assertNotNull(actualCustomer.customerId());
        Assertions.assertEquals(expectedCPF, actualCustomer.cpf().value());
        Assertions.assertEquals(expectedEmail, actualCustomer.email().value());
        Assertions.assertEquals(expectedName, actualCustomer.name().value());
    }

    @Test
    @DisplayName("Não deve instanciar um cliente com CPF invalido")
    void testCreateCustomerWithInvalidCPF() {
        // given
        final var expectedError = "Invalid value for Cpf";

        // when
        final var actualError = Assertions.assertThrows(
                ValidationException.class,
                () -> Customer.newCustomer("John Doe", "123456.789-01", "john.doe@gmail.com")
        );

        // then
        Assertions.assertEquals(expectedError, actualError.getMessage());
    }

    @Test
    @DisplayName("Não deve instanciar um cliente com nome invalido")
    void testCreateCustomerWithInvalidName() {
        // given
        final var expectedError = "Invalid value for Name";

        // when
        final var actualError = Assertions.assertThrows(
                ValidationException.class,
                () -> Customer.newCustomer(null, "123.456.789-01", "john.doe@gmail.com")
        );

        // then
        Assertions.assertEquals(expectedError, actualError.getMessage());
    }


    @Test
    @DisplayName("Não deve instanciar um cliente com email invalido")
    void testCreateCustomerWithInvalidEmail() {
        // given
        final var expectedError = "Invalid value for Email";

        // when
        final var actualError = Assertions.assertThrows(
                ValidationException.class,
                () -> Customer.newCustomer("John Doe", "123.456.789-01", "john.doe@gmail")
        );

        // then
        Assertions.assertEquals(expectedError, actualError.getMessage());
    }
}
