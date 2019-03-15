package com.jury.core.entity.dao;

import com.jury.core.entity.transformer.ResultSetTransformer;
import com.jury.core.exception.EmptyResultSetException;
import com.jury.core.session.Session;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

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
            // TODO https://i.stack.imgur.com/V6fjm.png
            // SELECT -> statement.executeQuery()
            // INSERT/UPDATE/DELETE -> statement.executeUpdate()
            // ANY -> statement.execute(); statement.getResultSet();
            // psql and sql server do not mind inserts being done with executeQuery, mysql does
            boolean resultSetPresent = statement.execute(); // TODO more often than not will be executeUpdate?
            if (resultSetPresent) {
                throw new SQLException("ResultSet found but no results expected from query");
            }
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
        while (!rs.isAfterLast()) { // this change may only apply to MYSQL so test
            rsa.perform(rs);
            rs.next();
        }
    }

    @SuppressWarnings("unchecked")
    protected void executeIntoList(String query, ResultSetTransformer transformer, List list) throws SQLException {
        executeWithAction(query, (result) -> list.add(transformer.produce(result)));
    }

}
