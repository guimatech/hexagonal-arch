package io.github.guimatech.hexagonal.infraestructure.graphql;

import io.github.guimatech.hexagonal.application.usecases.customer.CreateCustomerUseCase;
import io.github.guimatech.hexagonal.application.usecases.customer.GetCustomerByIdUseCase;
import io.github.guimatech.hexagonal.infraestructure.dtos.NewCustomerDTO;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.Objects;

// Adapter [Hexagonal Architecture]
@Controller
public class CustomerResolver {

    private final CreateCustomerUseCase createCustomerUseCase;
    private final GetCustomerByIdUseCase getCustomerByIdUseCase;

    public CustomerResolver(
            final CreateCustomerUseCase createCustomerUseCase,
            final GetCustomerByIdUseCase getCustomerByIdUseCase
    ) {
        this.createCustomerUseCase = Objects.requireNonNull(createCustomerUseCase);
        this.getCustomerByIdUseCase = Objects.requireNonNull(getCustomerByIdUseCase);
    }

    @MutationMapping
    public CreateCustomerUseCase.Output createCustomer(@Argument NewCustomerDTO input) {
        return createCustomerUseCase.execute(new CreateCustomerUseCase.Input(input.cpf(), input.name(), input.email()));
    }

    @QueryMapping
    public GetCustomerByIdUseCase.Output customerOfId(@Argument String id) {
        return getCustomerByIdUseCase.execute(new GetCustomerByIdUseCase.Input(id)).orElse(null);
    }
}
