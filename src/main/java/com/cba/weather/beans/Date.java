package com.cba.weather.beans;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Class to hold the parsed date values from API call.
 * @author Rishina Valsalan
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Date {
	private String pretty;
	private String year;
	private String mon;
	private String mday;
	private String hour;
	private String min;
	public String getPretty() {
		return pretty;
	}
	public void setPretty(String pretty) {
		this.pretty = pretty;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getMon() {
		return mon;
	}
	public void setMon(String mon) {
		this.mon = mon;
	}
	public String getMday() {
		return mday;
	}
	public void setMday(String mday) {
		this.mday = mday;
	}
	public String getHour() {
		return hour;
	}
	public void setHour(String hour) {
		this.hour = hour;
	}
	public String getMin() {
		return min;
	}
	public void setMin(String min) {
		this.min = min;
	}
	
	
}
