package com.scottlogic.gradtrader.trade;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.scottlogic.gradtrader.price.PriceException;

@Singleton
public class TradeManagerImpl implements TradeManager {

    Logger logger = LoggerFactory.getLogger(TradeManagerImpl.class);

    @Inject
    QuoteEngine quoteEngine;

    private long nextTradeId = 1L;

    private Map<Long, Quote> quotes = new LinkedHashMap<Long, Quote>();
    private Map<String, Map<Long, Trade>> trades = new LinkedHashMap<String, Map<Long, Trade>>();
    private Map<String, Set<TradeListener>> listeners = new LinkedHashMap<String, Set<TradeListener>>();

    public TradeManagerImpl() {
    }

    public Quote requestQuote(Rfq rfq) throws PriceException {
        Quote quote = quoteEngine.createQuote(rfq);
        quotes.put(quote.getQuoteId(), quote);
        return quote;
    }

    @Override
    public Trade placeTrade(Quote quote) throws TradeException {

        long placeTime = System.currentTimeMillis();
        Quote savedQuote = quotes.get(quote.getQuoteId());

        if (savedQuote == null) {
            throw new TradeException("Invalid quote id");
        }

        if (savedQuote.isAccepted()) {
            throw new TradeException("Quote already accepted");
        }

        if (placeTime > savedQuote.getExpires()) {
            throw new TradeException("Quote expired");
        }

        Trade trade = new Trade(nextTradeId++, System.currentTimeMillis(), savedQuote);
        saveTrade(trade);
        savedQuote.setAccepted();
        notifyListeners(trade);
        return trade;
    }

    private void saveTrade(Trade trade) {
        Map<Long, Trade> userTrades = trades.get(trade.getQuote().getUserId());
        if (userTrades == null) {
            userTrades = new LinkedHashMap<Long, Trade>();
            trades.put(trade.getQuote().getUserId(), userTrades);
        }
        userTrades.put(trade.getTradeId(), trade);
    }

    public Collection<Trade> getTrades(String userId) {
        try {
            return trades.get(userId).values();
        } catch (NullPointerException npe) {
            return new LinkedList<Trade>();
        }
    }

    public void addListener(String userId, TradeListener listener) {

        Set<TradeListener> tradeListeners = listeners.get(userId);
        if (tradeListeners == null) {
            tradeListeners = new LinkedHashSet<TradeListener>();
            listeners.put(userId, tradeListeners);
        }
        tradeListeners.add(listener);
    }

    private void notifyListeners(Trade trade) {
        Set<TradeListener> tradeListeners = listeners.get(trade.getQuote().getUserId());
        if (tradeListeners != null) {
            for (TradeListener listener : tradeListeners) {
                try {
                    listener.notify(trade);
                } catch (Exception e) {
                    logger.debug("{}: Trade listener exception", this);
                }
            }
        }
    }

}
