package com.scottlogic.gradtrader.price; 

import java.text.DecimalFormat;
import java.text.ParseException;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Price{

	private final long time;
	private final double bid;
	private final double ask;
	private final double mid;
	private static DecimalFormat format = new DecimalFormat("####0.000");
	
	public Price(Long time, String bid, String ask) throws ParseException{
		this.time = time;
		this.bid = (Double)format.parse(bid);
		this.ask = (Double)format.parse(ask);
		this.mid = (this.bid + this.ask) / 2.0;
	}
	
	@JsonCreator
	public Price(@JsonProperty("time") long time, @JsonProperty("bid") double bid, @JsonProperty("ask") double ask){
		this.time = time;
		this.bid = bid;
		this.ask = ask;
		this.mid = (bid + ask) / 2.0;
	}
	
	public Long getTime(){
		return time;
	}
	
	public Double getBid() {
		return bid; //format.format(bid);
	}

	public Double getAsk() {
		return ask; //format.format(ask);
	}
	
	@JsonIgnore
	public Double getMid() {
		return mid; //format.format(mid);
	}
	
	@JsonIgnore
	public double getBidDbl(){
		return bid;
	}
	
	@JsonIgnore
	public double getAskDbl(){
		return ask;
	}
	
	@JsonIgnore
	public double getMidDbl(){
		return mid;
	}
	
	@JsonIgnore
	public String toString(){
		return format.format(bid) + " / " + format.format(ask); 
	}

}
