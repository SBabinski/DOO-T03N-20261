package com.weatherapp.util;

import com.weatherapp.model.WeatherData;

public class WeatherFormatter {
    public void display(WeatherData data, String city) {
        System.out.println("------------------");
        System.out.println("Clima em: "+city.toUpperCase());
        System.out.println("------------------");
        System.out.println("Temperatura atual "+data.getCurrentTemp());
        System.out.println("Máxima do dia "+data.getMaxTemp());
        System.out.println("Mínima temperatura do dia: "+data.getMinTemp());
        System.out.println("Humidade: "+data.getHumidity());
        System.out.println("Condição: "+data.getConditions());
        System.out.println("Precipitação: "+data.getPrecipitation());
        System.out.println("Velocidade do ar: "+data.getWindSpeed());
        System.out.println("Direção do vendo: "+data.getWindDirection());
        System.out.println("-------------------");
    }
}