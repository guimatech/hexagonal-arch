package io.github.guimatech.hexagonal.infraestructure.graphql;

import io.github.guimatech.hexagonal.application.usecases.CreatePartnerUseCase;
import io.github.guimatech.hexagonal.application.usecases.GetPartnerByIdUseCase;
import io.github.guimatech.hexagonal.infraestructure.dtos.NewPartnerDTO;
import io.github.guimatech.hexagonal.infraestructure.services.PartnerService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.Objects;

// Adapter [Hexagonal Architecture]
@Controller
public class PartnerResolver {

    private final PartnerService partnerService;

    public PartnerResolver(PartnerService partnerService) {
        this.partnerService = Objects.requireNonNull(partnerService);
    }

    @MutationMapping
    public CreatePartnerUseCase.Output createPartner(@Argument NewPartnerDTO input) {
        final var useCase = new CreatePartnerUseCase(partnerService);
        return useCase.execute(new CreatePartnerUseCase.Input(input.cnpj(), input.email(), input.name()));
    }

    @QueryMapping
    public GetPartnerByIdUseCase.Output partnerOfId(@Argument Long id) {
        final var useCase = new GetPartnerByIdUseCase(partnerService);
        return useCase.execute(new GetPartnerByIdUseCase.Input(id)).orElse(null);
    }
}
