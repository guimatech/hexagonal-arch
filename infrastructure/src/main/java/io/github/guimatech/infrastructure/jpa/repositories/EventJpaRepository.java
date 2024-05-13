package io.github.guimatech.infrastructure.jpa.repositories;

import io.github.guimatech.infrastructure.jpa.entity.EventEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface EventJpaRepository extends CrudRepository<EventEntity, UUID> {

}