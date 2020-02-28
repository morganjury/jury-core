package com.jury.core.database.transformer;

import com.jury.core.database.entity.DatabaseObject;
import com.jury.exception.TransformerException;
import com.jury.transform.FileTransformer;

/**
 * This class should have a subclass for each subclass of com.jury.core.database.entity.DatabaseObject
 * This will either consume or produce an object inheriting from DatabaseObject, to produce or consume a String in
 * some format e.g. CSV, pipe delimited file.
 *
 * This class is intended for generating strings that can be used for exporting and importing data to/from a file.
 *
 * @param <DBO> The class inheriting from DatabaseObject
 */
public interface DboFileTransformer<DBO extends DatabaseObject<?>> extends FileTransformer<DBO> {



}
