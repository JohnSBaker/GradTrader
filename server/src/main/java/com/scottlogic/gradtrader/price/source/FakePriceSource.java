package com.scottlogic.gradtrader.price.source;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.scottlogic.gradtrader.price.Price;

public class FakePriceSource implements PriceSource {

    Logger logger = LoggerFactory.getLogger(FakePriceSource.class);

    private final String pairId;
    private final long base;
    private final long period;
    private final long range;
    private final long halfSpread;

    public FakePriceSource(String pairId, long base, long period, long range, long spread) {
        this.pairId = pairId;
        this.base = base;
        this.period = period;
        this.range = range;
        this.halfSpread = spread / 2;
    }

    public Price getPrice() {
        return getPrice(System.currentTimeMillis());
    }

    private long mod(long a, long b) {
        return a - b * (a / b);
    }

    public Price getPrice(long time) {
        long mid = base + (Math.abs(mod(time, period) - period / 2) - (period / 4)) * 2 * range / period;
        return new Price(pairId, time, mid - halfSpread, mid + halfSpread);
    }

}
