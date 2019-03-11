package com.jury.core.entity.dao;

import com.jury.core.exception.EmptyResultSetException;
import com.jury.core.session.Session;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DaddyDao {

    public interface ResultSetAction {
        void perform(ResultSet rs);
    }

    Session session;

    public DaddyDao(Session session) {
        this.session = session;
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

    protected ResultSet execute(String query) throws SQLException {
        PreparedStatement statement = session.getConnection().prepareStatement(query);
        ResultSet rs = statement.executeQuery();
        if (!rs.isBeforeFirst()) {
            throw new EmptyResultSetException(session.getDbms());
        }
        rs.next();
        return rs;
    }

    protected void executeWithAction(String query, ResultSetAction rsa) throws SQLException {
        ResultSet rs = execute(query);
        while (!rs.isLast()) {
            rsa.perform(rs);
            rs.next();
        }
    }

}
