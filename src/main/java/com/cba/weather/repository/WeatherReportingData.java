package com.cba.weather.repository;

import java.util.ArrayList;

import com.cba.weather.beans.WeatherData;


/**
 * Interface for getting the position data of the Locations.
 * @author Rishina Valsalan
 *
 */
public interface WeatherReportingData {

	public ArrayList<WeatherData> getPositionData();
}
