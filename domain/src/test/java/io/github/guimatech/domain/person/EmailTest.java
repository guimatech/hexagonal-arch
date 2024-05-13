package io.github.guimatech.domain.person;

import io.github.guimatech.domain.exceptions.ValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class EmailTest {

    @Test
    @DisplayName("Deve instanciar um Email valido")
    void testCreateEmail() {
        // given
        final var expectedEmail = "john@gmail.com";

        // when
        final var actualEmail = new Email(expectedEmail);

        // then
        Assertions.assertEquals(expectedEmail, actualEmail.value());
    }

    @Test
    @DisplayName("Não deve instanciar um Email invalido")
    void testCreateEmailWithInvalidValue() {
        // given
        final var expectedError = "Invalid value for Email";

        // when
        final var actualError = Assertions.assertThrows(
                ValidationException.class,
                () -> new Email("josh@s")
        );

        // then
        Assertions.assertEquals(expectedError, actualError.getMessage());
    }

    @Test
    @DisplayName("Não deve instanciar um Email null")
    void testCreateEmailWithNullValue() {
        // given
        final var expectedError = "Invalid value for Email";

        // when
        final var actualError = Assertions.assertThrows(
                ValidationException.class,
                () -> new Email(null)
        );

        // then
        Assertions.assertEquals(expectedError, actualError.getMessage());
    }
}
