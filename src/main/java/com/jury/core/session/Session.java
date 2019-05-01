package com.jury.core.session;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Session {

    private Properties properties;
    private Connection connection;

    private DBMS dbms;
    private String ip;
    private int port;
    private String databaseName;

    // TODO load database connection params (DBMS, ip, port, username, password) from file
    //  constructor that takes settings

    private Session() {
        throw new UnsupportedOperationException("Must initiate class variables");
    }

    public Session(DBMS dbms, String ip, int port, String databaseName, String username, String password) {
        this.dbms = dbms;
        this.ip = ip;
        this.port = port;
        this.databaseName = databaseName;
        properties = new Properties();
        properties.setProperty("user", username);
        properties.setProperty("password", password);
    }

    public final boolean begin() {
        try {
            Class.forName(dbms.getClassName());
            String url = dbms.getConnectionUrl(ip, port, databaseName);
            connection = DriverManager.getConnection(url, properties);
            return connection != null;
        } catch (ClassNotFoundException | SQLException e) {
            connection = null;
            return false;
        }
    }

    public final boolean end() {
        try {
            connection.close();
        } catch (SQLException e) {
            return false;
        }
        connection = null;
        return true;
    }

    public Connection getConnection() {
        return connection;
    }

    public DBMS getDbms() {
        return dbms;
    }

}
