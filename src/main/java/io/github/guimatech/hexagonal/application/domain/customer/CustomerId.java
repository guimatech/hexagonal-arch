package io.github.guimatech.hexagonal.application.domain.customer;

import io.github.guimatech.hexagonal.application.exceptions.ValidationException;

import java.util.Objects;
import java.util.UUID;

public record CustomerId(String value) {

    public CustomerId {
        if (Objects.isNull(value)) {
            throw new ValidationException("Invalid value for CustomerId");
        }
    }

    public static CustomerId unique() {
        return new CustomerId(UUID.randomUUID().toString());
    }

    public static CustomerId with(String value) {
        try {
            return new CustomerId(UUID.fromString(value).toString());
        } catch (IllegalArgumentException e) {
            throw new ValidationException("Invalid UUID string: " + value);
        }
    }
}
