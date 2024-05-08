package io.github.guimatech.hexagonal.infraestructure.repositories;

import io.github.guimatech.hexagonal.application.domain.partner.Partner;
import io.github.guimatech.hexagonal.application.domain.partner.PartnerId;
import io.github.guimatech.hexagonal.application.domain.person.Cnpj;
import io.github.guimatech.hexagonal.application.domain.person.Email;
import io.github.guimatech.hexagonal.application.repositories.PartnerRepository;
import io.github.guimatech.hexagonal.infraestructure.jpa.entity.PartnerEntity;
import io.github.guimatech.hexagonal.infraestructure.jpa.repositories.PartnerJpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

// Interface Adapter
@Component
public class PartnerDatabaseRepository implements PartnerRepository {

    private final PartnerJpaRepository partnerJpaRepository;

    public PartnerDatabaseRepository(final PartnerJpaRepository partnerJpaRepository) {
        this.partnerJpaRepository = Objects.requireNonNull(partnerJpaRepository);
    }

    @Override
    public Optional<Partner> partnerOfId(final PartnerId anId) {
        Objects.requireNonNull(anId, "id cannot be null");
        return this.partnerJpaRepository.findById(UUID.fromString(anId.value()))
                .map(PartnerEntity::toPartner);
    }

    @Override
    public Optional<Partner> partnerOfCNPJ(final Cnpj cnpj) {
        Objects.requireNonNull(cnpj, "Cnpj cannot be null");
        return this.partnerJpaRepository.findByCnpj(cnpj.value())
                .map(PartnerEntity::toPartner);
    }

    @Override
    public Optional<Partner> partnerOfEmail(final Email email) {
        Objects.requireNonNull(email, "Email cannot be null");
        return this.partnerJpaRepository.findByEmail(email.value())
                .map(PartnerEntity::toPartner);
    }

    @Override
    @Transactional
    public Partner create(final Partner partner) {
        return this.partnerJpaRepository.save(PartnerEntity.of(partner))
                .toPartner();
    }

    @Override
    @Transactional
    public Partner update(Partner partner) {
        return this.partnerJpaRepository.save(PartnerEntity.of(partner))
                .toPartner();
    }

    @Override
    public void deleteAll() {
        this.partnerJpaRepository.deleteAll();
    }
}