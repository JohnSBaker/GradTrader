package com.scottlogic.gradtrader.price.history;

import com.scottlogic.gradtrader.price.PairPrice;

import redis.clients.jedis.Jedis;

public interface PriceHistoryStore {

	public void addPrice(PairPrice pairPrice);
	
	
}
