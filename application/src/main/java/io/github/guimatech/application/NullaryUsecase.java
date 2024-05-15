package io.github.guimatech.application;

public abstract class NullaryUsecase<OUTPUT> {

    public abstract OUTPUT execute();

    public <T> T execute(final Presenter<OUTPUT, T> presenter) {
        try {
            return presenter.present(execute());
        } catch (Throwable error) {
            return presenter.present(error);
        }
    }
}
