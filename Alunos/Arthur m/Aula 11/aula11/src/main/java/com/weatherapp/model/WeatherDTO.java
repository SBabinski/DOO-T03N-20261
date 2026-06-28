package com.weatherapp.model;


public class WeatherDTO {

    private double currentTemp;
    private double maxTemp;
    private double minTemp;
    private double humidity;
    private String conditions;
    private double precipitation;
    private double windSpeed;
    private String windDirection;

    public WeatherDTO(double currentTemp, double maxTemp, double minTemp,
                      double humidity, String conditions, double precipitation,
                      double windSpeed, String windDirection) {
        this.currentTemp   = currentTemp;
        this.maxTemp       = maxTemp;
        this.minTemp       = minTemp;
        this.humidity      = humidity;
        this.conditions    = conditions;
        this.precipitation = precipitation;
        this.windSpeed     = windSpeed;
        this.windDirection = windDirection;
    }

    public double getCurrentTemp()    { return currentTemp; }
    public double getMaxTemp()        { return maxTemp; }
    public double getMinTemp()        { return minTemp; }
    public double getHumidity()       { return humidity; }
    public String getConditions()     { return conditions; }
    public double getPrecipitation()  { return precipitation; }
    public double getWindSpeed()      { return windSpeed; }
    public String getWindDirection()  { return windDirection; }
}