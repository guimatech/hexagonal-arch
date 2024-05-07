package io.github.guimatech.hexagonal.infraestructure.configurations;

import io.github.guimatech.hexagonal.application.usecases.CreateCustomerUseCase;
import io.github.guimatech.hexagonal.application.usecases.CreateEventUseCase;
import io.github.guimatech.hexagonal.application.usecases.CreatePartnerUseCase;
import io.github.guimatech.hexagonal.application.usecases.GetCustomerByIdUseCase;
import io.github.guimatech.hexagonal.application.usecases.GetPartnerByIdUseCase;
import io.github.guimatech.hexagonal.application.usecases.SubscribeCustomerToEventUseCase;
import io.github.guimatech.hexagonal.infraestructure.services.CustomerService;
import io.github.guimatech.hexagonal.infraestructure.services.EventService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Configuration
public class UseCaseConfig {

    private final CustomerService customerService;
    private final EventService eventService;

    public UseCaseConfig(
            final CustomerService customerService,
            final EventService eventService
    ) {
        this.customerService = Objects.requireNonNull(customerService);
        this.eventService = Objects.requireNonNull(eventService);
    }

    @Bean
    public CreateCustomerUseCase createCustomerUseCase() {
        // TODO: fix dependency injection
        return new CreateCustomerUseCase(null);
    }

    @Bean
    public CreateEventUseCase createEventUseCase() {
        return new CreateEventUseCase(null, null);
    }

    @Bean
    public CreatePartnerUseCase createPartnerUseCase() {
        return new CreatePartnerUseCase(null);
    }

    @Bean
    public GetCustomerByIdUseCase getCustomerByIdUseCase() {
        return new GetCustomerByIdUseCase(null);
    }

    @Bean
    public GetPartnerByIdUseCase getPartnerByIdUseCase() {
        return new GetPartnerByIdUseCase(null);
    }

    @Bean
    public SubscribeCustomerToEventUseCase subscribeCustomerToEventUseCase() {
        return new SubscribeCustomerToEventUseCase(customerService, eventService);
    }
}