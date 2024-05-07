package io.github.guimatech.hexagonal.infraestructure.services;

import io.github.guimatech.hexagonal.infraestructure.models.Customer;
import io.github.guimatech.hexagonal.infraestructure.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository repository;

    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Customer save(Customer customer) {
        return repository.save(customer);
    }

    public Optional<Customer> findById(Long id) {
        return repository.findById(id);
    }

    public Optional<Customer> findByCpf(String cpf) {
        return repository.findByCpf(cpf);
    }

    public Optional<Customer> findByEmail(String email) {
        return repository.findByEmail(email);
    }

}
