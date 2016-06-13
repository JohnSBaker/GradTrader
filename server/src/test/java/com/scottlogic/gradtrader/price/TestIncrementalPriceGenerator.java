package com.scottlogic.gradtrader.price;

import org.junit.*;
import static org.junit.Assert.*;

//@Ignore
public class TestIncrementalPriceGenerator{


  protected int value1, value2;
  
  private double delta = 1e-6;

  private IncrementalPriceGenerator target;
  
  @Before
  public void setUp(){
	  target = new IncrementalPriceGenerator("GBPUSD", 1.0, 2.0, 0.2, 0.02);
  }

  @Test
  public void testcall(){
	  assertPrice(target.call(), 0.99, 1.01);
	  assertPrice(target.call(), 1.19, 1.21);
	  assertPrice(target.call(), 1.39, 1.41);
	  assertPrice(target.call(), 1.59, 1.61);
	  assertPrice(target.call(), 1.79, 1.81);
	  assertPrice(target.call(), 1.99, 2.01);
	  assertPrice(target.call(), 1.79, 1.81);
	  assertPrice(target.call(), 1.59, 1.61);
	  assertPrice(target.call(), 1.39, 1.41);
	  assertPrice(target.call(), 1.19, 1.21);
	  assertPrice(target.call(), 0.99, 1.01);
	  assertPrice(target.call(), 1.19, 1.21);
  }
  
  private void assertPrice(Price actualPrice, double expectBid, double expectAsk){
	  assertEquals(expectBid, actualPrice.getBidDbl(), delta);
	  assertEquals(expectAsk, actualPrice.getAskDbl(), delta);
  }
  
  
}
