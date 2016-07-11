package com.scottlogic.gradtrader.price.source;

import java.util.Collection;

import com.scottlogic.gradtrader.price.Price;
import com.scottlogic.gradtrader.price.PriceException;
import com.scottlogic.gradtrader.price.history.PriceHistory;

public interface PriceSource {

    Price getPrice();

    Price getPrice(long time);

    PriceHistory getPriceHistory(long start, long end) throws PriceException;

    Collection<PriceHistory> getPriceHistory(long from, long to, long resolution) throws PriceException;

}
