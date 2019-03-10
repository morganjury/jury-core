package com.jury.core.dao;

import com.jury.core.exception.EmptyResultSetException;
import com.jury.core.session.Session;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DaddyDao {

    Session session;

    public DaddyDao(Session session) {
        this.session = session;
    }

    protected ResultSet execute(String query) throws SQLException {
        PreparedStatement statement = session.getConnection().prepareStatement(query);
        ResultSet rs = statement.executeQuery();
        if (!rs.isBeforeFirst()) {
            throw new EmptyResultSetException(session.getDbms());
        }
        rs.next();
        return rs;
    }

    protected void executeWithNoResults(String query) throws SQLException {
        PreparedStatement statement = session.getConnection().prepareStatement(query);
        try {
            statement.executeQuery();
        } catch (SQLException e) {
            String emptyResultSetMessageForDbms = EmptyResultSetException.getMessage(session.getDbms());
            if (!e.getMessage().contains(emptyResultSetMessageForDbms)) {
                throw e;
            }
        }
    }

}
