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

    private Map<String, PriceFeed> priceFeeds = new LinkedHashMap<String, PriceFeed>();

    @Inject
    public PriceFeedFactoryImpl(PriceSourceFactory priceSourceFactory,
            ScheduledThreadPoolExecutor scheduledExecutor,
            GradTraderConfiguration configuration) {
        this.priceSourceFactory = priceSourceFactory;
        this.scheduledExecutor = scheduledExecutor;
        this.configuration = configuration;
    }

    private PriceFeed createPriceFeed(String pair) {
        if (!configuration.getValidPairs().containsKey(pair)) {
            return null;
        }
        try {
            PriceFeed feed = new PriceFeed(pair, priceSourceFactory,
                    scheduledExecutor);
            priceFeeds.put(pair, feed);
            feed.start(configuration.getPriceFeedMillis());
            return feed;
        } catch (PriceException pe) {
            return null;
        }
    }

    public PriceFeed getPriceFeed(String pair) throws SubscriptionException {
        PriceFeed feed = priceFeeds.get(pair);
        if (feed == null) {
            feed = createPriceFeed(pair);
            if (feed == null) {
                throw new SubscriptionException("Invalid pair");
            }
        }
        return feed;
    }

}
