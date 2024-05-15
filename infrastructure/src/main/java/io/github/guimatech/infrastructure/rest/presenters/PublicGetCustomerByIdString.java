package io.github.guimatech.infrastructure.rest.presenters;

import io.github.guimatech.application.Presenter;
import io.github.guimatech.application.customer.GetCustomerByIdUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("publicGetCustomer")
public class PublicGetCustomerByIdString implements Presenter<Optional<GetCustomerByIdUseCase.Output>, Object> {

    private static final Logger LOG = LoggerFactory.getLogger(PublicGetCustomerByIdString.class);

    @Override
    public Object present(Optional<GetCustomerByIdUseCase.Output> output) {
        return output.map(GetCustomerByIdUseCase.Output::id).orElseGet(() -> "Customer not found.");
    }

    @Override
    public Object present(Throwable error) {
        LOG.error("An error was observer at GetCustomerByIdUseCase.", error);
        return "Customer not found.";
    }
}
