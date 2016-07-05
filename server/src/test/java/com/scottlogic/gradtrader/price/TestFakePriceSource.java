package com.scottlogic.gradtrader.price;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.scottlogic.gradtrader.price.source.FakePriceSource;

//@Ignore
public class TestFakePriceSource {

    private final String pair = "gbpusd";

    private FakePriceSource target;

    private Long time = 1467021300000L;

    @Before
    public void setUp() {
        target = new FakePriceSource(pair, 150000L, 20000L, 2000L, 2000L);
    }

    @Test
    public void testCall() throws JsonParseException, JsonMappingException,
            IOException {
        assertPriceAfterTick(150000L, 152000L, 0L); // 0s
        assertPriceAfterTick(149800L, 151800L, 1000L);
        assertPriceAfterTick(149600L, 151600L, 1000L);
        assertPriceAfterTick(149400L, 151400L, 1000L);
        assertPriceAfterTick(149200L, 151200L, 1000L);
        assertPriceAfterTick(149000L, 151000L, 1000L); // 5s
        assertPriceAfterTick(148800L, 150800L, 1000L);
        assertPriceAfterTick(148600L, 150600L, 1000L);
        assertPriceAfterTick(148400L, 150400L, 1000L);
        assertPriceAfterTick(148200L, 150200L, 1000L);
        assertPriceAfterTick(148000L, 150000L, 1000L); // 10s
        assertPriceAfterTick(148200L, 150200L, 1000L);
        assertPriceAfterTick(148400L, 150400L, 1000L);
        assertPriceAfterTick(148600L, 150600L, 1000L);
        assertPriceAfterTick(148800L, 150800L, 1000L);
        assertPriceAfterTick(149000L, 151000L, 1000L); // 15s
        assertPriceAfterTick(149200L, 151200L, 1000L);
        assertPriceAfterTick(149400L, 151400L, 1000L);
        assertPriceAfterTick(149600L, 151600L, 1000L);
        assertPriceAfterTick(149800L, 151800L, 1000L);
        assertPriceAfterTick(150000L, 152000L, 1000L); // 20s
        assertPriceAfterTick(149998L, 151998L, 10L); // 20.010s
        assertPriceAfterTick(149980L, 151980L, 90L); // 20.100s
        assertPriceAfterTick(149800L, 151800L, 900L); // 21s
    }

    private void assertPriceAfterTick(long expectBid, long expectAsk,
            long tickMillis) throws JsonParseException, JsonMappingException,
            IOException {
        time = time + tickMillis;
        Price actualPrice = target.getPrice(time);
        assertEquals(pair, actualPrice.getPair());
        assertEquals(expectBid, actualPrice.getBid());
        assertEquals(expectAsk, actualPrice.getAsk());
    }

}
