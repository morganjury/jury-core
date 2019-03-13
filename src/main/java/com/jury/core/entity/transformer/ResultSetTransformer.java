package com.jury.core.entity.transformer;

import com.jury.core.entity.DatabaseObject;
import com.jury.core.exception.TransformerException;

import java.sql.ResultSet;

/**
 * This class should have a subclass for each subclass of com.jury.core.entity.DatabaseObject
 * This class will consume a ResultSet to produce a DatabaseObject, the consume method is a No-Op, instead there is an
 * insertString method.
 *
 * This class is intended for use with a DBMS, to produce an insert statement or to produce a DBO from the ResultSet
 * returned by a query.
 *
 * @param <DBO> The class inheriting from DatabaseObject
 * @param <R> ResultSet
 */
public interface ResultSetTransformer<DBO extends DatabaseObject, R extends ResultSet> extends Transformer<DBO, R> {

    String NO_OP_MESSAGE = "Cannot generate ResultSet object, use insertString method";

    /**
     * This method consumes a DatabaseObject to produce a String that can be used in a SQL insert statement.
     *
     * @param object the DatabaseObject
     * @return A String ready for use in an insert statement
     * @throws TransformerException if the object cannot be consumed
     */
    String insertString(DBO object) throws TransformerException;

    /**
     * This method is a No-Op.
     *
     * @param object the DatabaseObject
     * @return A ResultSet
     * @throws TransformerException ALWAYS
     */
    @Override
    R consume(DBO object) throws TransformerException;

    /**
     * This method consumes the current ResultSet entry to produce a DatabaseObject
     *
     * @param rs the ResultSet who's current entry represents a single DatabaseObject
     * @return the DatabaseObject represented
     * @throws TransformerException if the object cannot be produced
     */
    @Override
    DBO produce(R rs) throws TransformerException;

}
