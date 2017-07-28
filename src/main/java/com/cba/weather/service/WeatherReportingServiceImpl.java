
package com.cba.weather.service;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cba.weather.beans.HistoricalData;
import com.cba.weather.beans.WeatherData;
import com.cba.weather.beans.WeatherDataSet;
import com.cba.weather.constants.WeatherConstants;
import com.cba.weather.repository.WeatherReportingData;

/**
 * Service implementation class to get the position data and to calculate weather parameters and generate the feed.
 * @author Rishina Valsalan
 *
 */
@Service("weatherReportingService")
public class WeatherReportingServiceImpl implements WeatherReportingService{
	
	public static Logger logger = LoggerFactory.getLogger(WeatherReportingServiceImpl.class);
	
	@Autowired
	WeatherReportingData weatherReportingData;
	@Autowired
	WeatherCalculationService weatherCalculationService;
	
	/**
	 * Service method to get weather data
	 * 1)Calls getPositionData() to get the locations , their positions and elevations from the location.txt file
	 * 2)From the position data, loops through the arrayList of Locations ; For each location , gets historical data of 2 weeks.
	 * 3)Calculates the weather parameters - temperature, humidity, pressure.
	 * 4)Writes weather data in '|' delimited format to WeatherFeed.txt file at \target.
	 */
	public ArrayList<WeatherData> getWeather(Calendar currentDateTime){
		ArrayList<WeatherData> arrayListWeatherData = getPositionData();
		for(WeatherData weatherData: arrayListWeatherData){
			ArrayList<HistoricalData> arrayListHistoricalData = getHistoricalData(currentDateTime,weatherData.getLocation());
			weatherData = calculateWeatherParameters(currentDateTime,arrayListHistoricalData,weatherData);
		}
		generateFeed(arrayListWeatherData,currentDateTime);
		
		return arrayListWeatherData;
	}

	/**
	 * This method gets the locations and their latitude , longitude and elevation data from the location file.
	 */
	public ArrayList<WeatherData> getPositionData(){
		ArrayList<WeatherData> arrayListWeatherData = weatherReportingData.getPositionData();
		return arrayListWeatherData;
	}
	
	/**
	 * Method to get past 14 days weather data.
	 * The method gets the data from a weather API call.
	 */
	public ArrayList<HistoricalData> getHistoricalData(Calendar currentDateTime, String location){
		ArrayList<HistoricalData> arrayListHistoricalData = new ArrayList<HistoricalData>();
		arrayListHistoricalData = getAPIData(currentDateTime,location);
		return arrayListHistoricalData;
	}
	
	/**
	 * Calculates the weather parameters for a location.
	 * 1) Gets the weather observations for last 14 days.
	 * 2) Using the observations, calculates the drift in temperature , pressure and humidity.
	 * 3) Using the observations, classifies the weather condition as Rain, Sunny, or Snow.
	 * @param currentDateTime
	 * @param arrayListHistoricalData
	 * @param weatherData
	 * @return
	 */
	public WeatherData calculateWeatherParameters(Calendar currentDateTime, ArrayList<HistoricalData> arrayListHistoricalData, WeatherData weatherData){
		WeatherDataSet weatherDataSet = new WeatherDataSet();
		weatherDataSet = weatherCalculationService.getWeatherObservations(currentDateTime, arrayListHistoricalData);
		if(weatherDataSet !=null){
			weatherData = weatherCalculationService.calculateDrift(weatherDataSet, weatherData);
			weatherData.setWeatherCondition(weatherCalculationService.getWeatherCondition(weatherDataSet));
		}
		return weatherData;
		
	}
	
	/**
	 * A weather API  call is made  for getting the half hourly weather data  for the last 14 days.
	 * The API call needs the location and date to fetch data
	 * An arraylist of historical weather data is formed from all the 14 days data for the particular location.
	 * @param currentDateTime
	 * @param location
	 * @return
	 */
	public ArrayList<HistoricalData> getAPIData(Calendar currentDateTime, String location){
		
		ArrayList<HistoricalData> arrayListHistoricalData = new ArrayList<HistoricalData>();
				
		for(int i=1;i<=WeatherConstants.HISTORY_DAYS;i++){
			try{
				Calendar dateForHistoryData = GregorianCalendar.getInstance();
				dateForHistoryData.add(Calendar.DAY_OF_MONTH, -i);
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
				String date_yyyyMMdd = simpleDateFormat.format(dateForHistoryData.getTime());
				
				HistoricalData historicalData = callAPI(location,date_yyyyMMdd);
				
				arrayListHistoricalData.add(historicalData);
			}catch(Exception exception){
				logger.error("There was an exception while calling the weather API..."+exception.getMessage());
			}
		}
		return arrayListHistoricalData;
	}
	
	
	/**
	 * Wunderground weather API is used to call weather data for the date and location passed in the URL.
	 * The values needed to make this call are Wunderground's APP ID, location and date in the format yyyyMMdd.
	 * Jackson is used to parse the JSON returned by the API call to the HistoricalData class.
	 * @param location
	 * @param date_yyyyMMdd
	 * @return
	 */
	public HistoricalData callAPI(String location,String date_yyyyMMdd){
		RestTemplate restTemplate = new RestTemplate();
		HistoricalData historicalData = restTemplate.getForObject("http://api.wunderground.com/api/"+WeatherConstants.APP_ID+"/history_"+date_yyyyMMdd+"/q/AU/"+location+".json",HistoricalData.class);
		return historicalData;
	}
	
	
	/**
	 * This method gets the current time in ISO8601 date format for the feed and writes the delimiter formatted string, holding the weather data for 10 cities, to 
	 * a file in the Application folder.If the file is not present already, new file is created . Else, the
	 * file is updated with the feed from each run.
	 * 
	 * @param arrayListWeatherData 
	 * @param currentDateTime
	 */
	public void generateFeed(ArrayList<WeatherData> arrayListWeatherData, Calendar currentDateTime){
		
		String strCurrentDateTime = getISODate(currentDateTime);
		
		if(null!=arrayListWeatherData && arrayListWeatherData.size()>0){
			try{
				Path filePath = Paths.get(System.getProperty("user.dir")+"\\target\\WeatherFeed.txt");
				try(BufferedWriter writer = Files.newBufferedWriter(filePath,StandardOpenOption.CREATE,StandardOpenOption.APPEND)){
					for (WeatherData weatherData : arrayListWeatherData) {
						writer.write(weatherData.getLocation()
								+"|"+ weatherData.getLatitude()+","+weatherData.getLongitude()+","+weatherData.getElevation()
								+"|"+ strCurrentDateTime
								+"|"+ weatherData.getWeatherCondition()
								+"|"+ weatherData.getTemperature()
								+"|"+ weatherData.getPressure()
								+"|"+ weatherData.getHumidity());
						writer.write(System.lineSeparator());
					}
				}catch(IOException ioException){
					logger.debug("Could not write to file"+ioException.getMessage());
				}
			}catch(InvalidPathException invalidPathException){
				logger.debug("Could not find the file path");
			}
		}
		else{
			logger.debug("There is no data for generating the feed");
		}
	}
	
	/**
	 * This method gets the current time in ISO8601 date format
	 * @param currentDateTime
	 * @return
	 */
	public String getISODate(Calendar currentDateTime){
		DateTimeFormatter dateFormatter = DateTimeFormatter.ISO_DATE_TIME;
		LocalDateTime localDateTime = LocalDateTime.ofInstant((currentDateTime).toInstant(), ZoneId.of("UTC"));
		return (dateFormatter.format(localDateTime));
	}
	
}
