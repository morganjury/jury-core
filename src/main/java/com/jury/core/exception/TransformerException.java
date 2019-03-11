package com.jury.core.exception;

import org.jetbrains.annotations.NotNull;

public class TransformerException extends RuntimeException {

    public TransformerException() {
        super("Unknwon transformer error");
    }

    public TransformerException(@NotNull Class from, @NotNull Class to) {
        super("Error transforming " + from.getName() + " to " + to.getName());
    }

    public TransformerException(@NotNull Class from, @NotNull Class to, Throwable cause) {
        super("Error transforming " + from.getName() + " to " + to.getName());
    }

    public TransformerException(String message) {
        super(message);
    }

    public TransformerException(String message, Throwable cause) {
        super(message, cause);
    }

}
