package com.jury.core.entity.transformer;

import org.junit.Test;

import java.sql.Date;

import static org.junit.Assert.assertEquals;

public class SqlDateTransformerTest {

    private final SqlDateTransformer dateTransformer = new SqlDateTransformer();
    private final Date testDate = new Date(2019-1900,3-1,25);

    @Test
    public void consume() {
        assertEquals("20190325", dateTransformer.consume(testDate));
    }

    @Test
    public void produce() {
        assertEquals(testDate.toString(), dateTransformer.produce("20190325").toString());
    }

}
