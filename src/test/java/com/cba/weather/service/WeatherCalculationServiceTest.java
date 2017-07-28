package com.cba.weather.service;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.cba.weather.WeatherTest;
import com.cba.weather.beans.WeatherDataSet;


/**
 * Test class to test the methods of WeatherConditionService class
 * @author Rishina Valsalan
 *
 */
public class WeatherCalculationServiceTest extends WeatherTest {
	@Autowired
	WeatherCalculationService weatherCalculationService;

	/**
	 * Test method to check weather condition value is calculated 
	 * Sets the value for current date to be passed for the getValue method of WeatherConditionService
	 * Set the value for humidity and pressure index to be passed for the getWeatherCondition method of WeatherConditionService
	 *
	 */
	@Test
	public void testGetWeatherCondition(){
		String weatherCondition;
		WeatherDataSet weatherDataSet = new WeatherDataSet();
		Map<String, Integer> mapWeatherCondition  = new HashMap<String, Integer>();
		
		Calendar currentDateTime = Calendar.getInstance();
		currentDateTime.setTime(new Date());
		
		mapWeatherCondition.put("Cloudy", 4);
		mapWeatherCondition.put("Clear", 1);
		mapWeatherCondition.put("Snow", 0);
		weatherDataSet.setWeatherCondition(mapWeatherCondition);
		weatherCondition = weatherCalculationService.getWeatherCondition(weatherDataSet);
		assertTrue("Failure - Weather Condition value is not calculated", weatherCondition.equals("Rain") );
		
	}
}	

