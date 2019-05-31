package com.jury.core.database.transformer.impl;

import com.jury.core.database.transformer.DateTimeStringTransformer;
import com.jury.core.exception.TransformerException;

import java.time.LocalDateTime;
import java.util.Date;

public class YYYYMMDDhhmmTransformer implements DateTimeStringTransformer {

    @Override
    public String consume(LocalDateTime dateTime) throws TransformerException {
        try {
            String year = "" + dateTime.getYear();
            String month = pad(dateTime.getMonthValue());
            String day = pad(dateTime.getDayOfMonth());
            String hours = pad(dateTime.getHour());
            String minutes = pad(dateTime.getMinute());
            return String.format("%s%s%s%s%s", year, month, day, hours, minutes);
        } catch (Exception e) {
            throw new TransformerException(Date.class, String.class, e);
        }
    }

    private String pad(int input) {
        String textValue = String.valueOf(input);
        return (textValue.length() == 1) ? "0" + textValue : textValue;
    }

    @Override
    public LocalDateTime produce(String object) throws TransformerException {
        if (object.length() != 12) {
            throw new TransformerException(String.class, Date.class,
                    new IllegalArgumentException("Expected 8 chars but got " + object.length() + ": " + object)
            );
        }
        try {
            int year = Integer.valueOf(object.substring(0, 4));
            int month = Integer.valueOf(object.substring(4, 6));
            int day = Integer.valueOf(object.substring(6, 8));
            int hours = Integer.valueOf(object.substring(8,10));
            int minutes = Integer.valueOf(object.substring(10,12));
            return LocalDateTime.of(year, month, day, hours, minutes);
        } catch (Exception e) {
            throw new TransformerException(String.class, Date.class, e);
        }
    }

}
