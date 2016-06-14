package com.scottlogic.gradtrader.price;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class IncrementalPriceGenerator implements PriceGenerator{

  private final String pair; 
  private final double min;
  private final double max;
  private final double halfSpread;
  private double increment;
  private final double tolerance = 1e-6;
  
  private double current;
  private final ObjectMapper mapper = new ObjectMapper();

  Logger logger = LoggerFactory.getLogger(IncrementalPriceGenerator.class);
  
  public IncrementalPriceGenerator(String pair, double min, double max, double increment, double spread){
	this.pair = pair;
    this.min = min;
    this.max = max;
    this.halfSpread = spread / 2.0;
    this.increment = increment;
    this.current = min;
  }

  public String call() {
	Price price = new Price(current - halfSpread, current + halfSpread);
	PairPrice pairPrice = new PairPrice(pair, price);
	
    double next = current + increment;
    if (next + tolerance < min || next - tolerance > max){
    	increment = increment * -1.0;
      next = current + increment;
    }
    current = next;
    try {
	    String message = mapper.writeValueAsString(pairPrice);
	    logger.debug(message);
		return message; 
	} catch (JsonProcessingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return "{error:\""+pair+" Price error\"}";
	}
  }

}
