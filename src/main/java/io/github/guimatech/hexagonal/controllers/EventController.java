package io.github.guimatech.hexagonal.controllers;

import io.github.guimatech.hexagonal.application.usecases.CreateEventUseCase;
import io.github.guimatech.hexagonal.application.usecases.SubscribeCustomerToEventUseCase;
import io.github.guimatech.hexagonal.dtos.EventDTO;
import io.github.guimatech.hexagonal.dtos.SubscribeDTO;
import io.github.guimatech.hexagonal.services.CustomerService;
import io.github.guimatech.hexagonal.services.EventService;
import io.github.guimatech.hexagonal.services.PartnerService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private CustomerService customerService;

    @Autowired
    private EventService eventService;

    @Autowired
    private PartnerService partnerService;

    @PostMapping
    @ResponseStatus(CREATED)
    public ResponseEntity<?> create(@RequestBody EventDTO dto) {
        try {
            final Long partnerId = Objects.requireNonNull(dto.getPartner(), "Partner is required").getId();
            final var useCase = new CreateEventUseCase(eventService, partnerService);
            final var output = useCase.execute(new CreateEventUseCase.Input(dto.getDate(), dto.getName(), partnerId, dto.getTotalSpots()));
            return ResponseEntity.created(URI.create("/events/" + output.id())).body(output);
        } catch (Exception ex) {
            return ResponseEntity.unprocessableEntity().body(ex.getMessage());
        }
    }

    @Transactional
    @PostMapping(value = "/{id}/subscribe")
    public ResponseEntity<?> subscribe(@PathVariable Long id, @RequestBody SubscribeDTO dto) {
        try {
            final var useCase = new SubscribeCustomerToEventUseCase(customerService, eventService);
            final var output = useCase.execute(new SubscribeCustomerToEventUseCase.Input(id, dto.getCustomerId()));
            return ResponseEntity.ok(output);
        } catch (Exception ex) {
            return ResponseEntity.unprocessableEntity().body(ex.getMessage());
        }
    }
}
