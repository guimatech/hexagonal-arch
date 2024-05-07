package io.github.guimatech.hexagonal.application.entities;

public class Partner {

    private final PartnerId partnerId;
    private final Name name;
    private final Cnpj cnpj;
    private final Email email;

    public Partner(final PartnerId partnerId, final String name, final String cnpj, final String email) {
        this.partnerId = partnerId;
        this.name = new Name(name);
        this.cnpj = new Cnpj(cnpj);
        this.email = new Email(email);
    }

    public static Partner newPartner(final String name, final String cnpj, final String email) {
        return new Partner(PartnerId.unique(), name, cnpj, email);
    }

    public PartnerId partnerId() {
        return partnerId;
    }

    public Name name() {
        return name;
    }

    public Cnpj cnpj() {
        return cnpj;
    }

    public Email email() {
        return email;
    }
}
