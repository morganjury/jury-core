package com.jury.core.session;

import com.jury.core.exception.UnknownDBMSException;

public enum DBMS {

    POSTGRES("org.postgresql.Driver"),
    SQLSERVER("com.microsoft.sqlserver.jdbc.SQLServerDriver"),
    MYSQL("MYSQL"),
    ORACLE("ORACLE");

    private String className;

    DBMS(String className) {
        this.className = className;
    }

    public String getClassName() {
        return className;
    }

    public String getConnectionUrl(String ip, int port, String databaseName) {
        switch (this) {
            case POSTGRES:
                return String.format("jdbc:%s://%s:%s/%s", "postgresql", ip, String.valueOf(port), databaseName);
            case SQLSERVER:
                return String.format("jdbc:%s://%s:%s;databaseName=%s;", "sqlserver", ip, String.valueOf(port), databaseName);
            case MYSQL:
            case ORACLE:
            default:
                throw new UnknownDBMSException(this.name());
        }
    }

}
