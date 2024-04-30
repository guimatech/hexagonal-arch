package io.github.guimatech.hexagonal.application;

public abstract class UnitUsecase<INPUT> {

    public abstract void execute(INPUT input);
}
