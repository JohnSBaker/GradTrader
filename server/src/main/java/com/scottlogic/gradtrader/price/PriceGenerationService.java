package com.scottlogic.gradtrader.price;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import org.atmosphere.cpr.Broadcaster;
import org.atmosphere.cpr.BroadcasterFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.scottlogic.gradtrader.AppInjector;
import com.scottlogic.gradtrader.SubscriptionException;
import com.scottlogic.gradtrader.price.history.PriceHistoryStore;
import com.scottlogic.gradtrader.websockets.WebSocketClient;

public class PriceGenerationService implements PriceService, PriceListener {

    Logger logger = LoggerFactory.getLogger(PriceGenerationService.class);
	
	private BroadcasterFactory factory;
	
	@Inject
	private PriceHistoryStore store;

	private List<String> validPairs;
	
	public PriceGenerationService(){
		this.validPairs = AppInjector.getConfiguration().getValidPairs();
		logger.debug("PriceService validPairs: {}", validPairs);
	}
		
	public void setFactory(BroadcasterFactory factory){
		this.factory = factory;
		for (String pair: validPairs){
			initBroadcaster(pair);
		}		
	}

	private Broadcaster initBroadcaster(String pair){
		Broadcaster broadcaster = factory.lookup(pair);
		if (broadcaster==null){
			broadcaster = factory.get(pair);
			Double testPrice = AppInjector.getConfiguration().getTestPrices().get(pair);
    		if (testPrice==null){
    			testPrice = 1.0;    		
    		}
    		PriceGenerator pg = new IncrementalPriceGenerator(pair, testPrice - 0.01, testPrice + 0.01, 0.002, 0.02, 0.00001);
    		pg.addListener(this);
    		broadcaster.scheduleFixedBroadcast(pg, 0, 5, TimeUnit.SECONDS);			
    		logger.debug("Generating prices for {}", pair);
		}
		return broadcaster;		
	}
	
	private Broadcaster lookupBroadcaster(String pair) throws SubscriptionException{
		if (!validPairs.contains(pair)) {
			throw new SubscriptionException("Invalid pair");
		}
		Broadcaster broadcaster = factory.lookup(pair);
		if (broadcaster==null){
			broadcaster = initBroadcaster(pair);
		}
		return broadcaster;
	}
	
	public void subscribe(WebSocketClient client, String pair) throws SubscriptionException{
		Broadcaster broadcaster = lookupBroadcaster(pair);		
		client.subscribe(pair);
		if (broadcaster.getAtmosphereResources().contains(client.getWebSocket().resource())){
    		throw new SubscriptionException("Already subscribed");
		} else {
			broadcaster.addAtmosphereResource(client.getWebSocket().resource());
		}
	}

	public void unsubscribe(WebSocketClient client, String pair) throws SubscriptionException{
		Broadcaster broadcaster = lookupBroadcaster(pair);		
		client.unsubscribe(pair);
		if (!broadcaster.getAtmosphereResources().contains(client.getWebSocket().resource())){
    		throw new SubscriptionException("Not subscribed");
		} else {
			broadcaster.removeAtmosphereResource(client.getWebSocket().resource());
		}
	}
	
	@Override
	public void notify(PairPrice pairPrice) {
		store.addPrice(pairPrice);		
	}	

	
}
