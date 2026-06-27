package com.weatherapp.model.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/*
 * Model class representing the current weather conditions
 * returned by the Visual Crossing API.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrentConditions {

    private double temp;
    private double humidity;
    private String conditions;
    private double precip;
    private double windspeed;
    private double winddir;

    public double getTemp()        { return temp; }
    public double getHumidity()    { return humidity; }
    public String getConditions()  { return conditions; }
    public double getPrecip()      { return precip; }
    public double getWindspeed()   { return windspeed; }
    public double getWinddir()     { return winddir; }

    public void setTemp(double temp)              { this.temp = temp; }
    public void setHumidity(double humidity)      { this.humidity = humidity; }
    public void setConditions(String conditions)  { this.conditions = conditions; }
    public void setPrecip(double precip)          { this.precip = precip; }
    public void setWindspeed(double windspeed)    { this.windspeed = windspeed; }
    public void setWinddir(double winddir)        { this.winddir = winddir; }
}