package com.scottlogic.gradtrader.price.source;

import com.scottlogic.gradtrader.price.Price;

public interface PriceSource {

    Price getPrice();

    Price getPrice(long time);
}
