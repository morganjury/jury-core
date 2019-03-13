package com.jury.core.exception;

public class TransformerException extends RuntimeException {

    public TransformerException() {
        super("Unknwon transformer error");
    }

    public TransformerException(Class from, Class to) {
        this("Error transforming " + from.getName() + " to " + to.getName());
    }

    public TransformerException(Class from, Class to, Throwable cause) {
        this("Error transforming " + from.getName() + " to " + to.getName(), cause);
    }

    private TransformerException(String message) {
        super(message);
    }

    private TransformerException(String message, Throwable cause) {
        super(message, cause);
    }

}
