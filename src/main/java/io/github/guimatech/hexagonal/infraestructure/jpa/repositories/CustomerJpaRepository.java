package io.github.guimatech.hexagonal.infraestructure.jpa.repositories;

import io.github.guimatech.hexagonal.infraestructure.jpa.entity.CustomerEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface CustomerJpaRepository extends CrudRepository<CustomerEntity, UUID> {

    Optional<CustomerEntity> findByCpf(String cpf);

    Optional<CustomerEntity> findByEmail(String email);
}
