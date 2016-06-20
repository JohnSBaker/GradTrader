package com.scottlogic.gradtrader.price;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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

public class PriceGenerationService implements PriceService {

    Logger logger = LoggerFactory.getLogger(PriceGenerationService.class);
	
    private static double priceStart = 0.0;

	private BroadcasterFactory factory;
	
	private Map<String, PriceGenerator> priceGenerators = new LinkedHashMap<String, PriceGenerator>();
	
	@Inject
	private PriceHistoryStore store;

	private List<String> validPairs;
	
	public PriceGenerationService(){
		this.validPairs = AppInjector.getConfiguration().getValidPairs();
		logger.debug("PriceService validPairs: {}", validPairs);
	}
	
	public void setFactory(BroadcasterFactory factory){
		this.factory = factory;
	}
	
	private Broadcaster lookupBroadcaster(String pair) throws SubscriptionException{
		if (!validPairs.contains(pair)) {
			throw new SubscriptionException("Invalid pair");
		}
		Broadcaster broadcaster = factory.lookup(pair);
		if (broadcaster==null){
			broadcaster = factory.get(pair);
    		priceStart = priceStart + 1.0;    		
    		PriceGenerator pg = new IncrementalPriceGenerator(pair, priceStart, priceStart + 1.0, 0.2, 0.02, 0.0001);
    		pg.setPriceHistoryStore(store);
    		broadcaster.scheduleFixedBroadcast(pg, 0, 5, TimeUnit.SECONDS);			
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
	
	public String call() {
		return "";
	}	

	
}
