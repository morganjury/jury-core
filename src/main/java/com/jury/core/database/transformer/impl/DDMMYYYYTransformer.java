package com.jury.core.database.transformer.impl;

import com.jury.core.database.transformer.DateStringTransformer;
import com.jury.core.exception.TransformerException;

import java.time.LocalDate;
import java.util.Date;

public class DDMMYYYYTransformer implements DateStringTransformer {

    String separator;

    @Override
    public String consume(LocalDate date) throws TransformerException {
        try {
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
                    ? String.format("%s%s%s%s%s", day, separator, month, separator, year)
                    : String.format("%s%s%s", day, month, year);
        } catch (Exception e) {
            throw new TransformerException(Date.class, String.class, e);
        }
    }

    @Override
    public LocalDate produce(String object) throws TransformerException {
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
            return LocalDate.of(year, month, day);
        } catch (Exception e) {
            throw new TransformerException(String.class, Date.class, e);
        }
    }

}
