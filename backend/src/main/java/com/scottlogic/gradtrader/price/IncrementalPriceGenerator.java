package com.scottlogic.gradtrader.price;

public class IncrementalPriceGenerator implements PriceGenerator{

  private final String pair; 
  private final double min;
  private final double max;
  private final double halfSpread;
  private double increment;
  private final double tolerance = 1e-6;
  
  private double current;

  public IncrementalPriceGenerator(String pair, double min, double max, double increment, double spread){
	this.pair = pair;
    this.min = min;
    this.max = max;
    this.halfSpread = spread / 2.0;
    this.increment = increment;
    this.current = min;
  }

  public Price call(){
	Price price = new Price(current - halfSpread, current + halfSpread);
    double next = current + increment;
    if (next + tolerance < min || next - tolerance > max){
    	increment = increment * -1.0;
      next = current + increment;
    }
    current = next;
    System.out.println(pair + ": " + price);
    return price;
  }

}
