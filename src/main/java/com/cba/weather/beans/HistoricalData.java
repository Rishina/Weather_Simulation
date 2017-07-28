package com.cba.weather.beans;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Class to hold the parsed historical data from API call.
 * @author Rishina Valsalan
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class HistoricalData {
	private History history;
	
	public History getHistory() {
		return history;
	}
	public void setHistory(History history) {
		this.history = history;
	}
	
	}
