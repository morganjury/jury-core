package com.jury.core.database.transformer.impl;

import junitparams.FileParameters;
import junitparams.JUnitParamsRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;

@RunWith(JUnitParamsRunner.class)
public class YYYYMMDDhhmmTransformerTest {

    public static final String testResourcesPath = "test/main/resources/";
    private YYYYMMDDhhmmTransformer transformer = new YYYYMMDDhhmmTransformer();

    @Test
    @FileParameters(testResourcesPath + "YYYYMMDDhhmmTransformerProduce.csv")
    public void produce(int year, int month, int day, int hours, int mins, String dateString) {
        LocalDateTime expectedResult = LocalDateTime.of(year, month, day, hours, mins);
        assertEquals(expectedResult, transformer.produce(dateString));
    }

    @Test
    @FileParameters(testResourcesPath + "YYYYMMDDhhmmTransformerConsume.csv")
    public void consume(String expectedResult, int year, int month, int day, int hours, int mins) {
        LocalDateTime input = LocalDateTime.of(year, month, day, hours, mins);
        assertEquals(expectedResult, transformer.consume(input));
    }

}
