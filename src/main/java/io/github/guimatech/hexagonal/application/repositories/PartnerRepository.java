package io.github.guimatech.hexagonal.application.repositories;

import io.github.guimatech.hexagonal.application.entities.Partner;
import io.github.guimatech.hexagonal.application.entities.PartnerId;

import java.util.Optional;

public interface PartnerRepository {

    Optional<Partner> partnerOfId(PartnerId anId);

    Optional<Partner> partnerOfCnpj(String cpf);

    Optional<Partner> partnerOfEmail(String email);

    Partner create(Partner partner);

    Partner update(Partner partner);
}
