package com.scottlogic.gradtrader;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Pairs {
    
    List<String> pairs;

    public Pairs() {
        // Jackson deserialization
    }

    public Pairs(List<String> pairs) { 
        this.pairs = pairs;
    }

    @JsonProperty
	public List<String> getPairs() {
		return pairs;
	}

}
