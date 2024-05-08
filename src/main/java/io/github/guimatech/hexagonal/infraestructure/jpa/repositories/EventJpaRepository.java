package io.github.guimatech.hexagonal.infraestructure.jpa.repositories;

import io.github.guimatech.hexagonal.infraestructure.jpa.entity.EventEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface EventJpaRepository extends CrudRepository<EventEntity, UUID> {

}