package com.weatherapp.service;

import com.weatherapp.config.EnvConfig;
import com.weatherapp.model.WeatherDTO;
import com.weatherapp.model.api.ApiResponse;
import com.weatherapp.model.api.CurrentConditions;
import com.weatherapp.model.api.Day;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class WeatherService {

    private static final String API_KEY = EnvConfig.getApiKey();
    private static final String BASE_URL =
        "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/";

    private String buildURL(String city) {
        return BASE_URL +
               city + "/today?" +
               "key=" + API_KEY +
               "&include=current,days" +
               "&elements=temp,tempmax,tempmin,humidity,conditions,precip,windspeed,winddir" +
               "&unitGroup=metric&lang=pt";
    }

    private String convertWindDirection(double degrees) {
        if (degrees >= 337.5 || degrees < 22.5)  return "Norte";
        if (degrees < 67.5)                       return "Nordeste";
        if (degrees < 112.5)                      return "Leste";
        if (degrees < 157.5)                      return "Sudeste";
        if (degrees < 202.5)                      return "Sul";
        if (degrees < 247.5)                      return "Sudoeste";
        if (degrees < 292.5)                      return "Oeste";
        return "Noroeste";
    }

    public WeatherDTO searchWeather(String city) throws IOException, InterruptedException {

        city = city.trim();

        if (city.isBlank()) {
            throw new IllegalArgumentException("Digite uma cidade.");
        }

        String url = buildURL(city);

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = client.send(
                request,
                HttpResponse.BodyHandlers.ofString()
        );

        if (response.statusCode() != 200) {
            throw new RuntimeException("Cidade não encontrada.");
        }

        ObjectMapper mapper = new ObjectMapper();
        ApiResponse apiResponse = mapper.readValue(response.body(), ApiResponse.class);

        CurrentConditions current = apiResponse.getCurrentConditions();
        Day today = apiResponse.getDays().get(0);

        return new WeatherDTO(
                current.getTemp(),
                today.getTempmax(),
                today.getTempmin(),
                current.getHumidity(),
                current.getConditions(),
                today.getPrecip(),
                current.getWindspeed(),
                convertWindDirection(current.getWinddir())
        );
    }
}