package com.jury.core.entity.dao;

import com.jury.core.entity.DatabaseObject;
import com.jury.core.entity.transformer.ResultSetTransformer;
import com.jury.core.exception.TransformerException;
import com.jury.core.session.Session;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Dao<DBO extends DatabaseObject, PK> extends DaoExecutor implements DaoTemplate<DBO,PK> {

    public String tableName;
    public String insertColumns;
    public ResultSetTransformer<DBO> resultSetTransformer;

    // TODO pass identity column name

    public Dao(Session session, String tableName, ResultSetTransformer<DBO> resultSetTransformer) {
        super(session);
        this.tableName = tableName;
        this.resultSetTransformer = resultSetTransformer;
    }

    public Dao(Session session, String tableName, String insertColumns, ResultSetTransformer<DBO> resultSetTransformer) {
        this(session, tableName, resultSetTransformer);
        this.insertColumns = insertColumns;
    }

    public void insert(DBO object) throws SQLException {
        if (insertColumns == null) {
            executeWithNoResults("INSERT INTO " + tableName + " VALUES (" + resultSetTransformer.insertString(object) + ")");
        } else {
            executeWithNoResults("INSERT INTO " + tableName + " (" + insertColumns + ") VALUES (" + resultSetTransformer.insertString(object) + ")");
        }
    }

    public void update(PK key, DBO object) throws SQLException {
        throw new UnsupportedOperationException("No generic implementation for 'update', must override.");
    }

    public void delete(PK key) throws SQLException {
        executeWithNoResults("DELETE FROM " + tableName + " WHERE id=" + key);
    }

    public DBO get(PK key) throws SQLException, TransformerException {
        return executeIntoList("SELECT * FROM " + tableName + " WHERE id=" + key, resultSetTransformer, new ArrayList<>()).get(0);
    }

    public List<DBO> get(List<PK> list) throws SQLException {
        return executeIntoList("SELECT * FROM " + tableName + " WHERE id in (" + sqlReadyList(list) + ")", resultSetTransformer, new ArrayList<>());
    }

    public List<DBO> getAll() throws SQLException {
        return executeIntoList("SELECT * FROM " + tableName, resultSetTransformer, new ArrayList<>());
    }

    public List<DBO> getPaged(int numResults, int offset) throws SQLException {
        String query;
        switch (session.getDbms()) {
            case POSTGRES:
                query = "SELECT * FROM " + tableName + " LIMIT " + numResults + " OFFSET " + offset;
                break;
            case SQLSERVER:
                query = "SELECT * FROM " + tableName + " OFFSET " + offset + " FETCH NEXT " + numResults + " ROWS ONLY";
                break;
            case MYSQL:
                query = "SELECT * FROM " + tableName + " LIMIT " + offset + "," + numResults;
                break;
            case ORACLE:
                query = "SELECT * FROM " + tableName + " OFFSET " + offset + " FETCH NEXT " + numResults + " ROWS ONLY";
                break;
            default:
                throw new IllegalArgumentException("Query for DBMS " + session.getDbms().name() + " not known.");
        }
        return executeIntoList(query, resultSetTransformer, new ArrayList<>());
    }

    public static String sqlReadyList(Object ... objects) {
        return sqlReadyList(Arrays.asList(objects));
    }

    static String sqlReadyList(List<Object> list) {
        return buildList(list);
    }

    static String sqlReadyListNullified(List<Object> list) {
        return nullifyBlanks(sqlReadyList(list));
    }

    private static String buildList(List<Object> list) {
        StringBuilder sb = new StringBuilder();
        boolean isFirst = true;
        for (Object o : list) {
            if (!isFirst) {
                sb.append(",");
            } else {
                isFirst = false;
            }
            sb.append("'");
            sb.append(String.valueOf(o).replace("'","''"));
            sb.append("'");
        }
        return sb.toString();
    }

    private static String nullifyBlanks(String insertString) {
        // must loop here because this method is stupid:
        // when replacing ,'','', it sees the first ,'', and replaces it with ,NULL, but then starts at the following '
        // instead of the separating , so it only sees '', instead of ,'',
        while (insertString.contains(",\'\',")) {
            insertString = insertString.replaceAll(",\'\',",",NULL,");
        }
        return insertString;
    }

}
