package com.jury.core.entity.transformer;

import com.jury.core.exception.TransformerException;

import java.sql.Date;

@SuppressWarnings("deprecation")
public class SqlDateTransformer implements Transformer<Date, String> {

    /**
     * java.sql.Date objects can be a pain (hence this transformer) so just call "new Date(System.currentTimeMillis())"
     * @param object the object of interest
     * @return YYYYMMDD string format of the supplied date
     * @throws TransformerException
     */
    @Override
    public String consume(Date object) throws TransformerException {
        try {
            String year = "" + (object.getYear() + 1900);
            String month = "" + (object.getMonth() + 1);
            if (month.length() == 1) {
                month = "0" + month;
            }
            String day = "" + object.getDate();
            if (day.length() == 1) {
                day = "0" + day;
            }
            return String.format("%s%s%s", year, month, day);
        } catch (Exception e) {
            throw new TransformerException(Date.class, String.class, e);
        }
    }

    /**
     *
     * @param object the alternative representation of the object of interest
     * @return
     * @throws TransformerException
     */
    @Override
    public Date produce(String object) throws TransformerException {
        if (object.length() != 8) {
            throw new TransformerException(String.class, Date.class,
                    new IllegalArgumentException("Expected 8 chars but got " + object.length() + ": " + object)
            );
        }
        try {
            int year = Integer.valueOf(object.substring(0, 4));
            int month = Integer.valueOf(object.substring(4, 6));
            int day = Integer.valueOf(object.substring(6, 8));
            return new Date(year - 1900, month - 1, day);
        } catch (Exception e) {
            throw new TransformerException(String.class, Date.class, e);
        }
    }

}
