package io.github.guimatech.hexagonal.infraestructure.dtos;

public record NewCustomerDTO(
        String name,
        String cpf,
        String email

) {
}
