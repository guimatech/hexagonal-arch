package io.github.guimatech.hexagonal.application.entities;

import io.github.guimatech.hexagonal.application.exceptions.ValidationException;

public record Cnpj(String value) {

    public Cnpj {
        if (value == null || !value.matches("^\\d{2}\\.\\d{3}\\.\\d{3}\\/\\d{4}-\\d{2}$")) {
            throw new ValidationException("Invalid value for CNPJ");
        }
    }
}
