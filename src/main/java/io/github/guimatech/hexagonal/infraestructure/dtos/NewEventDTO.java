package io.github.guimatech.hexagonal.infraestructure.dtos;

public record NewEventDTO(
        String name,
        String date,
        int totalSpots,
        String partnerId
) {
}
