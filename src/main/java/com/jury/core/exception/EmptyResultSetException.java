package com.jury.core.exception;

import com.jury.core.session.DBMS;
import com.sun.istack.internal.NotNull;

import java.sql.SQLException;

public class EmptyResultSetException extends SQLException {

    public static final String DEFAULT_MESSAGE = "Empty result set";
    public static final String POSTGRESQL_MESSAGE = "No results were returned by the query";
    public static final String SQLSERVER_MESSAGE = "The statement did not return a result set";

    public EmptyResultSetException() {
        super(DEFAULT_MESSAGE);
    }

    public EmptyResultSetException(@NotNull final DBMS dbms) {
        super(getMessage(dbms));
    }

    public static String getMessage(@NotNull DBMS dbms) {
        switch (dbms) {
            case POSTGRES:
                return POSTGRESQL_MESSAGE;
            case SQLSERVER:
                return SQLSERVER_MESSAGE;
            case MYSQL:
            case ORACLE:
            default:
                return DEFAULT_MESSAGE;
        }
    }

}
