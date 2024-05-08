package io.github.guimatech.hexagonal.application.repositories;

import io.github.guimatech.hexagonal.application.domain.partner.Partner;
import io.github.guimatech.hexagonal.application.domain.partner.PartnerId;
import io.github.guimatech.hexagonal.application.domain.person.Cnpj;
import io.github.guimatech.hexagonal.application.domain.person.Email;

import java.util.Optional;

public interface PartnerRepository {

    Optional<Partner> partnerOfId(final PartnerId anId);

    Optional<Partner> partnerOfCNPJ(final Cnpj cnpj);

    Optional<Partner> partnerOfEmail(final Email email);

    Partner create(final Partner partner);

    Partner update(final Partner partner);

    void deleteAll();
}
