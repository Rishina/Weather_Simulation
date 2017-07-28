/**
 * 
 */
package com.cba.weather.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.cba.weather.beans.HistoricalData;
import com.cba.weather.beans.Observations;
import com.cba.weather.beans.WeatherData;
import com.cba.weather.beans.WeatherDataSet;

/**
 * Class to calculate the weather parameters.
 * @author Rishina Valsalan
 *
 */
@Component
public class WeatherCalculationService {
	public static Logger logger = LoggerFactory.getLogger(WeatherCalculationService.class);
	
	/**
	 * On the weather dataset obtained for the last 14 days, apply drift method for forecasting to find the increase or
	 * decrease in the each weather parameter for each day. Get the average of the drifts and add it to the last day's 
	 * value.
	 * temperature.get(0) has the last day's temperature.
	 * The calculated values are set to the WeatherData and returned.
	 * @param weatherDataSet
	 * @param weatherData
	 * @return
	 */
	public WeatherData calculateDrift(WeatherDataSet weatherDataSet, WeatherData weatherData ){
		double pressureDrift=0;
		double temperatureDrift=0;
		double humidityDrift=0;
		 
		
		ArrayList<Double> temperature = weatherDataSet.getTemperature();
		ArrayList<Double> pressure = weatherDataSet.getPressure();
		ArrayList<Double> humidity = weatherDataSet.getHumidity();
		
		double temperatureBase = temperature.get(0);
		double pressureBase = pressure.get(0);
		double humidityBase = humidity.get(0);
		
		for(int i=0;i<temperature.size()-1; i++){
			temperatureDrift += temperature.get(i) - temperature.get(i+1);
			pressureDrift += pressure.get(i) - pressure.get(i+1);
			humidityDrift += humidity.get(i) - humidity.get(i+1);
		}
		weatherData.setTemperature(Math.round(temperatureBase + (temperatureDrift / temperature.size()) ) );
		weatherData.setPressure(Math.round(pressureBase + (pressureDrift/pressure.size()) ) );
		weatherData.setHumidity(Math.round(humidityBase + (humidityDrift/humidity.size()) ) );
		
		return weatherData;
	}
	
	/**
	 * Method to get weather condition.
	 * From the weather condition observation for last two weeks,calculate rainIndex, snow index and sunny index based 
	 * on the number of days that had conditions similar to rain, snow or clear.
	 * Based on the index value , classify the current weather condition.
	 * @param weatherDataSet
	 * @return
	 */
	public String getWeatherCondition(WeatherDataSet weatherDataSet){
		String weatherCondition="";
		int rainIndex = 0;
		int snowIndex = 0;
		int sunnyIndex = 0;
		
		Map<String,Integer> mapWeatherCondition = weatherDataSet.getWeatherCondition();
		for(Map.Entry<String, Integer> condition: mapWeatherCondition.entrySet()){
			
			if(condition.getKey().contains("Cloud")
					|| condition.getKey().contains("Thunder")
					|| condition.getKey().contains("Hail") ){
				rainIndex += condition.getValue();
			}else if(condition.getKey().contains("Snow")
					|| condition.getKey().contains("Freez") ){
				snowIndex += condition.getValue();
			}else{
				sunnyIndex += condition.getValue();
			}
		}
		
		weatherCondition = classifyWeatherCondition(sunnyIndex,rainIndex,snowIndex);
		return weatherCondition;
	}
	
	/**
	 * Based on the index value , classify the current weather condition.
	 * @param sunnyIndex
	 * @param rainIndex
	 * @param snowIndex
	 * @return
	 */
	public String classifyWeatherCondition(int sunnyIndex, int rainIndex, int snowIndex){
		String weatherCondition ="";
		
		if(sunnyIndex>= rainIndex){
			if(sunnyIndex>=snowIndex){
				weatherCondition = "Sunny";
			}else{
				weatherCondition ="Snow";
			}
		}else{
			if(rainIndex>=snowIndex){
				weatherCondition = "Rain";
			}else{
				weatherCondition ="Snow";
			}
		}
		return weatherCondition;
	}
	
	/**
	 * Parse and get the weather observation from the JSON returned by the API call.
	 * It has last 14 days data. This method gets the temperature , pressure, humidity and weather condition for the 
	 * same hour as current time on the last 14 days.
	 * For weather condition, the weather conditions are added to a map with the condition as the key.
	 * A repeat of a weather condition adds to the value for the already added key.
	 * All these observations are set in the WeatherDataSet.
	 * 
	 */
	public WeatherDataSet getWeatherObservations(Calendar currentDateTime, ArrayList<HistoricalData> arrayListHistoricalData){
		WeatherDataSet weatherDataSet = new WeatherDataSet();
		ArrayList<Double> temperature = new ArrayList<Double>();
		ArrayList<Double> pressure = new ArrayList<Double>();
		ArrayList<Double> humidity = new ArrayList<Double>();
		Map<String,Integer> weatherCondition = new HashMap<String,Integer>();
		
		for(HistoricalData historicalData: arrayListHistoricalData){
			if(historicalData.getHistory()!=null){
				ArrayList<Observations> arrayListObservations = historicalData.getHistory().getObservations();
				
				for(Observations observations : arrayListObservations){
					int hour = Integer.parseInt(observations.getDate().getHour());
					int minute = Integer.parseInt(observations.getDate().getMin());
					
					if(currentDateTime.get(Calendar.HOUR_OF_DAY) == hour){
						if(( (currentDateTime.get(Calendar.MINUTE) >=0 && currentDateTime.get(Calendar.MINUTE) <30)&& minute ==0 )
							|| (  minute == 30 && (currentDateTime.get(Calendar.MINUTE) >=30) ) ){
							temperature.add(Double.parseDouble(observations.getTemperature()) );
							pressure.add(Double.parseDouble(observations.getPressure()) );
							humidity.add(Double.parseDouble(observations.getHumidity()) );
							
							if (weatherCondition!=null && observations.getWeatherCondition()!=null && !observations.getWeatherCondition().isEmpty()){
								Integer weatherConditionCount = weatherCondition.get(observations.getWeatherCondition() );
								weatherCondition.put(observations.getWeatherCondition(), (weatherConditionCount==null)?1:weatherConditionCount+1);
							}
						}
					}
				}
				
				if(temperature.size()<=0 || pressure.size()<=0 || humidity.size()<=0){
					logger.error("Weather data not available in the API result for the city");
					return null;
				}
				else{
					weatherDataSet.setTemperature(temperature);
					weatherDataSet.setPressure(pressure);
					weatherDataSet.setHumidity(humidity);
					weatherDataSet.setWeatherCondition(weatherCondition);
				}
			}else{
				logger.debug("The API is not returning value since it has exceeded the rate plan calls");
			}
		}
		return weatherDataSet;
	}
	
}
