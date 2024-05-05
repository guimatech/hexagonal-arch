package io.github.guimatech.hexagonal.application.usecases;

import io.github.guimatech.hexagonal.application.UseCase;
import io.github.guimatech.hexagonal.application.exceptions.ValidationException;
import io.github.guimatech.hexagonal.infraestructure.models.Partner;
import io.github.guimatech.hexagonal.infraestructure.services.PartnerService;

import java.util.Objects;

public class CreatePartnerUseCase
        extends UseCase<CreatePartnerUseCase.Input, CreatePartnerUseCase.Output> {

    private final PartnerService partnerService;

    public CreatePartnerUseCase(PartnerService partnerService) {
        this.partnerService = Objects.requireNonNull(partnerService);
    }

    @Override
    public Output execute(Input input) {
        if (partnerService.findByCnpj(input.cnpj).isPresent()) {
            throw new ValidationException("Partner already exists");
        }
        if (partnerService.findByEmail(input.email).isPresent()) {
            throw new ValidationException("Partner already exists");
        }

        var partner = new Partner();
        partner.setName(input.name);
        partner.setCnpj(input.cnpj);
        partner.setEmail(input.email);

        partner = partnerService.save(partner);

        return new Output(partner.getId(), partner.getCnpj(), partner.getEmail(), partner.getName());
    }

    public record Input(String cnpj, String email, String name) {}

    public record Output(Long id, String cnpj, String email, String name) {}
}
