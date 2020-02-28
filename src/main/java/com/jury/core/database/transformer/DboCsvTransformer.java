package com.jury.core.database.transformer;

import com.jury.core.database.entity.DatabaseObject;
import com.jury.exception.TransformerException;

/**
 * This class should have a subclass for each subclass of com.jury.core.database.entity.DatabaseObject
 * This will either consume or produce an object inheriting from DatabaseObject, to produce or consume a String in CSV format.
 *
 * This class is intended for generating such strings either for use when creating SQL queries of lists of object ID's,
 * or for producing a file that can be used for exporting and importing data.
 *
 * @param <DBO> The class inheriting from DatabaseObject
 */
public interface DboCsvTransformer<DBO extends DatabaseObject<?>> extends DboFileTransformer<DBO> {

    String COMMA_IN_QUOTES_REGEX = ",(?=(?:[^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)";

    /**
     * This method consumes a single line of CSV to produce a DatabaseObject.
     *
     * @param csv the single line of CSV representing a single DatabaseObject
     * @return the DatabaseObject represented
     * @throws TransformerException if the object cannot be produced
     */
    @Override
    DBO consume(String csv) throws TransformerException;

    /**
     * This method consumes a DatabaseObject to produce a String in CSV format.
     *
     * @param object the DatabaseObject
     * @return a String in CSV format
     * @throws TransformerException if the object cannot be consumed
     */
    @Override
    String produce(DBO object) throws TransformerException;

}
