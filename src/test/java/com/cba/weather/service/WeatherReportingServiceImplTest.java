package com.cba.weather.service;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.cba.weather.WeatherTest;
import com.cba.weather.beans.HistoricalData;
import com.cba.weather.beans.WeatherData;
import com.cba.weather.repository.WeatherReportingData;

/**
 * Test class to test the methods of WeatherReportingService class
 * @author Rishina Valsalan
 *
 */
public class WeatherReportingServiceImplTest extends WeatherTest{
	
	@Autowired
	WeatherReportingData weatherReportingData;
	@Autowired
	WeatherReportingService weatherReportingService;
	
	
	
	/**
	 * Test to check the data returned by position data is not null and has data for 10 cities.
	 * Checks to see the position data is not null.
	 * Check to assert there are 8 locations for which weather has to be calculated.
	 */
	@Test
	public void testGetPositionData(){
		ArrayList<WeatherData> arrayListWeatherData = weatherReportingData.getPositionData();
		
		
		assertNotNull("Failure - Expected Position Data that is not Null", arrayListWeatherData);
		assertEquals("Failure - Data expected for 10 cities.", 8, arrayListWeatherData.size());
				
	}

	/**
	 * Test to see the weather calculation returns the values for humidity,pressure , temperature and weather
	 * condition.
	 * Checks to see the weather data is not null
	 * Checks to assert the weather data is calculated and returned.
	 */
	@Test
	public void testGetWeather(){
		
		Calendar currentDateTime = Calendar.getInstance();
		currentDateTime.setTime(new Date());
		
		ArrayList<WeatherData> arrayListWeatherData = weatherReportingService.getWeather(currentDateTime);
		
		assertNotNull("Failure - Expected Weather Data that is not Null", arrayListWeatherData);
		assertTrue("Failure - The service method does not calculate Humidity.",(arrayListWeatherData.get(0).getHumidity()!=0) );
		assertTrue("Failure - The service method does not calculate Pressure.", (arrayListWeatherData.get(0).getPressure()!=0) );
		assertNotNull("Failure - The service method does not calculate Temperature.", arrayListWeatherData.get(0).getTemperature());
		assertFalse("Failure - The service method does not calculate Weather Condition.", arrayListWeatherData.get(0).getWeatherCondition().equals(""));
	}
	
	/**
	 * Test method for API call.
	 * 
	 * The method tests if the return object from the API call is Null. 
	 * It tests if there is a 'history' json returned which has the required weather data of cities.
	 * This json would not be present if the call fails and returns any error message.
	 * 
	 * 
	 */
	@Test
	public void testCallWeatherAPI() {
		
		HistoricalData historicalData = weatherReportingService.callAPI("Sydney", "20170728");
		assertNotNull("Failure - Expected Weather Data that is not Null", historicalData);
		assertNotNull("Failure - Invalid call",historicalData.getHistory());
	}

}
