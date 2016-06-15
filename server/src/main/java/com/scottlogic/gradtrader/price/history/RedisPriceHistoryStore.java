package com.scottlogic.gradtrader.price.history;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Pipeline;

import com.scottlogic.gradtrader.price.PairPrice;
import com.scottlogic.gradtrader.price.Price;

public class RedisPriceHistoryStore implements PriceHistoryStore{

    Logger logger = LoggerFactory.getLogger(RedisPriceHistoryStore.class);
    final JedisPool pool;
    
	public RedisPriceHistoryStore(){
		
		pool = new JedisPool(new JedisPoolConfig(), "127.0.0.1", 6379);
		
	    logger.debug("Create RedisPriceHistoryStore");
	    //Connecting to Redis server on localhost
	    //jedis = new Jedis("localhost");
	    //logger.debug("Connection to Redis server successful");
	    //check whether server is running or not
	    //logger.debug("Redis server is running: "+jedis.ping());
	}
	
	public void addPrice(PairPrice pairPrice){
		Jedis jedis = null;
        try {
        	jedis = pool.getResource();
			String pair = pairPrice.getPair();
			Price price = pairPrice.getPrice();
			Double mid = price.getMid(); 
			int res = 10000;
			long time = price.getTime();
			Long resStart = time / res;
			String minKey = String.format("pair:%1$s:res:%2$d:time:%3$d:min", pair, res, resStart);
			String maxKey = String.format("pair:%1$s:res:%2$d:time:%3$d:max", pair, res, resStart);
			String opnKey = String.format("pair:%1$s:res:%2$d:time:%3$d:opn", pair, res, resStart);
			String clsKey = String.format("pair:%1$s:res:%2$d:time:%3$d:cls", pair, res, resStart);
			logger.debug("redis close key: {}", clsKey); //threadsafety issue???[B cannot be cast to java.lang.Long
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
        } finally {
        	if (jedis!=null){
        		jedis.close();
        	}
        }
	}
	
	
	
	
	
	
}
