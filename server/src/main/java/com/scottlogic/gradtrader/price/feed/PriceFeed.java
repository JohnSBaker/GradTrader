package com.scottlogic.gradtrader.price.feed;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.scottlogic.gradtrader.price.Price;
import com.scottlogic.gradtrader.price.PriceException;
import com.scottlogic.gradtrader.price.PriceListener;
import com.scottlogic.gradtrader.price.source.PriceSource;
import com.scottlogic.gradtrader.price.source.PriceSourceFactory;

public class PriceFeed {

    Logger logger = LoggerFactory.getLogger(PriceFeed.class);

    private final String pairId;

    private PriceSource priceSource;
    private ScheduledThreadPoolExecutor scheduledExecutor;

    private List<PriceListener> listeners = new LinkedList<PriceListener>();
    private Future<?> future;

    @Inject
    protected PriceFeed(String pairId, PriceSourceFactory priceSourceFactory,
            ScheduledThreadPoolExecutor scheduledExecutor)
            throws PriceException {
        this.pairId = pairId;
        this.priceSource = priceSourceFactory.getPriceSource(pairId);
        this.scheduledExecutor = scheduledExecutor;
    }

    public void start(long frequencyMillis) {
        future = scheduledExecutor.scheduleAtFixedRate(() -> {
            try {
                notifyListeners(priceSource.getPrice());
            } catch (Exception e) {
                logger.error(e.getMessage());
                e.printStackTrace();
            }
        }, frequencyMillis, frequencyMillis, TimeUnit.MILLISECONDS);
        logger.debug("Price feed for {} every {}ms", pairId, frequencyMillis);
    }

    public void stop() {
        future.cancel(false);
    }

    public void addListener(PriceListener listener) {
        listeners.add(listener);
    }

    public void removeListener(PriceListener listener) {
        listeners.remove(listener);
    }

    private void notifyListeners(Price price) {
        for (PriceListener listener : listeners) {
            try {
                listener.notify(price);
            } catch (Exception e) {
                logger.debug("Price listener exception");
            }
        }
    }

}
