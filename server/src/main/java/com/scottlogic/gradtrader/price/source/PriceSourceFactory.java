package com.scottlogic.gradtrader.price.source;

import com.scottlogic.gradtrader.price.PriceException;

public interface PriceSourceFactory {

    public PriceSource getPriceSource(String pair) throws PriceException;

}
