package io.github.guimatech.hexagonal.infraestructure.repositories;

import io.github.guimatech.hexagonal.infraestructure.models.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

    Optional<Customer> findByCpf(String cpf);

    Optional<Customer> findByEmail(String email);
}
