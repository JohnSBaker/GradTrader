package com.scottlogic.gradtrader.price.feed;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import com.google.inject.Inject;
import com.scottlogic.gradtrader.SubscriptionException;
import com.scottlogic.gradtrader.config.GradTraderConfiguration;
import com.scottlogic.gradtrader.price.PriceException;
import com.scottlogic.gradtrader.price.source.PriceSourceFactory;

public class PriceFeedFactoryImpl implements PriceFeedFactory {

    private PriceSourceFactory priceSourceFactory;
    private ScheduledThreadPoolExecutor scheduledExecutor;
    private GradTraderConfiguration configuration;

    private Map<String, PriceFeed> priceFeeds = new LinkedHashMap<>();

    @Inject
    public PriceFeedFactoryImpl(PriceSourceFactory priceSourceFactory,
                                ScheduledThreadPoolExecutor scheduledExecutor,
                                GradTraderConfiguration configuration) {
        this.priceSourceFactory = priceSourceFactory;
        this.scheduledExecutor = scheduledExecutor;
        this.configuration = configuration;
    }

    private PriceFeed createPriceFeed(String pairId) {
        if (!configuration.getValidPairs().containsKey(pairId)) {
            return null;
        }
        try {
            PriceFeed feed = new PriceFeed(pairId, priceSourceFactory,
                    scheduledExecutor);
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
