package com.cba.weather;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


import com.cba.weather.beans.WeatherData;
import com.cba.weather.service.WeatherReportingService;


/**
 * Class to call the scheduler every 10 minutes to get the weather data based on the current date and time.
 * @author Rishina Valsalan
 *
 */
@Component
public class WeatherReport {
	public static Logger logger = LoggerFactory.getLogger(WeatherReport.class);
	@Autowired
	WeatherReportingService weatherReportingService;
	
	/**
	 * Method scheduled to get the weather data at every fixed interval.
	 * 1)Gets the current time in ISO8601 date format for the output feed.
	 * 2)Gets the current date and time of Calendar class to be used to get the MONTH and HOUR from the date.
	 * 3)Call the service method to get weather.
	 * 4)Loop through the arrayList of WeatherData returned by the service method to log the weather data of each location.
	 */
	@Scheduled(fixedRate=600000)
	public void getWeather(){
		
		logger.info("Scheduled call to generate weather feed - START");
		ArrayList<WeatherData> arrayListWeatherData = new ArrayList<WeatherData>();

		String strCurrentDateTime = getISODate();
		
		Calendar currentDateTime = Calendar.getInstance();
		currentDateTime.setTime(new Date());
		
		arrayListWeatherData = weatherReportingService.getWeather(currentDateTime);
		logFeed(arrayListWeatherData, strCurrentDateTime);
		
		logger.info("Schedule call - END");
	}
	
	/**
	 * Method to get the current date in ISO format for the feed.
	 * @return
	 */
	public String getISODate(){
		DateTimeFormatter dateFormatter = DateTimeFormatter.ISO_DATE_TIME;
		LocalDateTime localDateTime = LocalDateTime.ofInstant((new Date()).toInstant(), ZoneId.of("UTC"));
		return (dateFormatter.format(localDateTime));
	}
	
	/**
	 * Method to log the weather records in the '|' delimited format.
	 * @param arrayListWeatherData
	 * @param strCurrentDateTime
	 */
	public void logFeed(ArrayList<WeatherData> arrayListWeatherData, String strCurrentDateTime){
		for(WeatherData weatherData : arrayListWeatherData){
			logger.info(weatherData.getLocation()
					+"|"+ weatherData.getLatitude()+","+weatherData.getLongitude()+","+weatherData.getElevation()
					+"|"+ strCurrentDateTime
					+"|"+ weatherData.getWeatherCondition()
					+"|"+ weatherData.getTemperature()
					+"|"+ weatherData.getPressure()
					+"|" + weatherData.getHumidity());
		}
	}
}
