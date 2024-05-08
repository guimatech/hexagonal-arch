package io.github.guimatech.hexagonal.application.repository;

import io.github.guimatech.hexagonal.application.domain.customer.Customer;
import io.github.guimatech.hexagonal.application.domain.customer.CustomerId;
import io.github.guimatech.hexagonal.application.repositories.CustomerRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class InMemoryCustomerRepository implements CustomerRepository {
    private final Map<String, Customer> customers;
    private final Map<String, Customer> customersByCpf;
    private final Map<String, Customer> customersByEmail;

    public InMemoryCustomerRepository() {
        this.customers = new HashMap<>();
        this.customersByCpf = new HashMap<>();
        this.customersByEmail = new HashMap<>();
    }

    @Override
    public Optional<Customer> customerOfId(CustomerId anId) {
        return Optional.ofNullable(this.customers.get(Objects.requireNonNull(anId).value().toString()));
    }

    @Override
    public Optional<Customer> customerOfCPF(String cpf) {
        return Optional.ofNullable(this.customersByCpf.get(Objects.requireNonNull(cpf)));
    }

    @Override
    public Optional<Customer> customerOfEmail(String email) {
        return Optional.ofNullable(this.customersByEmail.get(Objects.requireNonNull(email)));
    }

    @Override
    public Customer create(Customer customer) {
        this.customers.put(customer.customerId().value().toString(), customer);
        this.customersByCpf.put(customer.cpf().value(), customer);
        this.customersByEmail.put(customer.email().value(), customer);
        return customer;
    }

    @Override
    public Customer update(Customer customer) {
        return null;
    }

    @Override
    public void deleteAll() {
        this.customers.clear();
        this.customersByCpf.clear();
        this.customersByEmail.clear();
    }
}