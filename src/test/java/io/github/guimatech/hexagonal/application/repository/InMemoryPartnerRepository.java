package io.github.guimatech.hexagonal.application.repository;

import io.github.guimatech.hexagonal.application.domain.partner.Partner;
import io.github.guimatech.hexagonal.application.domain.partner.PartnerId;
import io.github.guimatech.hexagonal.application.repositories.PartnerRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class InMemoryPartnerRepository implements PartnerRepository {
    private final Map<String, Partner> partners;
    private final Map<String, Partner> partnersByCnpj;
    private final Map<String, Partner> partnersByEmail;

    public InMemoryPartnerRepository() {
        this.partners = new HashMap<>();
        this.partnersByCnpj = new HashMap<>();
        this.partnersByEmail = new HashMap<>();
    }

    @Override
    public Optional<Partner> partnerOfId(PartnerId anId) {
        return Optional.ofNullable(this.partners.get(Objects.requireNonNull(anId).value().toString()));
    }

    @Override
    public Optional<Partner> partnerOfCNPJ(String cnpj) {
        return Optional.ofNullable(this.partnersByCnpj.get(Objects.requireNonNull(cnpj)));
    }

    @Override
    public Optional<Partner> partnerOfEmail(String email) {
        return Optional.ofNullable(this.partnersByEmail.get(Objects.requireNonNull(email)));
    }

    @Override
    public Partner create(Partner partner) {
        this.partners.put(partner.partnerId().value().toString(), partner);
        this.partnersByCnpj.put(partner.cnpj().value(), partner);
        this.partnersByEmail.put(partner.email().value(), partner);
        return partner;
    }

    @Override
    public Partner update(Partner partner) {
        return null;
    }

    @Override
    public void deleteAll() {
        this.partners.clear();
        this.partnersByCnpj.clear();
        this.partnersByEmail.clear();
    }
}