package io.github.guimatech.hexagonal.infraestructure.dtos;

public record NewPartnerDTO(
        String name,
        String cnpj,
        String email
) {
}
