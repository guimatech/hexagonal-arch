package io.github.guimatech.hexagonal.controllers;

import io.github.guimatech.hexagonal.application.usecases.CreateCustomerUseCase;
import io.github.guimatech.hexagonal.dtos.CustomerDTO;
import io.github.guimatech.hexagonal.models.Customer;
import io.github.guimatech.hexagonal.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

// Adapter [Hexagonal Architecture]
@RestController
@RequestMapping(value = "customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CustomerDTO dto) {
        try {
            final var useCase = new CreateCustomerUseCase(customerService);
            final var output = useCase.execute(new CreateCustomerUseCase.Input(dto.getCpf(), dto.getEmail(), dto.getName()));
            return ResponseEntity.created(URI.create("/customers/" + output.id())).body(output);
        } catch (Exception ex) {
            return ResponseEntity.unprocessableEntity().body(ex.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        var customer = customerService.findById(id);
        if (customer.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(customer.get());
    }
}