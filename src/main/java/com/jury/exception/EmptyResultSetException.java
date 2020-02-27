package com.jury.exception;

import com.jury.core.session.DBMS;

import java.sql.SQLException;

public class EmptyResultSetException extends SQLException {

    public static final String DEFAULT_MESSAGE = "Empty result set";
    public static final String POSTGRESQL_MESSAGE = "No results were returned by the query";
    public static final String SQLSERVER_MESSAGE = "The statement did not return a result set";
    public static final String MYSQL_MESSAGE = "Illegal operation on empty result set.";

    public EmptyResultSetException() {
        super(DEFAULT_MESSAGE);
    }

    public EmptyResultSetException(final DBMS dbms) {
        super(getMessage(dbms));
    }

    public static String getMessage(DBMS dbms) {
        switch (dbms) {
            case POSTGRES:
                return POSTGRESQL_MESSAGE;
            case SQLSERVER:
                return SQLSERVER_MESSAGE;
            case MYSQL:
                return MYSQL_MESSAGE;
            case ORACLE:
            default:
                return DEFAULT_MESSAGE;
        }
    }

}
