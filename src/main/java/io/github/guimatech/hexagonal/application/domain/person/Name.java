package io.github.guimatech.hexagonal.application.domain.person;

import io.github.guimatech.hexagonal.application.exceptions.ValidationException;

import java.util.Objects;

public record Name(String value) {
    public Name {
        if (Objects.isNull(value) || value.isBlank()) {
            throw new ValidationException("Invalid value for name");
        }
    }
}
