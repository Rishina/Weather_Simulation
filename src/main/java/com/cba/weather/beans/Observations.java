package com.cba.weather.beans;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Class to hold the parsed weather observations from API call - Temperature, Pressure, Humidity, Weather Condition,
 * @author Rishina Valsalan
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Observations {
	private Date date;
	@JsonProperty("tempm")
	private String temperature;
	@JsonProperty("hum")
	private String humidity;
	@JsonProperty("pressurem")
	private String pressure;
	@JsonProperty("conds")
	private String weatherCondition;
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getTemperature() {
		return temperature;
	}
	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}
	public String getHumidity() {
		return humidity;
	}
	public void setHumidity(String humidity) {
		this.humidity = humidity;
	}
	public String getPressure() {
		return pressure;
	}
	public void setPressure(String pressure) {
		this.pressure = pressure;
	}
	public String getWeatherCondition() {
		return weatherCondition;
	}
	public void setWeatherCondition(String weatherCondition) {
		this.weatherCondition = weatherCondition;
	}
	
}
