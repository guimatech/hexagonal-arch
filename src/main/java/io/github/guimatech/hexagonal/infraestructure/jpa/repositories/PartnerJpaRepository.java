package io.github.guimatech.hexagonal.infraestructure.jpa.repositories;

import io.github.guimatech.hexagonal.infraestructure.jpa.entity.PartnerEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface PartnerJpaRepository extends CrudRepository<PartnerEntity, UUID> {

    Optional<PartnerEntity> findByCnpj(String cnpj);

    Optional<PartnerEntity> findByEmail(String email);
}