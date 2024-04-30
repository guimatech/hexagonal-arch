package io.github.guimatech.hexagonal.application;

public abstract class UseCase<INPUT, OUTPUT> {

    // Acordos:
    // 1. Cada caso de uso tem um Input e um Output próprio. Não retorna a entidade, o agregado, ou objeto de valor.
    // 2. O caso de uso implementa o padrão Command

    public abstract OUTPUT execute(INPUT input);
}
