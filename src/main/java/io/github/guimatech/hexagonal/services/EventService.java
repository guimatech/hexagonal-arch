package io.github.guimatech.hexagonal.services;

import io.github.guimatech.hexagonal.models.Event;
import io.github.guimatech.hexagonal.models.Ticket;
import io.github.guimatech.hexagonal.repositories.EventRepository;
import io.github.guimatech.hexagonal.repositories.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class EventService {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Transactional
    public Event save(Event event) {
        return eventRepository.save(event);
    }

    public Optional<Event> findById(Long id) {
        return eventRepository.findById(id);
    }
    
    public Optional<Ticket> findTicketByEventIdAndCustomerId(Long id, Long customerId) {
        return ticketRepository.findByEventIdAndCustomerId(id, customerId);
    }
}
