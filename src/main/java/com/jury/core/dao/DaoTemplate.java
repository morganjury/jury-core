package com.jury.core.dao;

import com.jury.core.entity.DatabaseObject;

import java.sql.SQLException;
import java.util.List;

public interface DaoTemplate {

    <T extends DatabaseObject> void insert(T object) throws SQLException;
    <T extends DatabaseObject> void update(int uid, T object) throws SQLException;
    void delete(int uid) throws SQLException;

    <T extends DatabaseObject> T get(int uid) throws SQLException;
    <T extends DatabaseObject> List<T> get(List<Integer> uid) throws SQLException;

}
