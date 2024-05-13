package io.github.guimatech.infrastructure.jpa.repositories;

import io.github.guimatech.infrastructure.jpa.entity.TicketEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface TicketJpaRepository extends CrudRepository<TicketEntity, UUID> {

}
