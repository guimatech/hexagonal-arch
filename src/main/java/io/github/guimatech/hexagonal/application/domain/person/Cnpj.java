package io.github.guimatech.hexagonal.application.domain.person;

import io.github.guimatech.hexagonal.application.exceptions.ValidationException;

import java.util.Objects;

public record Cnpj(String value) {

    public Cnpj {
        if (Objects.isNull(value) || !value.matches("^\\d{2}\\.\\d{3}\\.\\d{3}\\/\\d{4}-\\d{2}$")) {
            throw new ValidationException("Invalid value for CNPJ");
        }
    }
}
