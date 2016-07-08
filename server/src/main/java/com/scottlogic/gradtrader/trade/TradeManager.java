package com.scottlogic.gradtrader.trade;

import java.util.Collection;

import com.scottlogic.gradtrader.price.PriceException;

public interface TradeManager {

    public Quote requestQuote(Rfq rfq) throws PriceException;

    public Trade placeTrade(Quote quote) throws TradeException;

    public Collection<Trade> getTrades(String userId);

    public void addListener(String userId, TradeListener listener);

}
