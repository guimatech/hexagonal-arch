package io.github.guimatech.hexagonal.infraestructure.repositories;

import io.github.guimatech.hexagonal.infraestructure.models.Ticket;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TicketRepository extends CrudRepository<Ticket, Long> {

    Optional<Ticket> findByEventIdAndCustomerId(Long id, Long customerId);
}
