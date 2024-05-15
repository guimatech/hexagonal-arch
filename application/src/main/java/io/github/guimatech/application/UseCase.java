package io.github.guimatech.application;

public abstract class UseCase<INPUT, OUTPUT> {

    // Acordos:
    // 1. Cada caso de uso tem um Input e um Output próprio. Não retorna a entidade, o agregado, ou objeto de valor.
    // 2. O caso de uso implementa o padrão Command

    public abstract OUTPUT execute(final INPUT input);

    public <T> T execute(final INPUT input, final Presenter<OUTPUT, T> presenter) {
        try {
            return presenter.present(execute(input));
        } catch (Throwable error) {
            return presenter.present(error);
        }
    }
}