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
import com.scottlogic.gradtrader.trade.Trade;
import com.scottlogic.gradtrader.trade.TradeListener;
import com.scottlogic.gradtrader.trade.TradeManager;

@BroadcasterService
public class ClientBroadcaster extends SimpleBroadcaster implements PriceListener, TradeListener, Callable<String> {

    Logger logger = LoggerFactory.getLogger(ClientBroadcaster.class);

    private final ObjectMapper mapper = new ObjectMapper();

    private final List<String> subscribedPairs = new LinkedList<String>();
    private final List<String> subscribedTrades = new LinkedList<String>();
    private final Map<String, Price> prices = new LinkedHashMap<String, Price>();

    @Inject
    private PriceFeedFactory priceFeedFactory;

    @Inject
    private TradeManager tradeManager;

    public void start(long frequencyMillis) {
        scheduleFixedBroadcast(this, 0, frequencyMillis, TimeUnit.MILLISECONDS);
        logger.debug("Broadcast prices to client every {}ms", frequencyMillis);
    }

    public void subscribePrices(String pairId) throws SubscriptionException {
        if (subscribedPairs.contains(pairId)) {
            throw new SubscriptionException("Already subscribed");
        }
        priceFeedFactory.getPriceFeed(pairId).addListener(this);
        subscribedPairs.add(pairId);
    }

    public void unsubscribePrices(String pairId) throws SubscriptionException {
        if (!subscribedPairs.contains(pairId)) {
            throw new SubscriptionException("Not subscribed");
        }
        priceFeedFactory.getPriceFeed(pairId).removeListener(this);
        subscribedPairs.remove(pairId);
        prices.remove(pairId);
    }

    public void subscribeTrades(String userId) throws SubscriptionException {
        if (subscribedTrades.contains(userId)) {
            throw new SubscriptionException("Already subscribed");
        }
        tradeManager.addListener(userId, this);
        subscribedTrades.add(userId);
    }

    @Override
    public void notify(Price price) {
        if (subscribedPairs.contains(price.getPairId())) {
            prices.put(price.getPairId(), price);
        }
    }

    @Override
    public void notify(Trade trade) {
        logger.debug("ClientBroadcaster notified of a trade");
        try {
            broadcast(mapper.writeValueAsString(new WebSocketMessage("trades", trade)));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String call() throws Exception {
        try {
            if (prices.isEmpty()) {
                return null;
            }
            String message = mapper.writeValueAsString(new WebSocketMessage("prices", prices.values()));
            return message;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "{error:\" Client Price broadcast error\"}";
        }
    }

}
