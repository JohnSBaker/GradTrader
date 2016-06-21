package com.scottlogic.gradtrader.price;

import java.util.LinkedList;
import java.util.List;

public abstract class AbstractPriceGenerator implements PriceGenerator{

	
	private List<PriceListener> listeners = new LinkedList<PriceListener>();
	
	public void addListener(PriceListener listener){
		listeners.add(listener);
	}

	public void removeListener(PriceListener listener){
		listeners.remove(listener);
	}
	
	protected List<PriceListener> getListeners(){
		return listeners;
	}
}
