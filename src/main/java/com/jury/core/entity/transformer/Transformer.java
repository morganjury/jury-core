package com.jury.core.entity.transformer;

import com.jury.core.exception.TransformerException;

public interface Transformer<O, R> {

    R transform(O object) throws TransformerException;
    O get(R object) throws TransformerException;

}
