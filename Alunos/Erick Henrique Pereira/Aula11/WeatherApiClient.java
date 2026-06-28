package com.weatherapp.api;

import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.weatherapp.model.WeatherData;
import java.io.InputStream;
import java.util.Properties;


public class WeatherApiClient {

    private static final String API_KEY = loadApiKey();
    private static final String BASE_URL = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/";

    public WeatherData fetchWeather(String city) throws Exception {
        String url = BASE_URL + city + "?unitGroup=metric&key=" + API_KEY + "&contentType=json&lang=pt";

        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();

        if (responseCode != HttpURLConnection.HTTP_OK) {
            throw new Exception("Erro na requisição: " + responseCode);
        }

        BufferedReader reader = new BufferedReader(
            new InputStreamReader(connection.getInputStream())
        );

        StringBuilder response = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            response.append(line);
        }

        reader.close();

        ObjectMapper mapper = new ObjectMapper();
        JsonNode json = mapper.readTree(response.toString());

        JsonNode currentConditions = json.get("currentConditions");
        JsonNode today = json.get("days").get(0);

        return new WeatherData(
            currentConditions.get("temp").asDouble(),
            today.get("tempmax").asDouble(),
            today.get("tempmin").asDouble(),
            currentConditions.get("humidity").asDouble(),
            currentConditions.get("conditions").asText(),
            currentConditions.get("precip").asDouble(),
            currentConditions.get("windspeed").asDouble(),
            currentConditions.get("winddir").asDouble()
        );
    }
    private static String loadApiKey() throws RuntimeException {
    try (InputStream input = WeatherApiClient.class
            .getClassLoader()
            .getResourceAsStream("config.properties")) {

        if (input == null) {
            throw new RuntimeException("Arquivo config.properties não encontrado!");
        }

        Properties properties = new Properties();
        properties.load(input);
        return properties.getProperty("API_KEY");

    } catch (Exception e) {
        throw new RuntimeException("Erro ao carregar config.properties: " + e.getMessage());
    }
}
}