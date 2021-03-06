/**
 * 
 */
package com.cba.weather.beans;

import org.springframework.stereotype.Component;

/**
 * Class to hold the weather parameters - Location, Position, Elevation, Weather Condition,
 * Temperature, Pressure, and Relative Humidity.
 * @author Rishina Valsalan
 *
 */
@Component
public class WeatherData {
	
	private String location;
	private double latitude;
	private double longitude;
	private double elevation;
	private String weatherCondition;
	private double temperature;
	private double humidity;
	private double pressure;
	public double humidityIndex;
	public double pressureIndex;
	
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public double getElevation() {
		return elevation;
	}
	public void setElevation(double elevation) {
		this.elevation = elevation;
	}
	public String getWeatherCondition() {
		return weatherCondition;
	}
	public void setWeatherCondition(String weatherCondition) {
		this.weatherCondition = weatherCondition;
	}
	public double getTemperature() {
		return temperature;
	}
	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}
	public double getHumidity() {
		return humidity;
	}
	public void setHumidity(double humidity) {
		this.humidity = humidity;
	}
	public double getPressure() {
		return pressure;
	}
	public void setPressure(double pressure) {
		this.pressure = pressure;
	}
	public double getHumidityIndex() {
		return humidityIndex;
	}
	public void setHumidityIndex(double humidityIndex) {
		this.humidityIndex = humidityIndex;
	}
	public double getPressureIndex() {
		return pressureIndex;
	}
	public void setPressureIndex(double pressureIndex) {
		this.pressureIndex = pressureIndex;
	}
	
	
}
