package com.cba.weather;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.cba.weather.beans.WeatherData;
import com.cba.weather.service.WeatherReportingService;

/**
 * Class to test the weather report.
 * @author Rishina
 *
 */
public class WeatherReportTest extends WeatherTest {
	@Autowired
	WeatherReportingService weatherReportingService;

	@Test
	public void testGetWeather(){
		ArrayList<WeatherData> arrayListWeatherData = new ArrayList<WeatherData>();
		
		//Get the current date and time of Calendar class to be used to get the MONTH and HOUR from the date.
		Calendar currentDateTime = Calendar.getInstance();
		currentDateTime.setTime(new Date());
		
		//Call the service method to get weather.
		arrayListWeatherData = weatherReportingService.getWeather(currentDateTime);
		
		assertNotNull("Failure - Expected Weather Data that is not Null", arrayListWeatherData);
		assertEquals("Failure - Data expected for 8 cities.", 8, arrayListWeatherData.size());
				
	}
}
