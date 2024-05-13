package io.github.guimatech.domain.person;

import io.github.guimatech.domain.exceptions.ValidationException;

import java.util.Objects;

public record Cpf(String value) {

    public Cpf {
        if (Objects.isNull(value) || !value.matches("^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$")) {
            throw new ValidationException("Invalid value for Cpf");
        }
    }
}
