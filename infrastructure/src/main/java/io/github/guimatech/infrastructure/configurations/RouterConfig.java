package io.github.guimatech.infrastructure.configurations;

import io.github.guimatech.infrastructure.http.SpringHttpRouter;
import io.github.guimatech.infrastructure.rest.PartnerFnController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;

// Frameworks and Drivers
@Configuration
public class RouterConfig {

    @Bean
    public RouterFunction<?> routes(
            final PartnerFnController partnerFnController
    ) {
        final var router = new SpringHttpRouter();
        partnerFnController.bind(router);
        return router.router().build();
    }
}
