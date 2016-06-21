package com.scottlogic.gradtrader.price.history;

import com.scottlogic.gradtrader.PriceHistory;
import com.scottlogic.gradtrader.price.PairPrice;

public interface PriceHistoryStore {

	public void addPrice(PairPrice pairPrice);
	
	public PriceHistory getHistory(String pair, Integer resolution, Long start, Long end);
	
}
