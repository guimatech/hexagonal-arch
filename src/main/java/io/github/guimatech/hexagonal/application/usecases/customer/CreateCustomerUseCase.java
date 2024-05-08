package io.github.guimatech.hexagonal.application.usecases.customer;

import io.github.guimatech.hexagonal.application.UseCase;
import io.github.guimatech.hexagonal.application.domain.customer.Customer;
import io.github.guimatech.hexagonal.application.domain.person.Cpf;
import io.github.guimatech.hexagonal.application.domain.person.Email;
import io.github.guimatech.hexagonal.application.exceptions.ValidationException;
import io.github.guimatech.hexagonal.application.repositories.CustomerRepository;

public class CreateCustomerUseCase
        extends UseCase<CreateCustomerUseCase.Input, CreateCustomerUseCase.Output> {

    private final CustomerRepository customerRepository;

    public CreateCustomerUseCase(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Output execute(final Input input) {
        if (customerRepository.customerOfCPF(new Cpf(input.cpf)).isPresent()) {
            throw new ValidationException("Customer already exists");
        }

        if (customerRepository.customerOfEmail(new Email(input.email)).isPresent()) {
            throw new ValidationException("Customer already exists");
        }

        var customer = customerRepository.create(Customer.newCustomer(input.name, input.cpf, input.email));

        return new Output(
                customer.customerId().value(),
                customer.cpf().value(),
                customer.email().value(),
                customer.name().value()
        );
    }

    public record Input(String cpf, String email, String name) {
    }

    public record Output(String id, String cpf, String email, String name) {
    }
}