package com.scottlogic.gradtrader.trade;

import java.util.Collection;

import com.scottlogic.gradtrader.price.PriceException;

public interface TradeManager {

    Quote requestQuote(Rfq rfq) throws PriceException;

    Trade placeTrade(Quote quote) throws TradeException;

    Collection<Trade> getTrades(String userId);

    void addListener(String userId, TradeListener listener);

    void removeListener(String userId, TradeListener listener);

}
