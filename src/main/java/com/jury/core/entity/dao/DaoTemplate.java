package com.jury.core.entity.dao;

import com.jury.core.entity.DatabaseObject;

import java.sql.SQLException;
import java.util.List;

public interface DaoTemplate<DBO extends DatabaseObject> {

    void insert(DBO object) throws SQLException;
    void update(int uid, DBO object) throws SQLException;
    void delete(int uid) throws SQLException;

    DBO get(int uid) throws SQLException;
    List<DBO> get(List<Integer> uid) throws SQLException;

}
