package com.jury.core.database.transformer;

import com.jury.core.database.entity.DatabaseObject;
import com.jury.transform.ResultSetTransformer;

/**
 * This class should have a subclass for each subclass of com.jury.core.database.entity.DatabaseObject
 * This class will consume a ResultSet to produce a DatabaseObject, the consume method is a No-Op, instead there is an
 * insertString method.
 *
 * This class is intended for use with a DBMS, to produce an insert statement or to produce a DBO from the ResultSet
 * returned by a query.
 *
 * @param <DBO> The class inheriting from DatabaseObject
 */
public interface DboResultSetTransformer<PK, DBO extends DatabaseObject<PK>> extends ResultSetTransformer<DBO> {



}
