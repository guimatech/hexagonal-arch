package io.github.guimatech.hexagonal.infraestructure.dtos;

public record SubscribeDTO(
        Long customerId,
        Long eventId
) {
}
