package com.jury.core.entity.transformer;

import com.jury.core.exception.TransformerException;

import java.util.Date;

public interface DateStringTransformer extends Transformer<Date, String> {

    /**
     * java.sql.Date objects can be a pain (hence this transformer) so just call "new Date(System.currentTimeMillis())"
     * @param object the object of interest
     * @return YYYYMMDD string format of the supplied date
     * @throws TransformerException
     */
    @Override
    String consume(Date object) throws TransformerException;

    /**
     *
     * @param object the alternative representation of the object of interest
     * @return
     * @throws TransformerException
     */
    @Override
    Date produce(String object) throws TransformerException;

}
