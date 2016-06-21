package com.scottlogic.gradtrader;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Resolutions {
    
    List<Integer> resolutions;

    public Resolutions() {
        // Jackson deserialization
    }

    public Resolutions(List<Integer> resolutions) { 
        this.resolutions = resolutions;
    }

    @JsonProperty
	public List<Integer> getResolutions() {
		return resolutions;
	}

}
