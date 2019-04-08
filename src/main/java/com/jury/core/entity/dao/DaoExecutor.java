package com.jury.core.entity.dao;

import com.jury.core.entity.DatabaseObject;
import com.jury.core.entity.transformer.ResultSetTransformer;
import com.jury.core.exception.EmptyResultSetException;
import com.jury.core.session.Session;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DaoExecutor {

    public interface ResultSetAction {
        void perform(ResultSet rs);
    }

    Session session;

    public DaoExecutor(Session session) {
        this.session = session;
    }

    protected void executeWithNoResults(String query) throws SQLException {
        PreparedStatement statement = session.getConnection().prepareStatement(query);
        try {
            // https://i.stack.imgur.com/V6fjm.png
            // SELECT -> statement.executeQuery() // PSQL and SQLServer throw a SQLException stating EmptyResultSet, MYSQL refuses to run the query
            // INSERT/UPDATE/DELETE -> statement.executeUpdate()
            // ANY -> statement.execute(); statement.getResultSet();
            boolean resultSetPresent = statement.execute();
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
        while (!rs.isAfterLast()) {
            rsa.perform(rs);
            rs.next();
        }
    }

    protected <T extends DatabaseObject> List<T> executeIntoList(String query, ResultSetTransformer<T> transformer, List<T> list) throws SQLException {
        try {
            executeWithAction(query, (result) -> list.add(transformer.produce(result)));
            return list;
        } catch (EmptyResultSetException e) {
            return new ArrayList<>();
        }
    }

}
