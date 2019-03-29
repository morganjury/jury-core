package com.jury.core.entity.transformer.impl;

import com.jury.core.entity.transformer.DateStringTransformer;
import com.jury.core.exception.TransformerException;

import java.util.Date;

public class DDMMYYYYTransformer implements DateStringTransformer {

    String separator;

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
            return separator != null
                    ? String.format("%s%s%s%s%s", day, separator, month, separator, year)
                    : String.format("%s%s%s", day, month, year);
        } catch (Exception e) {
            throw new TransformerException(Date.class, String.class, e);
        }
    }

    @Override
    public Date produce(String object) throws TransformerException {
        if (separator != null) {
            object = object.replace(separator,"");
        }
        if (object.length() != 8) {
            throw new TransformerException(String.class, Date.class,
                    new IllegalArgumentException("Expected 8 chars but got " + object.length() + ": " + object)
            );
        }
        try {
            int day = Integer.valueOf(object.substring(0, 2));
            int month = Integer.valueOf(object.substring(2, 4));
            int year = Integer.valueOf(object.substring(4, 8));
            return new Date(year - 1900, month - 1, day);
        } catch (Exception e) {
            throw new TransformerException(String.class, Date.class, e);
        }
    }

}
