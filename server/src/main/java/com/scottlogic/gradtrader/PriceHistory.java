package com.scottlogic.gradtrader;

import java.util.List;

import com.scottlogic.gradtrader.price.history.Candlestick;

public class PriceHistory {

	
	private final String pair;
	private final Integer resolution;
	private final Long from;
	private final Long to;
	private final List<Candlestick> history;
	
	public PriceHistory(String pair, Integer resolution, Long from, Long to,
			List<Candlestick> history) {
		super();
		this.pair = pair;
		this.resolution = resolution;
		this.from = from;
		this.to = to;
		this.history = history;
	}

	public String getPair() {
		return pair;
	}

	public Integer getResolution() {
		return resolution;
	}

	public Long getFrom() {
		return from;
	}

	public Long getTo() {
		return to;
	}

	public List<Candlestick> getHistory() {
		return history;
	}
	
	
	
}
