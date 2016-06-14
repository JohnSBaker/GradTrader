package com.scottlogic.gradtrader.price;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PairPrice {
	
	private final String pair;
	private final Price price;
	
	@JsonCreator
	public PairPrice(@JsonProperty("pair") String pair, @JsonProperty("price") Price price) {
		super();
		this.pair = pair;
		this.price = price;
	}
	public String getPair() {
		return pair;
	}
	public Price getPrice() {
		return price;
	}
	
	

}
