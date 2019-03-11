package com.jury.core.entity.transformer;

import com.jury.core.entity.DatabaseObject;
import com.jury.core.exception.TransformerException;

public interface CsvTransformer<DBO extends DatabaseObject, S extends String> extends Transformer<DBO, S> {

    @Override
    S transform(DBO object) throws TransformerException;

    @Override
    DBO get(S csv) throws TransformerException;

}
