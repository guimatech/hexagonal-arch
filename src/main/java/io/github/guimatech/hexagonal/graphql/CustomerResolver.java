package io.github.guimatech.hexagonal.graphql;

import io.github.guimatech.hexagonal.application.usecases.CreateCustomerUseCase;
import io.github.guimatech.hexagonal.dtos.CustomerDTO;
import io.github.guimatech.hexagonal.services.CustomerService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

// Adapter [Hexagonal Architecture]
@Controller
public class CustomerResolver {

    private final CustomerService customerService;

    public CustomerResolver(CustomerService customerService) {
        this.customerService = customerService;
    }

    @MutationMapping
    public CreateCustomerUseCase.Output createCustomer(@Argument CustomerDTO input) {
        final var useCase = new CreateCustomerUseCase(customerService);
        return useCase.execute(new CreateCustomerUseCase.Input(input.getCpf(), input.getName(), input.getEmail()));
    }

    @QueryMapping
    public CustomerDTO customerOfId(@Argument Long id) {
        return customerService.findById(id)
                .map(CustomerDTO::new)
                .orElse(null);
    }
}
