package com.weatherapp.model;

public class WeatherInfo {

    private double currentTemperature;
    private double maximumTemperature;
    private double minimumTemperature;
    private double humidity;
    private String condition;
    private double precipitation;
    private double windSpeed;
    private String windDirection;

    public WeatherInfo(
            double currentTemperature,
            double maximumTemperature,
            double minimumTemperature,
            double humidity,
            String condition,
            double precipitation,
            double windSpeed,
            String windDirection) {

        this.currentTemperature = currentTemperature;
        this.maximumTemperature = maximumTemperature;
        this.minimumTemperature = minimumTemperature;
        this.humidity = humidity;
        this.condition = condition;
        this.precipitation = precipitation;
        this.windSpeed = windSpeed;
        this.windDirection = windDirection;
    }

    public double getCurrentTemperature() {
        return currentTemperature;
    }

    public double getMaximumTemperature() {
        return maximumTemperature;
    }

    public double getMinimumTemperature() {
        return minimumTemperature;
    }

    public double getHumidity() {
        return humidity;
    }

    public String getCondition() {
        return condition;
    }

    public double getPrecipitation() {
        return precipitation;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public String getWindDirection() {
        return windDirection;
    }
}