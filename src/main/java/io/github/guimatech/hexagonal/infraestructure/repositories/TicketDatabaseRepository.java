package io.github.guimatech.hexagonal.infraestructure.repositories;

import io.github.guimatech.hexagonal.application.domain.event.ticket.Ticket;
import io.github.guimatech.hexagonal.application.domain.event.ticket.TicketId;
import io.github.guimatech.hexagonal.application.repositories.TicketRepository;
import io.github.guimatech.hexagonal.infraestructure.jpa.entity.TicketEntity;
import io.github.guimatech.hexagonal.infraestructure.jpa.repositories.TicketJpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

// Interface Adapter
@Component
public class TicketDatabaseRepository implements TicketRepository {

    private final TicketJpaRepository ticketJpaRepository;

    public TicketDatabaseRepository(final TicketJpaRepository ticketJpaRepository) {
        this.ticketJpaRepository = Objects.requireNonNull(ticketJpaRepository);
    }

    @Override
    public Optional<Ticket> ticketOfId(final TicketId anId) {
        Objects.requireNonNull(anId, "id cannot be null");
        return this.ticketJpaRepository.findById(UUID.fromString(anId.value()))
                .map(TicketEntity::toTicket);
    }

    @Override
    @Transactional
    public Ticket create(final Ticket ticket) {
        return this.ticketJpaRepository.save(TicketEntity.of(ticket))
                .toTicket();
    }

    @Override
    @Transactional
    public Ticket update(Ticket ticket) {
        return this.ticketJpaRepository.save(TicketEntity.of(ticket))
                .toTicket();
    }

    @Override
    public void deleteAll() {
        this.ticketJpaRepository.deleteAll();
    }
}