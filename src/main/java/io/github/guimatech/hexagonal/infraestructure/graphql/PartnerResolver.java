package io.github.guimatech.hexagonal.infraestructure.graphql;

import io.github.guimatech.hexagonal.application.repositories.PartnerRepository;
import io.github.guimatech.hexagonal.application.usecases.partner.CreatePartnerUseCase;
import io.github.guimatech.hexagonal.application.usecases.partner.GetPartnerByIdUseCase;
import io.github.guimatech.hexagonal.infraestructure.dtos.NewPartnerDTO;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.Objects;

// Adapter [Hexagonal Architecture]
@Controller
public class PartnerResolver {

    private final PartnerRepository partnerRepository;

    public PartnerResolver(PartnerRepository partnerRepository) {
        this.partnerRepository = Objects.requireNonNull(partnerRepository);
    }

    @MutationMapping
    public CreatePartnerUseCase.Output createPartner(@Argument NewPartnerDTO input) {
        final var useCase = new CreatePartnerUseCase(partnerRepository);
        return useCase.execute(new CreatePartnerUseCase.Input(input.cnpj(), input.email(), input.name()));
    }

    @QueryMapping
    public GetPartnerByIdUseCase.Output partnerOfId(@Argument String id) {
        final var useCase = new GetPartnerByIdUseCase(partnerRepository);
        return useCase.execute(new GetPartnerByIdUseCase.Input(id)).orElse(null);
    }
}
