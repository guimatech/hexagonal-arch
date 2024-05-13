package io.github.guimatech.domain.partner;

import io.github.guimatech.domain.exceptions.ValidationException;

import java.util.Objects;
import java.util.UUID;

public record PartnerId(String value) {

    public PartnerId {
        if (Objects.isNull(value)) {
            throw new ValidationException("Invalid value for PartnerId");
        }
    }

    public static PartnerId unique() {
        return new PartnerId(UUID.randomUUID().toString());
    }

    public static PartnerId with(String value) {
        try {
            return new PartnerId(UUID.fromString(value).toString());
        } catch (IllegalArgumentException e) {
            throw new ValidationException("Invalid UUID string: " + value);
        }
    }
}
