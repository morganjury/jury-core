package com.jury.core.entity.transformer;

import org.junit.Test;

import java.sql.Timestamp;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class DateStringTransformerTest {

    private final DateStringTransformer dateStringTransformer = new DateStringTransformer();
    private final Date testDate = new Date(2019-1900,3-1,25);
    private final long testTimeStampMillis = 1553589581464L;

    @Test
    public void consume() {
        assertEquals("20190325", dateStringTransformer.consume(testDate));
        assertEquals("20190326", dateStringTransformer.consume(new Timestamp(testTimeStampMillis)));
    }

    @Test
    public void produce() {
        assertEquals(testDate.toString(), dateStringTransformer.produce("20190325").toString());
    }

}
