package com.scottlogic.gradtrader.price.source;

import com.scottlogic.gradtrader.price.Price;

public interface PriceSource {

    public Price getPrice();

    public Price getPrice(long time);
}
