package io.github.guimatech.hexagonal.graphql;

import io.github.guimatech.hexagonal.application.usecases.CreatePartnerUseCase;
import io.github.guimatech.hexagonal.application.usecases.GetPartnerByIdUseCase;
import io.github.guimatech.hexagonal.dtos.PartnerDTO;
import io.github.guimatech.hexagonal.services.PartnerService;
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
    public CreatePartnerUseCase.Output createPartner(@Argument PartnerDTO input) {
        final var useCase = new CreatePartnerUseCase(partnerService);
        return useCase.execute(new CreatePartnerUseCase.Input(input.getCnpj(), input.getEmail(), input.getName()));
    }

    @QueryMapping
    public GetPartnerByIdUseCase.Output partnerOfId(@Argument Long id) {
        final var useCase = new GetPartnerByIdUseCase(partnerService);
        return useCase.execute(new GetPartnerByIdUseCase.Input(id)).orElse(null);
    }
}
