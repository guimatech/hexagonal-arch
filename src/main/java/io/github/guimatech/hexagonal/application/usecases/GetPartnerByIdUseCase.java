package io.github.guimatech.hexagonal.application.usecases;

import io.github.guimatech.hexagonal.application.UseCase;
import io.github.guimatech.hexagonal.application.entities.PartnerId;
import io.github.guimatech.hexagonal.application.repositories.PartnerRepository;

import java.util.Optional;

public class GetPartnerByIdUseCase
        extends UseCase<GetPartnerByIdUseCase.Input, Optional<GetPartnerByIdUseCase.Output>> {

    private final PartnerRepository partnerRepository;

    public GetPartnerByIdUseCase(final PartnerRepository partnerRepository) {
        this.partnerRepository = partnerRepository;
    }

    @Override
    public Optional<Output> execute(final Input input) {
        return partnerRepository.partnerOfId(PartnerId.with(input.id))
                .map(partner -> new Output(partner.partnerId().value(),
                        partner.cnpj().value(),
                        partner.email().value(),
                        partner.name().value()));
    }

    public record Input(String id) {}

    public record Output(String id, String cnpj, String email, String name) {}
}
