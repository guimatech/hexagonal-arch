package io.github.guimatech.application.event;

import io.github.guimatech.application.UseCase;
import io.github.guimatech.domain.event.Event;
import io.github.guimatech.domain.partner.PartnerId;
import io.github.guimatech.domain.exceptions.ValidationException;
import io.github.guimatech.domain.event.EventRepository;
import io.github.guimatech.domain.partner.PartnerRepository;

import java.util.Objects;

public class CreateEventUseCase
        extends UseCase<CreateEventUseCase.Input, CreateEventUseCase.Output> {

    private final EventRepository eventRepository;
    private final PartnerRepository partnerRepository;

    public CreateEventUseCase(final EventRepository eventRepository, final PartnerRepository partnerRepository) {
        this.eventRepository = Objects.requireNonNull(eventRepository);
        this.partnerRepository = Objects.requireNonNull(partnerRepository);
    }

    public Output execute(final Input input) {
        final var aPartner = partnerRepository.partnerOfId(PartnerId.with(input.partnerId))
                .orElseThrow(() -> new ValidationException("Partner not found"));

        final var anEvent =
                eventRepository.create(Event.newEvent(input.name(), input.date(), input.totalSpots(), aPartner));

        return new Output(
                anEvent.eventId().value(),
                input.date,
                anEvent.name().value(),
                anEvent.totalSpots(),
                anEvent.partnerId().value());
    }

    public record Input(String date, String name, String partnerId, Integer totalSpots) {
    }

    public record Output(String id, String date, String name, int totalSpots, String partnerId) {
    }
}
