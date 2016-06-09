package com.scottlogic.gradtrader.price; 

public class Price{

	private final double bid;
	private final double ask;

	public double getBid() {
		return bid;
	}

	public double getAsk() {
		return ask;
	}
	
	public Price(double bid, double ask){
		this.bid = bid;
		this.ask = ask;
	}


}
