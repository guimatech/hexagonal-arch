package io.github.guimatech.hexagonal.application.entities;

public class Customer {

    private final CustomerId customerId;
    private Name name;
    private Cpf cpf;
    private Email email;

    public Customer(final CustomerId customerId, final String name, final String cpf, final String email) {
        this.customerId = customerId;
        this.name = new Name(name);
        this.cpf = new Cpf(cpf);
        this.email = new Email(email);
    }

    public static Customer newCustomer(final String name, final String cpf, final String email) {
        return new Customer(CustomerId.unique(), name, cpf, email);
    }

    public CustomerId customerId() {
        return customerId;
    }

    public Name name() {
        return name;
    }

    public Cpf cpf() {
        return cpf;
    }

    public Email email() {
        return email;
    }
}
