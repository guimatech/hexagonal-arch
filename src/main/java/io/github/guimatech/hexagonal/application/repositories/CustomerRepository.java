package io.github.guimatech.hexagonal.application.repositories;

import io.github.guimatech.hexagonal.application.entities.Customer;
import io.github.guimatech.hexagonal.application.entities.CustomerId;

import java.util.Optional;

public interface CustomerRepository {

    Optional<Customer> customerOfId(CustomerId anId);

    Optional<Customer> customerOfCpf(String cpf);

    Optional<Customer> customerOfEmail(String email);

    Customer create(Customer customer);

    Customer update(Customer customer);
}
