package io.github.guimatech.infrastructure.configurations;

import io.github.guimatech.application.partner.CreatePartnerUseCase;
import io.github.guimatech.application.partner.GetPartnerByIdUseCase;
import io.github.guimatech.infrastructure.rest.PartnerFnController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ControllerConfig {

    @Bean
    public PartnerFnController partnerFnController(
            final CreatePartnerUseCase createPartnerUseCase,
            final GetPartnerByIdUseCase getPartnerByIdUseCase
    ) {
        return new PartnerFnController(createPartnerUseCase, getPartnerByIdUseCase);
    }
}
