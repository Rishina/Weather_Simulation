/**---------------------------------------------------------------------------------------------------------------------
 * Application to generate fake weather feed for a game
 *
 * The application takes a location.txt file as input.The file has json data for the locations of which weather needs 
 * to be calculated and the latitude, longitude and elevation. The format is :
 * { "location": "Sydney","position": [-33.86,151.21,39]}
 *
 * From this file, we parse and find the elevation type and latitude region of each of the locations. 
 * 
 * An API call is made to Wunderground - free weather API, to get historical data. The data for last 14 days is fetched 
 * for each of the location. 
 * 
 * Drift method for forecasting is used to calculate the drift for temperature , pressure and humidity values to get the 
 * current values for temperature , pressure and humidity.
 * Based on the weather conditions for the last 14 days, an index value is calculated to find the chances of the possible 
 * current weather condition.
 *
 * The calculated values are logged and are written out to a text file - Weather.Txt in \target\resources folder.
 *
 * The Spring boot project executes a task scheduler every 10 minutes from the first run to get weather data from 10 cities. 
 * -------------------------------------------------------------------------------------------------------------------------
 */
package com.cba.weather;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;



 /** 
 * Class with main method for starting the spring boot application.
 * @author Rishina Valsalan
 *
 */
@SpringBootApplication
@EnableScheduling
public class Weather {

	/**
	 * Main method to start the application.
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(Weather.class, args);
	}

}
