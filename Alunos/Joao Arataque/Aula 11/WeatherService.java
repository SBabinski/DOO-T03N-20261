package com.weatherapp.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.weatherapp.model.WeatherInfo;

public class WeatherService {

    private static final String API_KEY = "P9ZYC";

    public WeatherInfo getWeather(String city) throws Exception {

        String url =
                "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/"
                        + city
                        + "?unitGroup=metric&key="
                        + API_KEY
                        + "&contentType=json&lang=pt";

        HttpURLConnection connection =
                (HttpURLConnection) new URL(url).openConnection();

        connection.setRequestMethod("GET");

        if (connection.getResponseCode() != 200) {
            throw new Exception("Cidade não encontrada.");
        }

        BufferedReader reader =
                new BufferedReader(
                        new InputStreamReader(
                                connection.getInputStream()));

        StringBuilder response = new StringBuilder();

        String line;

        while ((line = reader.readLine()) != null) {
            response.append(line);
        }

        reader.close();

        ObjectMapper mapper = new ObjectMapper();

        JsonNode json = mapper.readTree(response.toString());

        JsonNode current = json.get("currentConditions");

        JsonNode today = json.get("days").get(0);

        return new WeatherInfo(
                current.get("temp").asDouble(),
                today.get("tempmax").asDouble(),
                today.get("tempmin").asDouble(),
                current.get("humidity").asDouble(),
                current.get("conditions").asText(),
                current.get("precip").asDouble(),
                current.get("windspeed").asDouble(),
                getWindDirection(
                        current.get("winddir").asDouble())
        );
    }

    private String getWindDirection(double degrees) {

        if (degrees >= 45 && degrees < 135) {
            return "Leste";
        }

        if (degrees >= 135 && degrees < 225) {
            return "Sul";
        }

        if (degrees >= 225 && degrees < 315) {
            return "Oeste";
        }

        return "Norte";
    }
}p