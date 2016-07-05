package com.scottlogic.gradtrader.price.feed;

import com.scottlogic.gradtrader.SubscriptionException;

public interface PriceFeedFactory {

    public PriceFeed getPriceFeed(String pair) throws SubscriptionException;

}
