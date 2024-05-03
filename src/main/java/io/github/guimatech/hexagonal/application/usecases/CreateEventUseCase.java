package io.github.guimatech.hexagonal.application.usecases;

import io.github.guimatech.hexagonal.application.UseCase;
import io.github.guimatech.hexagonal.application.exceptions.ValidationException;
import io.github.guimatech.hexagonal.infraestructure.models.Event;
import io.github.guimatech.hexagonal.infraestructure.services.EventService;
import io.github.guimatech.hexagonal.infraestructure.services.PartnerService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Service
public class CreateEventUseCase
        extends UseCase<CreateEventUseCase.Input, CreateEventUseCase.Output> {

    private final EventService eventService;
    private final PartnerService partnerService;

    public CreateEventUseCase(final EventService eventService, final PartnerService partnerService) {
        this.eventService = Objects.requireNonNull(eventService);
        this.partnerService = Objects.requireNonNull(partnerService);
    }

    public Output execute(final Input input) {
        final var event = new Event();
        event.setDate(LocalDate.parse(input.date(), DateTimeFormatter.ISO_DATE));
        event.setName(input.name());
        event.setTotalSpots(input.totalSpots());

        partnerService.findById(input.partnerId())
                .ifPresentOrElse(event::setPartner, () -> {
                    throw new ValidationException("Partner not found");
                });

        final var createdEvent = eventService.save(event);
        return new Output(createdEvent.getId(), input.date, createdEvent.getName(), input.totalSpots, input.partnerId);
    }

    public record Input(String date, String name, Long partnerId, Integer totalSpots) {
    }

    public record Output(Long id, String date, String name, int totalSpots, Long partnerId) {
    }
}
