package io.github.guimatech.hexagonal.infraestructure.dtos;

public record SubscribeDTO(
        String customerId,
        String eventId
) {
}
