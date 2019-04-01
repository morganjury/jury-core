package com.jury.core.entity.transformer;

import com.jury.core.exception.TransformerException;

import java.time.LocalDate;
import java.time.ZoneId;
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

    static LocalDate getLocalDate(Date date) {
        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    static Date buildDate(int day, int month, int year) {
        return Date.from(
                LocalDate.of(year, month, day)
                        .atStartOfDay(ZoneId.systemDefault())
                        .toInstant()
        );
    }

}
