package com.scottlogic.gradtrader.price.feed;

import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.scottlogic.gradtrader.ScheduledExecutor;
import com.scottlogic.gradtrader.SubscriptionException;
import com.scottlogic.gradtrader.config.GradTraderConfiguration;
import com.scottlogic.gradtrader.price.PriceException;
import com.scottlogic.gradtrader.price.source.PriceSourceFactory;

@Singleton
public class PriceFeedFactoryImpl implements PriceFeedFactory {

    Logger logger = LoggerFactory.getLogger(PriceFeedFactoryImpl.class);

    @Inject
    private PriceSourceFactory priceSourceFactory;
    @Inject
    private GradTraderConfiguration configuration;
    @Inject
    private ScheduledExecutor scheduledExecutor;

    private Map<String, PriceFeed> priceFeeds = new LinkedHashMap<>();

    private PriceFeed createPriceFeed(String pairId) {
        if (!configuration.getValidPairs().containsKey(pairId)) {
            return null;
        }
        try {
            PriceFeed feed = new PriceFeed(pairId, priceSourceFactory, scheduledExecutor);
            priceFeeds.put(pairId, feed);
            feed.start(configuration.getPriceFeedMillis());
            return feed;
        } catch (PriceException pe) {
            return null;
        }
    }

    public PriceFeed getPriceFeed(String pairId) throws SubscriptionException {
        PriceFeed feed = priceFeeds.get(pairId);
        if (feed == null) {
            feed = createPriceFeed(pairId);
            if (feed == null) {
                throw new SubscriptionException("Invalid pair");
            }
        }
        return feed;
    }

}
