package com.jury.core.database.dao;

import com.jury.core.database.entity.DatabaseObject;
import com.jury.core.database.transformer.DboResultSetTransformer;
import com.jury.core.session.Session;
import com.jury.exception.TransformerException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Dao<PK, DBO extends DatabaseObject<PK>> extends DaoExecutor implements DaoTemplate<PK, DBO> {

    public String idColumn;
    public String getTable;
    public String insertTable;
    public String insertColumns;
    public DboResultSetTransformer<DBO> dboResultSetTransformer;

    public Dao(Session session, String getTable, DboResultSetTransformer<DBO> dboResultSetTransformer) {
        this(session, null, getTable, getTable, null, dboResultSetTransformer);
    }

    public Dao(Session session, String getTable, String insertColumns, DboResultSetTransformer<DBO> dboResultSetTransformer) {
        this(session, null, getTable, getTable, insertColumns, dboResultSetTransformer);
    }

    public Dao(Session session, String idColumn, String getTable, String insertTable, String insertColumns, DboResultSetTransformer<DBO> dboResultSetTransformer) {
        super(session);
        this.idColumn = idColumn == null ? "id" : idColumn;
        this.getTable = getTable;
        this.insertTable = insertTable;
        this.insertColumns = insertColumns;
        this.dboResultSetTransformer = dboResultSetTransformer;
    }

    public void insert(DBO object) throws SQLException {
        if (insertColumns == null) {
            executeWithNoResults("INSERT INTO " + insertTable + " VALUES (" + dboResultSetTransformer.insertString(object) + ")");
        } else {
            executeWithNoResults("INSERT INTO " + insertTable + " (" + insertColumns + ") VALUES (" + dboResultSetTransformer.insertString(object) + ")");
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
        return dboResultSetTransformer.produce(rs);
    }

    public List<DBO> get(List<PK> list) throws SQLException {
        return executeIntoList("SELECT * FROM " + getTable + " WHERE " + idColumn + " in (" + sqlReadyList(list) + ")", dboResultSetTransformer, new ArrayList<>());
    }

    public List<DBO> getAll() throws SQLException {
        return executeIntoList("SELECT * FROM " + getTable, dboResultSetTransformer, new ArrayList<>());
    }

    public List<DBO> getPaged(int numResults, int offset) throws SQLException {
        return executeIntoList(pageQuery("SELECT * FROM " + getTable, numResults, offset), dboResultSetTransformer, new ArrayList<>());
    }

    public String pageQuery(String query, int numResults, int offset) {
        switch (session.getDbms()) {
            case POSTGRES:
                return query + " LIMIT " + numResults + " OFFSET " + offset;
            case SQLSERVER:
                return query + " OFFSET " + offset + " FETCH NEXT " + numResults + " ROWS ONLY";
            case MYSQL:
                return query + " LIMIT " + offset + "," + numResults;
            case ORACLE:
                return query + " OFFSET " + offset + " FETCH NEXT " + numResults + " ROWS ONLY";
            default:
                throw new IllegalArgumentException("Query for DBMS " + session.getDbms().name() + " not known.");
        }
    }

    public static String sqlReadyList(Object ... objects) {
        return buildList(Arrays.asList(objects));
    }

    public static String sqlReadyListFromList(List<?> list) {
        return buildList(list);
    }

    static String sqlReadyListNullified(List<Object> list) {
        return nullifyBlanks(sqlReadyListFromList(list));
    }

    private static String buildList(List<?> list) {
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
        while (insertString.contains(",'',")) {
            insertString = insertString.replaceAll(",'',",",NULL,");
        }
        return insertString;
    }

}
