package com.scottlogic.gradtrader.price.history;

import com.scottlogic.gradtrader.price.Price;

public class Candlestick {

	private final long start;
	private final long resolution;
	private double open;
	private double close;
	private double min;
	private double max;
	
	public Candlestick(long start, long resolution, Price price){
		this.start = start;
		this.resolution = resolution;
		open = price.getMid();
		close = price.getMid();
		min = price.getMid();
		max = price.getMid();
	}
	
	/**
	 * 
	 * @param price
	 * @return true if price time is in range so was updated, otherwise false 
	 */
	public boolean update(Price price){
		if (price.getTime()<start || price.getTime()>start+resolution){
			return false;
		}
		close = price.getMid();
		if (min > price.getMid()){
			min = price.getMid();
		}
		if (max < price.getMid()){
			max = price.getMid();
		}
		return true;
	}
	
	public long getStart() {
		return start;
	}

	public long getResolution() {
		return resolution;
	}

	public long getEnd() {
		return start + resolution;
	}

	public double getOpen() {
		return open;
	}

	public double getClose() {
		return close;
	}

	public double getMin() {
		return min;
	}

	public double getMax() {
		return max;
	}
	
}
