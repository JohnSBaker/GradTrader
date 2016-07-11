package com.scottlogic.gradtrader.price.source;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.scottlogic.gradtrader.price.Price;
import com.scottlogic.gradtrader.price.PriceException;
import com.scottlogic.gradtrader.price.history.PriceHistory;

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
        logger.debug("Fake price source for {}: base {} range {} spread {} period {}ms ", pairId, base, range, spread,
                period);
    }

    public Price getPrice() {
        return getPrice(System.currentTimeMillis());
    }

    private long mod(long a, long b) {
        return a - b * (a / b);
    }

    public Price getPrice(long time) {
        long mid = getMid(time);
        return new Price(pairId, time, mid - halfSpread, mid + halfSpread);
    }

    private long getMid(long time) {
        return base + (Math.abs(mod(time, period) - period / 2L) - (period / 4L)) * 2L * range / period;
    }

    public PriceHistory getPriceHistory(long start, long end) throws PriceException {
        long min = base - range / 2;
        long max = base + range / 2;
        long open = getMid(start);
        long close = getMid(end);
        if (end - start < period) {
            throw new PriceException("Invalid price history resolution");
        }
        return new PriceHistory(start, end, open, close, min, max);
    }

    public Collection<PriceHistory> getPriceHistory(long from, long to, long resolution) throws PriceException {
        List<PriceHistory> priceHistories = new LinkedList<PriceHistory>();
        long start = from;
        long end;
        do {
            end = start + resolution;
            priceHistories.add(getPriceHistory(start, end));
            start = end;
        } while (end < to);
        return priceHistories;
    }

}
