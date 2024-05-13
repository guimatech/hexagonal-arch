package io.github.guimatech.domain.partner;

import io.github.guimatech.domain.person.Cnpj;
import io.github.guimatech.domain.person.Email;

import java.util.Optional;

public interface PartnerRepository {

    Optional<Partner> partnerOfId(final PartnerId anId);

    Optional<Partner> partnerOfCNPJ(final Cnpj cnpj);

    Optional<Partner> partnerOfEmail(final Email email);

    Partner create(final Partner partner);

    Partner update(final Partner partner);

    void deleteAll();
}
