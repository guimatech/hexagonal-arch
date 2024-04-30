package io.github.guimatech.hexagonal.graphql;

import io.github.guimatech.hexagonal.application.usecases.CreateCustomerUseCase;
import io.github.guimatech.hexagonal.application.usecases.GetCustomerByIdUseCase;
import io.github.guimatech.hexagonal.dtos.CustomerDTO;
import io.github.guimatech.hexagonal.services.CustomerService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.Objects;

// Adapter [Hexagonal Architecture]
@Controller
public class CustomerResolver {

    private final CustomerService customerService;

    public CustomerResolver(CustomerService customerService) {
        this.customerService = Objects.requireNonNull(customerService);
    }

    @MutationMapping
    public CreateCustomerUseCase.Output createCustomer(@Argument CustomerDTO input) {
        final var useCase = new CreateCustomerUseCase(customerService);
        return useCase.execute(new CreateCustomerUseCase.Input(input.getCpf(), input.getName(), input.getEmail()));
    }

    @QueryMapping
    public GetCustomerByIdUseCase.Output customerOfId(@Argument Long id) {
        final var useCase = new GetCustomerByIdUseCase(customerService);
        return useCase.execute(new GetCustomerByIdUseCase.Input(id)).orElse(null);
    }
}
