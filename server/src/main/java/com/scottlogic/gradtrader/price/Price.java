package com.scottlogic.gradtrader.price; 

import java.text.DecimalFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Price{

	private final double bid;
	private final double ask;
	private static DecimalFormat format = new DecimalFormat("####0.000");
	
	public Price(double bid, double ask){
		this.bid = bid;
		this.ask = ask;
	}
	
	public String getBid() {
		return format.format(bid);
	}

	public String getAsk() {
		return format.format(ask);
	}
	
	@JsonIgnore
	public double getBidDbl(){
		return bid;
	}
	
	@JsonIgnore
	public double getAskDbl(){
		return ask;
	}
	
	public String toString(){
		return format.format(bid) + " / " + format.format(ask); 
	}

}
