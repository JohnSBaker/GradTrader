package com.scottlogic.gradtrader.price;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scottlogic.gradtrader.price.history.PriceHistoryStore;

public class IncrementalPriceGenerator extends AbstractPriceGenerator{

  private final String pair; 
  private double min;
  private double max;
  private final double halfSpread;
  private double increment;
  private final double trend;
  private final double tolerance = 1e-6;
  
  private double current;
  private final ObjectMapper mapper = new ObjectMapper();

  Logger logger = LoggerFactory.getLogger(IncrementalPriceGenerator.class);
  
  public IncrementalPriceGenerator(String pair, double min, double max, double increment, double spread, double trend){
	this.pair = pair;
    this.min = min;
    this.max = max;
    this.halfSpread = spread / 2.0;
    this.increment = increment;
    this.trend = trend;
    this.current = min;
  }
  
  public String call() {
	Price price = new Price(System.currentTimeMillis(), current - halfSpread, current + halfSpread);
	PairPrice pairPrice = new PairPrice(pair, price);
	
	for (PriceListener listener: getListeners()){
		listener.notify(pairPrice);
	}
	
    double next = current + increment;
    if (next + tolerance < min || next - tolerance > max){
    	increment = increment * -1.0;
    	next = current + increment + trend;
    	min = min + trend;
    	max = max + trend;
    }
    current = next;
    try {
	    String message = mapper.writeValueAsString(pairPrice);
		return message; 
	} catch (JsonProcessingException e) {
		e.printStackTrace();
		return "{error:\""+pair+" Price error\"}";
	}
  }

}
