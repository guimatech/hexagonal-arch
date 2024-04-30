package io.github.guimatech.hexagonal.application.usecases;

import io.github.guimatech.hexagonal.application.UseCase;
import io.github.guimatech.hexagonal.application.exceptions.ValidationException;
import io.github.guimatech.hexagonal.models.Customer;
import io.github.guimatech.hexagonal.services.CustomerService;

import java.util.Objects;

public class CreateCustomerUseCase
        extends UseCase<CreateCustomerUseCase.Input, CreateCustomerUseCase.Output> {

    private final CustomerService customerService;

    public CreateCustomerUseCase(CustomerService customerService) {
        this.customerService = Objects.requireNonNull(customerService);
    }

    @Override
    public Output execute(Input input) {
        if (customerService.findByCpf(input.cpf).isPresent()) {
            throw new ValidationException("Customer already exists");
        }
        if (customerService.findByEmail(input.email).isPresent()) {
            throw new ValidationException("Customer already exists");
        }

        var customer = new Customer();
        customer.setName(input.name);
        customer.setCpf(input.cpf);
        customer.setEmail(input.email);

        customer = customerService.save(customer);

        return new Output(customer.getId(), customer.getCpf(), customer.getEmail(), customer.getName());
    }

    public record Input(String cpf, String email, String name) {}

    public record Output(Long id, String cpf, String email, String name) {}
}
