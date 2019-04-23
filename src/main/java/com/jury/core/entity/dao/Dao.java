package com.jury.core.entity.dao;

import com.jury.core.entity.DatabaseObject;
import com.jury.core.entity.transformer.ResultSetTransformer;
import com.jury.core.exception.TransformerException;
import com.jury.core.session.Session;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Dao<DBO extends DatabaseObject, PK> extends DaoExecutor implements DaoTemplate<DBO,PK> {

    public String idColumn;
    public String getTable;
    public String insertTable;
    public String insertColumns;
    public ResultSetTransformer<DBO> resultSetTransformer;

    public Dao(Session session, String getTable, ResultSetTransformer<DBO> resultSetTransformer) {
        this(session, null, getTable, getTable, null, resultSetTransformer);
    }

    public Dao(Session session, String getTable, String insertColumns, ResultSetTransformer<DBO> resultSetTransformer) {
        this(session, null, getTable, getTable, insertColumns, resultSetTransformer);
    }

    public Dao(Session session, String idColumn, String getTable, String insertTable, String insertColumns, ResultSetTransformer<DBO> resultSetTransformer) {
        super(session);
        this.idColumn = idColumn == null ? "id" : idColumn;
        this.getTable = getTable;
        this.insertTable = insertTable;
        this.insertColumns = insertColumns;
        this.resultSetTransformer = resultSetTransformer;
    }

    public void insert(DBO object) throws SQLException {
        if (insertColumns == null) {
            executeWithNoResults("INSERT INTO " + insertTable + " VALUES (" + resultSetTransformer.insertString(object) + ")");
        } else {
            executeWithNoResults("INSERT INTO " + insertTable + " (" + insertColumns + ") VALUES (" + resultSetTransformer.insertString(object) + ")");
        }
    }

    public void update(PK key, DBO object) throws SQLException {
        throw new UnsupportedOperationException("No generic implementation for 'update', must override.");
    }

    public void delete(PK key) throws SQLException {
        executeWithNoResults("DELETE FROM " + insertTable + " WHERE " + idColumn + "=" + sqlReadyList(key));
    }

    public DBO get(PK key) throws SQLException, TransformerException {
        ResultSet rs = execute("SELECT * FROM " + getTable + " WHERE " + idColumn + "=" + sqlReadyList(key));
        return resultSetTransformer.produce(rs);
    }

    public List<DBO> get(List<PK> list) throws SQLException {
        return executeIntoList("SELECT * FROM " + getTable + " WHERE " + idColumn + " in (" + sqlReadyList(list) + ")", resultSetTransformer, new ArrayList<>());
    }

    public List<DBO> getAll() throws SQLException {
        return executeIntoList("SELECT * FROM " + getTable, resultSetTransformer, new ArrayList<>());
    }

    public List<DBO> getPaged(int numResults, int offset) throws SQLException {
        String query;
        switch (session.getDbms()) {
            case POSTGRES:
                query = "SELECT * FROM " + getTable + " LIMIT " + numResults + " OFFSET " + offset;
                break;
            case SQLSERVER:
                query = "SELECT * FROM " + getTable + " OFFSET " + offset + " FETCH NEXT " + numResults + " ROWS ONLY";
                break;
            case MYSQL:
                query = "SELECT * FROM " + getTable + " LIMIT " + offset + "," + numResults;
                break;
            case ORACLE:
                query = "SELECT * FROM " + getTable + " OFFSET " + offset + " FETCH NEXT " + numResults + " ROWS ONLY";
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
            if (o == null) {
                sb.append("NULL");
                continue;
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
