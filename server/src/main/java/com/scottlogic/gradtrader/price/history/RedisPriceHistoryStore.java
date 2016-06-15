package com.scottlogic.gradtrader.price.history;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;

import com.scottlogic.gradtrader.price.PairPrice;
import com.scottlogic.gradtrader.price.Price;

public class RedisPriceHistoryStore implements PriceHistoryStore{

    Logger logger = LoggerFactory.getLogger(RedisPriceHistoryStore.class);
	Jedis jedis;
	
	public RedisPriceHistoryStore(){
	    logger.debug("Create RedisPriceHistoryStore");
	    //Connecting to Redis server on localhost
	    jedis = new Jedis("localhost");
	    logger.debug("Connection to Redis server successful");
	    //check whether server is running or not
	    logger.debug("Redis server is running: "+jedis.ping());
	}
	
	public void addPrice(PairPrice pairPrice){
		String pair = pairPrice.getPair();
		Price price = pairPrice.getPrice();
		Double mid = price.getMid(); 
		int res = 30000;
		long time = price.getTime();
		Long resStart = time / res;
		String minKey = String.format("pair:{}:res:{}:time:{}:min", pair, res, resStart);
		String maxKey = String.format("pair:{}:res:{}:time:{}:max", pair, res, resStart);
		String opnKey = String.format("pair:{}:res:{}:time:{}:opn", pair, res, resStart);
		String clsKey = String.format("pair:{}:res:{}:time:{}:cls", pair, res, resStart);
		if (jedis.exists(clsKey)) { // todo: need to check for the resStart time
			jedis.set(clsKey, mid.toString());
			//TODO atomic
			Double min = Double.valueOf(jedis.get(minKey));
			Double max = Double.valueOf(jedis.get(minKey));
			if (min > mid){
				jedis.set(minKey, mid.toString());				
			} else if (max < mid){
				jedis.set(maxKey, mid.toString());				
			}			
		} else {
			logger.debug("New price history record");
			jedis.set(opnKey, mid.toString());
			jedis.set(clsKey, mid.toString());
			jedis.set(minKey, mid.toString());
			jedis.set(maxKey, mid.toString());
		}
		
	}
	
	
	
	
	
	
}
