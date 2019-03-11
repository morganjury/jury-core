package com.jury.core.entity.transformer;

import com.jury.core.entity.DatabaseObject;
import com.jury.core.exception.TransformerException;

import java.sql.ResultSet;

public interface ResultSetTransformer<DBO extends DatabaseObject, R extends ResultSet> extends Transformer<DBO, R> {

    @Override
    R transform(DBO object) throws TransformerException;

    @Override
    DBO get(R rs) throws TransformerException;

}
