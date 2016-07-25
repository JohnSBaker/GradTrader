package com.scottlogic.gradtrader.price.source;

import com.scottlogic.gradtrader.price.Price;
import com.scottlogic.gradtrader.price.PriceException;
import com.scottlogic.gradtrader.price.history.PriceHistory;

import java.util.Collection;

public interface PriceSource {
    Price getPrice();

    Price getPrice(long time);

    PriceHistory getPriceHistory(long start, long end) throws PriceException;

    Collection<PriceHistory> getPriceHistory(long from, long to, long resolution) throws PriceException;

    int getFrequencyMillis();
}
