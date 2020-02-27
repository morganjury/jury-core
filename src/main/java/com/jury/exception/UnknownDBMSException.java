package com.jury.exception;

public class UnknownDBMSException extends RuntimeException {

    public UnknownDBMSException() {
        super("DBMS provided was not recognised");
    }

    public UnknownDBMSException(String dbmsName) {
        super("DBMS provided (" + dbmsName + ") was not recognised");
    }

}
