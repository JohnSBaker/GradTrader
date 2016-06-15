package com.scottlogic.gradtrader.price;

import java.util.concurrent.Callable;

import com.scottlogic.gradtrader.price.history.PriceHistoryStore;

public interface PriceGenerator extends Callable<String>{
	  public void setPriceHistoryStore(PriceHistoryStore historyStore);

}
