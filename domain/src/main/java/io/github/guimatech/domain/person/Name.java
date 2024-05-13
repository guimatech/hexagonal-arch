package io.github.guimatech.domain.person;

import io.github.guimatech.domain.exceptions.ValidationException;

import java.util.Objects;

public record Name(String value) {
    public Name {
        if (Objects.isNull(value) || value.isBlank()) {
            throw new ValidationException("Invalid value for Name");
        }
    }
}
