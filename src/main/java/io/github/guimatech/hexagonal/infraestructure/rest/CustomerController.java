package io.github.guimatech.hexagonal.infraestructure.rest;

import io.github.guimatech.hexagonal.application.usecases.CreateCustomerUseCase;
import io.github.guimatech.hexagonal.application.usecases.GetCustomerByIdUseCase;
import io.github.guimatech.hexagonal.infraestructure.dtos.NewCustomerDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Objects;

// Adapter [Hexagonal Architecture]
@RestController
@RequestMapping(value = "customers")
public class CustomerController {

    private final CreateCustomerUseCase createCustomerUseCase;
    private final GetCustomerByIdUseCase getCustomerByIdUseCase;

    public CustomerController(
            final CreateCustomerUseCase createCustomerUseCase,
            final GetCustomerByIdUseCase getCustomerByIdUseCase
    ) {
        this.createCustomerUseCase = Objects.requireNonNull(createCustomerUseCase);
        this.getCustomerByIdUseCase = Objects.requireNonNull(getCustomerByIdUseCase);
    }


    @PostMapping
    public ResponseEntity<?> create(@RequestBody NewCustomerDTO dto) {
        try {
            final var output = createCustomerUseCase.execute(new CreateCustomerUseCase.Input(dto.cpf(), dto.email(), dto.name()));
            return ResponseEntity.created(URI.create("/customers/" + output.id())).body(output);
        } catch (Exception ex) {
            return ResponseEntity.unprocessableEntity().body(ex.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        return getCustomerByIdUseCase.execute(new GetCustomerByIdUseCase.Input(id))
                .map(ResponseEntity::ok)
                .orElseGet(ResponseEntity.notFound()::build);
    }
}