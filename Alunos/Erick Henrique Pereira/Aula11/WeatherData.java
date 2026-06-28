package com.weatherapp.model;

public class WeatherData {
    private double currentTemp; //temperatura atual
    private double maxTemp; //máxima de temperatura
    private double minTemp; //temperatura mínima
    private double humidity; //humidade
    private String conditions; //condição, tipo chuvoso e ensolarado
    private double precipitation; //chuva em mm
    private double windSpeed; //velocidade do vendo
    private double windDirection; //direção do vento
    public WeatherData(double currentTemp, double maxTemp, double minTemp, double humidity, String conditions,
            double precipitation, double windSpeed, double windDirection) {
        this.currentTemp = currentTemp;
        this.maxTemp = maxTemp;
        this.minTemp = minTemp;
        this.humidity = humidity;
        this.conditions = conditions;
        this.precipitation = precipitation;
        this.windSpeed = windSpeed;
        this.windDirection = windDirection;
    }
    public double getCurrentTemp() {
        return currentTemp;
    }
    public double getMaxTemp() {
        return maxTemp;
    }
    public double getMinTemp() {
        return minTemp;
    }
    public double getHumidity() {
        return humidity;
    }
    public String getConditions() {
        return conditions;
    }
    public double getPrecipitation() {
        return precipitation;
    }
    public double getWindSpeed() {
        return windSpeed;
    }
    public double getWindDirection() {
        return windDirection;
    }
}
