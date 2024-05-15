package io.github.guimatech.application;

public interface Presenter<IN, OUT> {

    OUT present(IN input);

    OUT present(Throwable error);
}
