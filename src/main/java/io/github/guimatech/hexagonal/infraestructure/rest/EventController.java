package io.github.guimatech.hexagonal.infraestructure.rest;

import io.github.guimatech.hexagonal.application.usecases.CreateEventUseCase;
import io.github.guimatech.hexagonal.application.usecases.SubscribeCustomerToEventUseCase;
import io.github.guimatech.hexagonal.infraestructure.dtos.NewEventDTO;
import io.github.guimatech.hexagonal.infraestructure.dtos.SubscribeDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Objects;

import static org.springframework.http.HttpStatus.CREATED;

// Adapter
@RestController
@RequestMapping(value = "events")
public class EventController {

    private final CreateEventUseCase createEventUseCase;
    private final SubscribeCustomerToEventUseCase subscribeCustomerToEventUseCase;

    public EventController(
            final CreateEventUseCase createEventUseCase,
            final SubscribeCustomerToEventUseCase subscribeCustomerToEventUseCase
    ) {
        this.createEventUseCase = Objects.requireNonNull(createEventUseCase);
        this.subscribeCustomerToEventUseCase = Objects.requireNonNull(subscribeCustomerToEventUseCase);
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public ResponseEntity<?> create(@RequestBody NewEventDTO dto) {
        try {
            final var output = createEventUseCase.execute(
                    new CreateEventUseCase.Input(dto.date(), dto.name(), dto.partnerId(), dto.totalSpots()));

            return ResponseEntity.created(URI.create("/events/" + output.id())).body(output);
        } catch (Exception ex) {
            return ResponseEntity.unprocessableEntity().body(ex.getMessage());
        }
    }

    @Transactional
    @PostMapping(value = "/{id}/subscribe")
    public ResponseEntity<?> subscribe(@PathVariable Long id, @RequestBody SubscribeDTO dto) {
        try {
            final var output = subscribeCustomerToEventUseCase
                    .execute(new SubscribeCustomerToEventUseCase.Input(id, dto.customerId()));

            return ResponseEntity.ok(output);
        } catch (Exception ex) {
            return ResponseEntity.unprocessableEntity().body(ex.getMessage());
        }
    }
}
