package com.scottlogic.gradtrader.price.history;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.scottlogic.gradtrader.price.Price;

public class Candlestick {

	private final long start;
	private final long resolution;
	private double open;
	private double close;
	private double min;
	private double max;
	
	@JsonCreator
	public Candlestick(@JsonProperty("start") Long start,
			@JsonProperty("resolution") Long resolution, 
			@JsonProperty("open") Double open, 
			@JsonProperty("close") Double close, 
			@JsonProperty("min") Double min, 
			@JsonProperty("max") Double max) 
	{
		this.start = start;
		this.resolution = resolution;
		this.open = open;
		this.close = close;
		this.min = min;
		this.max = max;
	}
	
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
	
	@JsonProperty
	public long getStart() {
		return start;
	}

	@JsonProperty
	public long getResolution() {
		return resolution;
	}

	@JsonIgnore
	public long getEnd() {
		return start + resolution;
	}

	@JsonProperty
	public double getOpen() {
		return open;
	}

	@JsonProperty
	public double getClose() {
		return close;
	}

	@JsonProperty
	public double getMin() {
		return min;
	}

	@JsonProperty
	public double getMax() {
		return max;
	}
	
}
