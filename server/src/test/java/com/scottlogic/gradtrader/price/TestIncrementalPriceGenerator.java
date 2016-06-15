package com.scottlogic.gradtrader.price;

import java.io.IOException;

import org.junit.*;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.junit.Assert.*;

//@Ignore
public class TestIncrementalPriceGenerator{


  protected int value1, value2;
  private final String pair = "gbpusd";
  private double delta = 1e-6;

  private IncrementalPriceGenerator target;
  private final ObjectMapper mapper = new ObjectMapper();
  
  @Before
  public void setUp(){
	  target = new IncrementalPriceGenerator(pair, 1.0, 2.0, 0.2, 0.02, 0.0001);
  }

  @Test
  public void testcall() throws JsonParseException, JsonMappingException, IOException{
	  assertPrice(target.call(), 0.99, 1.01);
	  assertPrice(target.call(), 1.19, 1.21);
	  assertPrice(target.call(), 1.39, 1.41);
	  assertPrice(target.call(), 1.59, 1.61);
	  assertPrice(target.call(), 1.79, 1.81);
	  assertPrice(target.call(), 1.99, 2.01);
	  assertPrice(target.call(), 1.7901, 1.8101);
	  assertPrice(target.call(), 1.5901, 1.6101);
	  assertPrice(target.call(), 1.3901, 1.4101);
	  assertPrice(target.call(), 1.1901, 1.2101);
	  assertPrice(target.call(), 0.9901, 1.0101);
	  assertPrice(target.call(), 1.1902, 1.2102);
  }
  
  private void assertPrice(String pairPriceMessage, double expectBid, double expectAsk) throws JsonParseException, JsonMappingException, IOException{
  
	  PairPrice actualPairPrice = mapper.readValue(pairPriceMessage, PairPrice.class);
	  assertEquals(pair, actualPairPrice.getPair());
	  Price actualPrice = actualPairPrice.getPrice();
	  assertEquals(expectBid, actualPrice.getBid(), delta);
	  assertEquals(expectAsk, actualPrice.getAsk(), delta);
  }
  
  
}
