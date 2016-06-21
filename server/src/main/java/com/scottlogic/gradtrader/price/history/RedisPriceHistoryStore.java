package com.scottlogic.gradtrader.price.history;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scottlogic.gradtrader.AppInjector;
import com.scottlogic.gradtrader.PriceHistory;
import com.scottlogic.gradtrader.price.PairPrice;
import com.scottlogic.gradtrader.price.Price;

@Singleton
public class RedisPriceHistoryStore implements PriceHistoryStore{

    Logger logger = LoggerFactory.getLogger(RedisPriceHistoryStore.class);
    final JedisPool pool;
    private final ObjectMapper mapper = new ObjectMapper();
    
    private List<Integer> resolutions;
    private List<String> pairs;    
    private Map<String, Map<Integer, Candlestick>> candlesticks;
    
	public RedisPriceHistoryStore(){
		
    	logger.debug("Create RedisPriceHistoryStore");

    	this.pairs = AppInjector.getConfiguration().getValidPairs();
    	this.resolutions = AppInjector.getConfiguration().getPriceHistoryResolutions();
    	
    	logger.debug("{} pairs", pairs.size());
    	logger.debug("{} resolutions", resolutions.size());
    	
    	candlesticks = new LinkedHashMap<String, Map<Integer, Candlestick>>();
    	for (String pair: pairs){
    		candlesticks.put(pair, new LinkedHashMap<Integer, Candlestick>());
    	}

		pool = new JedisPool(new JedisPoolConfig(), "127.0.0.1", 6379);
		
	}
    
    private Map<Integer, Candlestick> getPairCandlesticks(String pair){
    	Map<Integer, Candlestick> map = candlesticks.get(pair);
    	if (map==null){
    		map = new LinkedHashMap<Integer, Candlestick>();
    		candlesticks.put(pair, map);
    	}
    	return map;
    }
        
    private void addPrice(PairPrice pairPrice, Integer resolution){
			String pair = pairPrice.getPair();
			Price price = pairPrice.getPrice();
			long time = price.getTime();

			Map<Integer, Candlestick> pairCandlesticks = getPairCandlesticks(pair); 
			Candlestick candlestick = pairCandlesticks.get(resolution);
        	if (candlestick==null || candlestick.getEnd() < price.getTime()){
    			Long resStart = (time / resolution) * resolution;
            	if (candlestick!=null){
            		Jedis jedis = null;
                    try {
                    	jedis = pool.getResource();
	            		//Issue: store never has currently changing candlestick
	            		//Sortedset got for zrange but must zadd, no zupdate by score
	            		//Hashes would update how we want but can't be got by range
	            		//If we want current candlesticks included, do we need to remove and replace every time? (overhead...)
	            		//Or use list/set/hash but getting range will be the overhead
	            		//Or get the history from Redis and append current ones from the maps here?
	                	String key = String.format("pair:%1$s:res:%2$d", pair, resolution);
	                	String candleJson = mapper.writeValueAsString(candlestick);
	                	jedis.zadd(key, resStart, candleJson);            		
                    } catch (JsonProcessingException e) {
            			logger.error("Error processing candlestick: ", e);
            		} finally {
                    	if (jedis!=null){
                    		jedis.close();
                    	}
                    }
            	}
        		candlestick = new Candlestick(resStart, resolution, price);
        		pairCandlesticks.put(resolution, candlestick);
        	} else {
        		candlestick.update(price);
        	}
        	//logger.debug("Save JSON to Redis: {}", candleJson);
        	
			//String key = String.format("pair:%1$s:res:%2$d:time:%3$d", pair, resolution, resStart);
        	//jedis.set(key, candleJson);
    }
	
	public void addPrice(PairPrice pairPrice){
		for (Integer resolution: resolutions){
			addPrice(pairPrice, resolution);
		}
	}
	
	//TODO: allow missing end param, meaning up to current, in which case add current candlestick from map here? 
	public PriceHistory getHistory(String pair, Integer resolution, Long start, Long end){
		Jedis jedis = null;
        try {        	
        	jedis = pool.getResource();
        	String key = String.format("pair:%1$s:res:%2$d", pair, resolution);        	
        	Set<String> resultset = jedis.zrangeByScore(key, start, end);
        	List<Candlestick> results = new LinkedList<Candlestick>();
        	for (String candleJson: resultset){
        		Candlestick cs = mapper.readValue(candleJson, Candlestick.class);
        		results.add(cs);
        	}
        	return new PriceHistory(pair, resolution, start, end, results); 
        } catch (IOException e) {
			logger.error("Error getting price history: ", e);
			return null;
		} finally {
        	if (jedis!=null){
        		jedis.close();
        	}
        }
	}
	
	
	
	
}
