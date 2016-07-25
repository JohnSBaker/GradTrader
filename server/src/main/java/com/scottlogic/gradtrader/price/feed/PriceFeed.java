package com.scottlogic.gradtrader.price.feed;

import com.google.inject.Inject;
import com.scottlogic.gradtrader.price.Price;
import com.scottlogic.gradtrader.price.PriceException;
import com.scottlogic.gradtrader.price.PriceListener;
import com.scottlogic.gradtrader.price.source.PriceSource;
import com.scottlogic.gradtrader.price.source.PriceSourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class PriceFeed {

    Logger logger = LoggerFactory.getLogger(PriceFeed.class);

    private final String pairId;
    private final PriceSource priceSource;
    private final ScheduledThreadPoolExecutor scheduledExecutor;

    private final List<PriceListener> listeners = new LinkedList<PriceListener>();
    private Future<?> future;

    @Inject
    protected PriceFeed(final String pairId, final PriceSourceFactory priceSourceFactory,
                        final ScheduledThreadPoolExecutor scheduledExecutor) throws PriceException {
        this.pairId = pairId;
        this.priceSource = priceSourceFactory.getPriceSource(pairId);
        this.scheduledExecutor = scheduledExecutor;
    }

    public PriceSource getPriceSource() {
        return priceSource;
    }
    
    public void start() {
        final int frequencyMillis = priceSource.getFrequencyMillis();
        future = scheduledExecutor.scheduleAtFixedRate(() -> {
            try {
                notifyListeners(priceSource.getPrice());
            } catch (final Exception e) {
                logger.error(e.getMessage());
                e.printStackTrace();
            }
        }, (long) (frequencyMillis * Math.random()), frequencyMillis, TimeUnit.MILLISECONDS);
        logger.debug("Price feed for {} every {}ms", pairId, frequencyMillis);
    }

    public void stop() {
        future.cancel(false);
    }

    public void addListener(final PriceListener listener) {
        listeners.add(listener);
    }

    public void removeListener(final PriceListener listener) {
        listeners.remove(listener);
    }

    private void notifyListeners(final Price price) {
        for (final PriceListener listener : listeners) {
            try {
                listener.notify(price);
            } catch (final Exception e) {
                logger.debug("Price listener exception");
            }
        }
    }
}
