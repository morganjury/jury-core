package com.jury.core.database.transformer;

import com.jury.core.database.entity.DatabaseObject;
import com.jury.core.exception.TransformerException;

/**
 * This class should have a subclass for each subclass of com.jury.core.database.entity.DatabaseObject
 * This will either consume or produce an object inheriting from DatabaseObject, to produce or consume a String in
 * some format e.g. CSV, pipe delimited file.
 *
 * This class is intended for generating strings that can be used for exporting and importing data to/from a file.
 *
 * @param <DBO> The class inheriting from DatabaseObject
 */
public interface FileTransformer<DBO extends DatabaseObject> extends Transformer<String, DBO> {

    /**
     * This method consumes a single line of a file to produce a DatabaseObject.
     *
     * @param fileLine the single line of the file representing a single DatabaseObject
     * @return the DatabaseObject represented
     * @throws TransformerException if the object cannot be produced
     */
    @Override
    DBO consume(String fileLine) throws TransformerException;

    /**
     * This method consumes a DatabaseObject to produce a String in some format.
     *
     * @param object the DatabaseObject
     * @return a String in some format
     * @throws TransformerException if the object cannot be consumed
     */
    @Override
    String produce(DBO object) throws TransformerException;

}
