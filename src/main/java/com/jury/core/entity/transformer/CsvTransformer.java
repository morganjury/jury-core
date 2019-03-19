package com.jury.core.entity.transformer;

import com.jury.core.entity.DatabaseObject;
import com.jury.core.exception.TransformerException;

/**
 * This class should have a subclass for each subclass of com.jury.core.entity.DatabaseObject
 * This will either consume or produce an object inheriting from DatabaseObject, to produce or consume a String in CSV format.
 *
 * This class is intended for generating such strings either for use when creating SQL queries of lists of object ID's,
 * or for producing a file that can be used for exporting and importing data.
 *
 * @param <DBO> The class inheriting from DatabaseObject
 */
public interface CsvTransformer<DBO extends DatabaseObject> extends Transformer<DBO, String> {

    /**
     * This method consumes a DatabaseObject to produce a String in CSV format.
     *
     * @param object the DatabaseObject
     * @return a String in CSV format
     * @throws TransformerException if the object cannot be consumed
     */
    @Override
    String consume(DBO object) throws TransformerException;

    /**
     * This method consumes a single line of CSV to produce a DatabaseObject.
     *
     * @param csv the single line of CSV representing a single DatabaseObject
     * @return the DatabaseObject represented
     * @throws TransformerException if the object cannot be produced
     */
    @Override
    DBO produce(String csv) throws TransformerException;

}
