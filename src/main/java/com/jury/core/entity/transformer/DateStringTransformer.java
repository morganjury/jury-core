package com.jury.core.entity.transformer;

import com.jury.core.exception.TransformerException;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public interface DateStringTransformer extends Transformer<LocalDate, String> {

    /**
     * java.sql.Date objects can be a pain (hence this transformer) so just call "new Date(System.currentTimeMillis())"
     * @param object the object of interest
     * @return YYYYMMDD string format of the supplied date
     * @throws TransformerException
     */
    @Override
    String consume(LocalDate object) throws TransformerException;

    /**
     *
     * @param object the alternative representation of the object of interest
     * @return
     * @throws TransformerException
     */
    @Override
    LocalDate produce(String object) throws TransformerException;

    static LocalDate getLocalDate(Date date) {
        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

}
