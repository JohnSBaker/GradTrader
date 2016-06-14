package com.scottlogic.gradtrader.price;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;

public class ClientPrices {

	private String clientId;
	private List<PairPrice> pairPrices = new LinkedList<PairPrice>();
	
	public ClientPrices(String clientId){
		
	}
	
	@JsonCreator
	public ClientPrices(String clientId, Collection<PairPrice> prices){
		this.pairPrices.addAll(pairPrices);
	}
	
	public void addPairPrice(PairPrice pairPrice){
		this.pairPrices.add(pairPrice);
	}
	
	public String getClientId(){
		return this.clientId;
	}
	
	public List<PairPrice> getPairPrices(){
		return this.pairPrices;
	}
	
	
}
