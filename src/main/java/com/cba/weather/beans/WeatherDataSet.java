package com.cba.weather.beans;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.stereotype.Component;

/**
 * Class to hold the datasets - the weather data fetched from the API call that is parsed and identified for the
 *  same time in the last 14 days
 * @author Rishina Valsalan
 *
 */
@Component
public class WeatherDataSet {
	private ArrayList<Double> temperature;
	private ArrayList<Double> pressure;
	private ArrayList<Double> humidity;
	private Map<String,Integer> weatherCondition;
	
	public ArrayList<Double> getTemperature() {
		return temperature;
	}
	public void setTemperature(ArrayList<Double> temperature) {
		this.temperature = temperature;
	}
	public ArrayList<Double> getPressure() {
		return pressure;
	}
	public void setPressure(ArrayList<Double> pressure) {
		this.pressure = pressure;
	}
	public ArrayList<Double> getHumidity() {
		return humidity;
	}
	public void setHumidity(ArrayList<Double> humidity) {
		this.humidity = humidity;
	}
	public Map<String, Integer> getWeatherCondition() {
		return weatherCondition;
	}
	public void setWeatherCondition(Map<String, Integer> weatherCondition) {
		this.weatherCondition = weatherCondition;
	}
	
	
}
