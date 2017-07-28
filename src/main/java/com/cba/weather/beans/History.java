package com.cba.weather.beans;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


/**
 * Class to hold the parsed history data from API call.
 * @author Rishina Valsalan
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class History {
	
	private ArrayList<Observations> observations;

	public ArrayList<Observations> getObservations() {
		return observations;
	}

	public void setObservations(ArrayList<Observations> observations) {
		this.observations = observations;
	}
	
	

}
