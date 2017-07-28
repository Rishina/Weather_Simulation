# WeatherSimulation

Application to generate fake weather feed for a game
 
The application takes a location.txt file as input.The file has json data for the locations of which weather needs 
to be calculated and the latitude, longitude and elevation. The format is :
{ "location": "Sydney","position": [-33.86,151.21,39]}
 
From this file, we parse and find the elevation type and latitude region of each of the locations. 
 
An API call is made to Wunderground - free weather API, to get historical data. The data for last 14 days is fetched 
for each of the location. 
 
Drift method is used to calculate the drift for temperature , pressure and humidity values to get the 
current values for temperature , pressure and humidity.
Based on the weather conditions for the last 14 days, an index value is calculated to find the chances of the possible 
current weather condition.

The calculated values are logged and are written out to a text file - Weather.Txt in \target\resources folder.

The Spring boot project executes a task scheduler every 30 seconds from the first run to get weather data from 10 cities.

## Algorithm
The application takes a location.txt file as input.The file has json data for the locations of which weather needs to be calculated and the latitude, longitude and elevation. The format is :
{ "location": "Sydney","position": [-33.86,151.21,39]}

### Temperature, Pressure and Relative Humidity Calculation ### <br/>
The drift method for forecasting is used to calculate the temperature , pressure and humidity.
The increase and decrease in the the values for the last 14 days during the same time of the day as the prediction time is averaged and the drift is added to the last day's value.<br/>
Yt = Yt-1 + ( (Yt-1 - Yt-2) + (Yt-2 - Yt-3) +....+ (Yt-13 - Yt-14) ) / no. of Ys

### Weather Condition Calculation ### <br/>
From the weather condition observation for last two weeks,calculate rainIndex, snow index and sunny index based on the number of days that had conditions similar to rain, snow or clear. Based on the highest index value , classify the current weather condition.


## Softwares Required

* [jdk1.8]
* [Maven 3.3.9] - Dependency Management
* [Eclipse Luna] - IDE 

## Installation steps.
Install JDK from http://www.oracle.com/technetwork/java/javase/downloads/index.html<br/>
Install maven from https://maven.apache.org/download.cgi<br/>
Install Eclipse from http://www.eclipse.org/downloads/eclipse-packages/<br/>

Open the project from eclipse as existing project from Eclipse and run the application.

## Steps to run the application and the testcases
Open the command prompt and change directory to the project folder and execute the below commands.
* mvn clean test - Build and run test cases
* mvn exec:java - Execute


## Sample Output
Sydney|-33.86,151.21,39.0|2017-07-28T06:56:55.569|Sunny|15.0|1025.0|43.0<br/>
Melbourne|-37.81,144.96,31.0|2017-07-28T06:56:55.569|Rain|9.0|1021.0|67.0<br/>
Canberra|-35.47,149.01,720.0|2017-07-28T06:56:55.569|Rain|4.0|1026.0|95.0<br/>
Darwin|-36.68,143.58,209.0|2017-07-28T06:56:55.569|Sunny|28.0|1015.0|45.0<br/>
Hobart|42.88,147.32,19.0|2017-07-28T06:56:55.569|Rain|9.0|1019.0|39.0<br/>
Adelaide|-34.66,138.68,59.0|2017-07-28T06:56:55.569|Rain|16.0|1016.0|42.0<br/>
NewCastle|32.92,151.78,9.0|2017-07-28T06:56:55.569|Sunny|16.0|1025.0|35.0<br/>
Wollongong|-34.42,150.89,5.0|2017-07-28T06:56:55.569|Sunny|15.0|1026.0|28.0<br/>


## Major classes used in the solution
WeatherReport.java - Scheduler<br/>
WeatherReportingServiceImpl.java - Methods for calculation

## Authors

* **Rishina Valsalan** 


