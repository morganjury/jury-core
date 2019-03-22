package com.jury.core.entity.dao;

import com.jury.core.entity.DatabaseObject;
import com.jury.core.exception.TransformerException;

import java.sql.SQLException;
import java.util.List;

public interface DaoTemplate<DBO extends DatabaseObject, PK> {

    void insert(DBO object) throws SQLException;
    void update(PK uid, DBO object) throws SQLException;
    void delete(PK uid) throws SQLException;

    DBO get(PK  uid) throws SQLException, TransformerException;
    List<DBO> get(List<PK> uid) throws SQLException;
    List<DBO> getAll() throws SQLException;
    List<DBO> getPaged(int number, int offset) throws SQLException;

}