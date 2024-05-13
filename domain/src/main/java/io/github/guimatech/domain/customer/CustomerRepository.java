package io.github.guimatech.domain.customer;

import io.github.guimatech.domain.person.Cpf;
import io.github.guimatech.domain.person.Email;

import java.util.Optional;

public interface CustomerRepository {

    Optional<Customer> customerOfId(final CustomerId anId);

    Optional<Customer> customerOfCPF(final Cpf cpf);

    Optional<Customer> customerOfEmail(final Email email);

    Customer create(final Customer customer);

    Customer update(final Customer customer);

    void deleteAll();
}
