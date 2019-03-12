package com.jury.core.exception;

import org.jetbrains.annotations.NotNull;

public class TransformerException extends RuntimeException {

    public TransformerException() {
        super("Unknwon transformer error");
    }

    public TransformerException(@NotNull Class from, @NotNull Class to) {
        this("Error transforming " + from.getName() + " to " + to.getName());
    }

    public TransformerException(@NotNull Class from, @NotNull Class to, Throwable cause) {
        this("Error transforming " + from.getName() + " to " + to.getName(), cause);
    }

    private TransformerException(String message) {
        super(message);
    }

    private TransformerException(String message, Throwable cause) {
        super(message, cause);
    }

}
