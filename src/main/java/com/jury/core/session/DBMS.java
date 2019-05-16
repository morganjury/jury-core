package com.jury.core.session;

import com.jury.core.exception.UnknownDBMSException;

public enum DBMS {

    POSTGRES("org.postgresql.Driver"),
    SQLSERVER("com.microsoft.sqlserver.jdbc.SQLServerDriver"),
    MYSQL("com.mysql.cj.jdbc.Driver"),
    ORACLE("oracle.jdbc.driver.OracleDriver");

    private String className;

    DBMS(String className) {
        this.className = className;
    }

    public String getClassName() {
        return className;
    }

    public String getConnectionUrl(String host, int port, String databaseName) {
        switch (this) {
            case POSTGRES: // 5432
                return String.format("jdbc:postgresql://%s:%s/%s", host, String.valueOf(port), databaseName);
            case SQLSERVER: // 1433
                return String.format("jdbc:sqlserver://%s:%s;databaseName=%s;", host, String.valueOf(port), databaseName);
            case MYSQL: // 3306
                return String.format("jdbc:mysql://%s:%s/%s", host, String.valueOf(port), databaseName);
            case ORACLE: // 1521
                return String.format("jdbc:oracle:thin:@%s:%s:%s", host, String.valueOf(port), databaseName);
            default:
                throw new UnknownDBMSException(this.name());
        }
    }

    public int getDefaultPort() {
        switch (this) {
            case POSTGRES:
                return 5432;
            case SQLSERVER:
                return 1433;
            case MYSQL:
                return 3306;
            case ORACLE:
                return 1521;
            default:
                throw new UnknownDBMSException(this.name());
        }
    }

}
