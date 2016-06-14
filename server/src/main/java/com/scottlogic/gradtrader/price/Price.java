package com.scottlogic.gradtrader.price; 

import java.text.DecimalFormat;
import java.text.ParseException;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Price{

	private final double bid;
	private final double ask;
	private static DecimalFormat format = new DecimalFormat("####0.000");
	
	public Price(String bid, String ask) throws ParseException{
		this.bid = (Double)format.parse(bid);
		this.ask = (Double)format.parse(ask);		
	}
	
	@JsonCreator
	public Price(@JsonProperty("bid") double bid, @JsonProperty("ask") double ask){
		this.bid = bid;
		this.ask = ask;
	}
	
	public Double getBid() {
		return bid; //format.format(bid);
	}

	public Double getAsk() {
		return ask; //format.format(ask);
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
	public String toString(){
		return format.format(bid) + " / " + format.format(ask); 
	}

}
