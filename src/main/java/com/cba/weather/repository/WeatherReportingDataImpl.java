/**
 * 
 */
package com.cba.weather.repository;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.cba.weather.beans.WeatherData;

/**
 * Interface implementation to get the position data from the location.txt file in the resources folder
 * and parse the json data in the file to get the locations and their latitude, longitude, and elevation.
 * @author Rishina Valsalan
 *
 */
@Repository("weatherReportingData")
public class WeatherReportingDataImpl implements WeatherReportingData{
	public static Logger logger = LoggerFactory.getLogger(WeatherReportingDataImpl.class);
	
	/**
	 * Method to get the location data from the location.txt file in src\main\resources folder
	 * Calls method to parse the JSON
	 */
	public ArrayList<WeatherData> getPositionData(){
		ArrayList<WeatherData>  arrayListWeatherData= new ArrayList<WeatherData>();
		
		arrayListWeatherData = parseJSONData();
		return arrayListWeatherData;
	}
	
	/**
	 * Method to parse the JSON data to get the location, latitude, longitude and elevation.
	 * 1) Gets the file from src\main\resources\location.txt
	 * 2)Parses the JSON file and get the array of location and position data
	 * 3)Loops through the JSONArray to get location name, latitude, longitude and elevation for
	 * each of the locations.
	 * @return
	 */
	public ArrayList<WeatherData> parseJSONData(){
		ArrayList<WeatherData> arrayListWeatherData = new ArrayList<WeatherData>();
		
		try{
			File locationFile = new File(System.getProperty("user.dir")+"\\src\\main\\resources\\location.txt");
			JSONParser jsonParser = new JSONParser();
		
			JSONArray jsonArray =  (JSONArray)jsonParser.parse(new FileReader(locationFile));
			
			for(Object object: jsonArray){
				WeatherData weatherData = new WeatherData();
				JSONObject jsonObject = (JSONObject)object;
				
				weatherData.setLocation((String)jsonObject.get("location"));
				JSONArray position = (JSONArray)jsonObject.get("position");
				if(position.get(0)!=null){
					weatherData.setLatitude((Double)position.get(0));
				}
				if(position.get(1)!= null){
					weatherData.setLongitude((Double)position.get(1));
				}
				if(position.get(2)!=null){
					weatherData.setElevation((Long)position.get(2));
				}
				arrayListWeatherData.add(weatherData);
			}
			
		}catch (Exception exception){
			logger.error("Could not parse file");
			exception.printStackTrace();
		}
		
		return arrayListWeatherData;
	}
	
}
