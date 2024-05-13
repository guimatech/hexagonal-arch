package io.github.guimatech.application.partner;

import io.github.guimatech.application.UseCase;
import io.github.guimatech.domain.partner.PartnerRepository;
import io.github.guimatech.domain.partner.PartnerId;

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
