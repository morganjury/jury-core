package com.jury.core.entity.transformer;

import com.jury.core.exception.TransformerException;

/**
 * This class is intended for consuming and producing one object into another. More specifically, intended for
 * com.jury.core.entity.DatabaseObject but have observed more general use cases so has been opened up.
 * For examples, see ResultSetTransformer and CsvTransformer in this same package. The intention is to take an object of
 * interest and produce an object which is an alternative representation of this object of interest, which can then be
 * used to reproduce the original object of interest i.e. this is a bi-directional function.
 *
 * @param <O> The object of interest.
 * @param <R> The alternative representation of the object of interest.
 */
public interface Transformer<O, R> {

    String APOSTROPHE = "'";
    String QUOTE = "\"";
    String COMMA = ",";

    /**
     * This method will consume the object of interest and produce an object that is an alternative representation of
     * this object of interest.
     *
     * @param object the object of interest
     * @return the alternative representation of the object of interest
     * @throws TransformerException if the object of interest cannot be consumed
     */
    R consume(O object) throws TransformerException;

    /**
     * This method will consume the alternative representation object and reproduce the original object of interest.
     * @param object the alternative representation of the object of interest
     * @return the object of interest
     * @throws TransformerException if the object of interest cannot be reproduced
     */
    O produce(R object) throws TransformerException;

}
