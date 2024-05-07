package io.github.guimatech.hexagonal.application.entities;

import io.github.guimatech.hexagonal.application.exceptions.ValidationException;

import java.util.UUID;

public record PartnerId(UUID value) {

    public PartnerId {
        if (value == null) {
            throw new ValidationException("Invalid value for PartnerId");
        }
    }

    public static PartnerId unique() {
        return new PartnerId(UUID.randomUUID());
    }

    public static PartnerId with(String value) {
        try {
            return new PartnerId(UUID.fromString(value));
        } catch (IllegalArgumentException e) {
            throw new ValidationException("Invalid UUID string: " + value);
        }
    }
}
