package com.scottlogic.gradtrader.price;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.scottlogic.gradtrader.price.source.FakePriceSource;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class TestFakePriceSource {

    private final String pair = "gbpusd";
    private int frequenceMillis = 1;
    private long basePrice = 100;
    private FakePriceSource target;
    private Long time;

    @Before
    public void setUp() {
        time = 0L;
        final FakePriceSource.Period[] periods = new FakePriceSource.Period[]{
                new FakePriceSource.Period(100, 500, FakePriceSource.Function.NONE)
        };

        final FakePriceSource.Period[] spreadPeriods = new FakePriceSource.Period[]{
                new FakePriceSource.Period(10, 100, FakePriceSource.Function.NONE)
        };
        target = new FakePriceSource(pair, frequenceMillis, basePrice, periods, spreadPeriods);
    }

    @Test
    public void testCallInitial() throws JsonParseException, JsonMappingException, IOException, PriceException {
        assertPriceAfterTick(100, 100, 0);
    }

    @Test
    public void testCallOnePeriod() throws JsonParseException, JsonMappingException, IOException, PriceException {
        assertPriceAfterTick(100, 100, 100);
    }

    @Test
    public void testCallOneSpreadPeriod() throws JsonParseException, JsonMappingException, IOException, PriceException {
        assertPriceAfterTick(350, 350, 50);
    }

    @Test
    public void testCallOneTick() throws JsonParseException, JsonMappingException, IOException, PriceException {
        assertPriceAfterTick(100, 110, 1);
    }

    @Test
    public void testCallTwoTicks() throws JsonParseException, JsonMappingException, IOException, PriceException {
        assertPriceAfterTick(100, 120, 2);
    }

    @Test
    public void testCallThreeTicks() throws JsonParseException, JsonMappingException, IOException, PriceException {
        assertPriceAfterTick(100, 130, 3);
    }

    @Test
    public void testCallFourTicks() throws JsonParseException, JsonMappingException, IOException, PriceException {
        assertPriceAfterTick(100, 140, 4);
    }

    @Test
    public void testCallFiveTicks() throws JsonParseException, JsonMappingException, IOException, PriceException {
        assertPriceAfterTick(100, 150, 5);
    }

    private void assertPriceAfterTick(final long expectBid, final long expectAsk, final int tickMillis) throws JsonParseException,
            JsonMappingException, IOException, PriceException {
        time = time + tickMillis;
        final Price actualPrice = target.getPrice(time);
        assertEquals(expectBid, actualPrice.getBid());
        assertEquals(expectAsk, actualPrice.getAsk());
    }

}
