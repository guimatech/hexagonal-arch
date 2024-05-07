package io.github.guimatech.hexagonal.application.entities;

import io.github.guimatech.hexagonal.application.exceptions.ValidationException;

public record Name(String value) {
    public Name {
        if (value == null || value.isBlank()) {
            throw new ValidationException("Invalid value for name");
        }
    }
}
