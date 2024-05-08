package io.github.guimatech.hexagonal.application.repositories;

import io.github.guimatech.hexagonal.application.domain.customer.Customer;
import io.github.guimatech.hexagonal.application.domain.customer.CustomerId;
import io.github.guimatech.hexagonal.application.domain.person.Cpf;
import io.github.guimatech.hexagonal.application.domain.person.Email;

import java.util.Optional;

public interface CustomerRepository {

    Optional<Customer> customerOfId(final CustomerId anId);

    Optional<Customer> customerOfCPF(final Cpf cpf);

    Optional<Customer> customerOfEmail(final Email email);

    Customer create(final Customer customer);

    Customer update(final Customer customer);

    void deleteAll();
}
