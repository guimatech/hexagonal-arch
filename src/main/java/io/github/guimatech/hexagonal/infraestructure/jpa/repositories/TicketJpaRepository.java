package io.github.guimatech.hexagonal.infraestructure.jpa.repositories;

import io.github.guimatech.hexagonal.infraestructure.jpa.entity.TicketEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface TicketJpaRepository extends CrudRepository<TicketEntity, UUID> {

}
