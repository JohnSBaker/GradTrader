package com.scottlogic.gradtrader.trade;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.scottlogic.gradtrader.price.Price;
import com.scottlogic.gradtrader.price.PriceException;
import com.scottlogic.gradtrader.price.source.PriceSourceFactory;

@Singleton
public class QuoteEngineImpl implements QuoteEngine {

    private final long quoteExpiry = 5000L;
    private long nextQuoteId = 1L;

    @Inject
    private PriceSourceFactory priceSourceFactory;

    public Quote createQuote(final Rfq rfq) throws PriceException {
        final Price price = priceSourceFactory.getPriceSource(rfq.getPairId()).getPrice();
        final long priceToUse;
        if (rfq.getDirection() == Direction.BUY) {
            priceToUse = price.getAsk();
        } else {
            priceToUse = price.getBid();
        }
        final Quote quote = new Quote(rfq.getUserId(), rfq.getPortfolioId(), nextQuoteId++, System.currentTimeMillis(),
                quoteExpiry, rfq.getPairId(), rfq.getQuantity(), rfq.getDirection(), priceToUse);
        return quote;
    }
}
