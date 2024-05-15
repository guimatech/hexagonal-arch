package io.github.guimatech.application.customer;

import io.github.guimatech.application.UseCase;
import io.github.guimatech.domain.customer.CustomerRepository;
import io.github.guimatech.domain.customer.CustomerId;

import java.util.Objects;
import java.util.Optional;

public class GetCustomerByIdUseCase
        extends UseCase<GetCustomerByIdUseCase.Input, Optional<GetCustomerByIdUseCase.Output>> {

    private final CustomerRepository customerRepository;

    public GetCustomerByIdUseCase(final CustomerRepository customerRepository) {
        this.customerRepository = Objects.requireNonNull(customerRepository);
    }

    @Override
    public Optional<Output> execute(final Input input) {
        return customerRepository.customerOfId(CustomerId.with(input.id))
                .map(customer -> new Output(
                        customer.customerId().value(),
                        customer.cpf().value(),
                        customer.email().value(),
                        customer.name().value()));
    }

    public record Input(String id) {}

    public record Output(String id, String cpf, String email, String name) {}
}