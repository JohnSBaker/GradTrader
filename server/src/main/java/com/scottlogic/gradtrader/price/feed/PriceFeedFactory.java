package com.scottlogic.gradtrader.price.feed;

import com.scottlogic.gradtrader.SubscriptionException;

public interface PriceFeedFactory {

    PriceFeed getPriceFeed(String pairId) throws SubscriptionException;

}
