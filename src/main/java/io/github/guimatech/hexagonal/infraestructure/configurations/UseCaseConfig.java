package io.github.guimatech.hexagonal.infraestructure.configurations;

import io.github.guimatech.hexagonal.application.repositories.CustomerRepository;
import io.github.guimatech.hexagonal.application.repositories.EventRepository;
import io.github.guimatech.hexagonal.application.repositories.PartnerRepository;
import io.github.guimatech.hexagonal.application.repositories.TicketRepository;
import io.github.guimatech.hexagonal.application.usecases.customer.CreateCustomerUseCase;
import io.github.guimatech.hexagonal.application.usecases.event.CreateEventUseCase;
import io.github.guimatech.hexagonal.application.usecases.partner.CreatePartnerUseCase;
import io.github.guimatech.hexagonal.application.usecases.customer.GetCustomerByIdUseCase;
import io.github.guimatech.hexagonal.application.usecases.partner.GetPartnerByIdUseCase;
import io.github.guimatech.hexagonal.application.usecases.event.SubscribeCustomerToEventUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Configuration
public class UseCaseConfig {

    private final CustomerRepository customerRepository;
    private final EventRepository eventRepository;
    private final PartnerRepository partnerRepository;
    private final TicketRepository ticketRepository;

    public UseCaseConfig(
            final CustomerRepository customerRepository,
            final EventRepository eventRepository,
            final PartnerRepository partnerRepository,
            final TicketRepository ticketRepository
    ) {
        this.customerRepository = Objects.requireNonNull(customerRepository);
        this.eventRepository = Objects.requireNonNull(eventRepository);
        this.partnerRepository = Objects.requireNonNull(partnerRepository);
        this.ticketRepository = Objects.requireNonNull(ticketRepository);
    }

    @Bean
    public CreateCustomerUseCase createCustomerUseCase() {
        return new CreateCustomerUseCase(customerRepository);
    }

    @Bean
    public CreateEventUseCase createEventUseCase() {
        return new CreateEventUseCase(eventRepository, partnerRepository);
    }

    @Bean
    public CreatePartnerUseCase createPartnerUseCase() {
        return new CreatePartnerUseCase(partnerRepository);
    }

    @Bean
    public GetCustomerByIdUseCase getCustomerByIdUseCase() {
        return new GetCustomerByIdUseCase(customerRepository);
    }

    @Bean
    public GetPartnerByIdUseCase getPartnerByIdUseCase() {
        return new GetPartnerByIdUseCase(partnerRepository);
    }

    @Bean
    public SubscribeCustomerToEventUseCase subscribeCustomerToEventUseCase() {
        return new SubscribeCustomerToEventUseCase(customerRepository, eventRepository, ticketRepository);
    }
}