package io.github.guimatech.hexagonal.application.usecases.partner;

import io.github.guimatech.hexagonal.application.UseCase;
import io.github.guimatech.hexagonal.application.domain.partner.Partner;
import io.github.guimatech.hexagonal.application.domain.person.Cnpj;
import io.github.guimatech.hexagonal.application.domain.person.Email;
import io.github.guimatech.hexagonal.application.exceptions.ValidationException;
import io.github.guimatech.hexagonal.application.repositories.PartnerRepository;

import java.util.Objects;

public class CreatePartnerUseCase extends UseCase<CreatePartnerUseCase.Input, CreatePartnerUseCase.Output> {

    private final PartnerRepository partnerRepository;

    public CreatePartnerUseCase(final PartnerRepository partnerRepository) {
        this.partnerRepository = Objects.requireNonNull(partnerRepository);
    }

    @Override
    public Output execute(final Input input) {
        if (partnerRepository.partnerOfCNPJ(new Cnpj(input.cnpj)).isPresent()) {
            throw new ValidationException("Partner already exists");
        }

        if (partnerRepository.partnerOfEmail(new Email(input.email)).isPresent()) {
            throw new ValidationException("Partner already exists");
        }

        var partner = partnerRepository.create(Partner.newPartner(input.name, input.cnpj, input.email));

        return new Output(
                partner.partnerId().value(),
                partner.cnpj().value(),
                partner.email().value(),
                partner.name().value()
        );
    }

    public record Input(String cnpj, String email, String name) {
    }

    public record Output(String id, String cnpj, String email, String name) {
    }
}