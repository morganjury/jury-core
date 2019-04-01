package com.jury.core.entity.transformer.impl;

import com.jury.core.entity.transformer.DateStringTransformer;
import com.jury.core.exception.TransformerException;

import java.time.LocalDate;
import java.util.Date;

public class YYYYMMDDTransformer implements DateStringTransformer {

    String separator;

    @Override
    public String consume(Date object) throws TransformerException {
        try {
            LocalDate date = DateStringTransformer.getLocalDate(object);
            String year = "" + date.getYear();
            String month = "" + date.getMonthValue();
            String day = "" + date.getDayOfMonth();
            if (month.length() == 1) {
                month = "0" + month;
            }
            if (day.length() == 1) {
                day = "0" + day;
            }
            return separator != null
                    ? String.format("%s%s%s%s%s", year, separator, month, separator, day)
                    : String.format("%s%s%s", year, month, day);
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
            int year = Integer.valueOf(object.substring(0, 4));
            int month = Integer.valueOf(object.substring(4, 6));
            int day = Integer.valueOf(object.substring(6, 8));
            return DateStringTransformer.buildDate(day, month, year);
        } catch (Exception e) {
            throw new TransformerException(String.class, Date.class, e);
        }
    }

}
