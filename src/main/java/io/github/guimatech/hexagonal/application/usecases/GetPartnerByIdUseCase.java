package io.github.guimatech.hexagonal.application.usecases;

import io.github.guimatech.hexagonal.application.UseCase;
import io.github.guimatech.hexagonal.infraestructure.services.PartnerService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GetPartnerByIdUseCase
        extends UseCase<GetPartnerByIdUseCase.Input, Optional<GetPartnerByIdUseCase.Output>> {

    private final PartnerService partnerService;

    public GetPartnerByIdUseCase(PartnerService partnerService) {
        this.partnerService = partnerService;
    }

    @Override
    public Optional<Output> execute(Input input) {
        return partnerService.findById(input.id)
                .map(partner -> new Output(partner.getId(), partner.getCnpj(), partner.getEmail(), partner.getName()));
    }

    public record Input(Long id) {}

    public record Output(Long id, String cnpj, String email, String name) {}
}
