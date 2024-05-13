package io.github.guimatech.infrastructure.dtos;

public record NewPartnerDTO(
        String name,
        String cnpj,
        String email
) {
}
