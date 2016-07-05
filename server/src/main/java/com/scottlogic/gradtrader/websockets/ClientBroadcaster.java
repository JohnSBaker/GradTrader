package com.scottlogic.gradtrader.websockets;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import org.atmosphere.config.service.BroadcasterService;
import org.atmosphere.util.SimpleBroadcaster;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.scottlogic.gradtrader.SubscriptionException;
import com.scottlogic.gradtrader.price.Price;
import com.scottlogic.gradtrader.price.PriceListener;
import com.scottlogic.gradtrader.price.feed.PriceFeedFactory;

@BroadcasterService
public class ClientBroadcaster extends SimpleBroadcaster implements
        PriceListener, Callable<String> {

    Logger logger = LoggerFactory.getLogger(ClientBroadcaster.class);

    private final ObjectMapper mapper = new ObjectMapper();

    private final List<String> subscribedPairs = new LinkedList<String>();
    private final Map<String, Price> prices = new LinkedHashMap<String, Price>();

    @Inject
    private PriceFeedFactory priceFeedFactory;

    public void start(long frequencyMillis) {
        this.scheduleFixedBroadcast(this, 0, frequencyMillis,
                TimeUnit.MILLISECONDS);
    }

    public void subscribe(String pair) throws SubscriptionException {
        if (subscribedPairs.contains(pair)) {
            throw new SubscriptionException("Already subscribed");
        }
        priceFeedFactory.getPriceFeed(pair).addListener(this);
        subscribedPairs.add(pair);
    }

    public void unsubscribe(String pair) throws SubscriptionException {
        if (!subscribedPairs.contains(pair)) {
            throw new SubscriptionException("Not subscribed");
        }
        priceFeedFactory.getPriceFeed(pair).removeListener(this);
        subscribedPairs.remove(pair);
        prices.remove(pair);
    }

    @Override
    public void notify(Price price) {
        if (subscribedPairs.contains(price.getPair())) {
            prices.put(price.getPair(), price);
        }
    }

    @Override
    public String call() throws Exception {
        try {
            if (prices.isEmpty()) {
                return null;
            }
            String message = mapper.writeValueAsString(new WebSocketMessage(
                    "Prices", prices.values()));
            return message;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "{error:\" Client Price broadcast error\"}";
        }
    }

}
