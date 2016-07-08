package com.scottlogic.gradtrader.trade;

import com.scottlogic.gradtrader.price.PriceException;

public interface QuoteEngine {

    Quote createQuote(Rfq rfq) throws PriceException;

}
