package com.weatherapp.view;

import com.weatherapp.model.WeatherDTO;


public class WeatherInterface {

    public void displayWeather(WeatherDTO weather, String city) {
        System.out.println("------------------");
        System.out.println("Clima em: " + city.toUpperCase());
        System.out.println("------------------");
        System.out.println("Temperatura atual : " + weather.getCurrentTemp()   + " °C");
        System.out.println("Máxima do dia     : " + weather.getMaxTemp()       + " °C");
        System.out.println("Mínima do dia     : " + weather.getMinTemp()       + " °C");
        System.out.println("Humidade          : " + weather.getHumidity()      + " %");
        System.out.println("Condição          : " + weather.getConditions());
        System.out.println("Precipitação      : " + weather.getPrecipitation() + " mm");
        System.out.println("Velocidade do ar  : " + weather.getWindSpeed()     + " km/h");
        System.out.println("Direção do vento  : " + weather.getWindDirection());
        System.out.println("------------------");
    }

    public void displayError(String message) {
        System.out.println("Erro: " + message);
    }
}