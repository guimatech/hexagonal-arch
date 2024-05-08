package io.github.guimatech.hexagonal.application.domain.person;

import io.github.guimatech.hexagonal.application.exceptions.ValidationException;

import java.util.Objects;

public record Email(String value) {

    public Email {
        if (Objects.isNull(value) || !value.matches("^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$")) {
            throw new ValidationException("Invalid value for email");
        }
    }
}
