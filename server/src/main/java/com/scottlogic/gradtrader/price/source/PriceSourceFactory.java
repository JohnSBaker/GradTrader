package com.scottlogic.gradtrader.price.source;

import com.scottlogic.gradtrader.price.PriceException;

public interface PriceSourceFactory {

    PriceSource getPriceSource(String pairId) throws PriceException;

}
