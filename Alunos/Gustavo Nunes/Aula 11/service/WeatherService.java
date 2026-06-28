/*
 * Service layer of the application.
 * Responsible for:
 * - Validating user input.
 * - Consuming the Visual Crossing API.
 * - Processing the JSON response.
 * - Converting API data into application objects.
 */
package service;

import config.EnvConfig;
import model.WeatherDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.api.ApiResponse;
import model.api.CurrentConditions;
import model.api.Day;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class WeatherService {
    
    private static final String API_KEY = EnvConfig.getApiKey();
    private static final String BASE_URL = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/";
    
    // Build the URL
    private String buildeURL(String city) {
        
        return BASE_URL +
                city + "/today?" +
                "key=" + API_KEY +
                "&include=current,days" +
                "&elements=temp,tempmax,tempmin,humidity,conditions,precip,windspeed,winddir" +
                "&unitGroup=metric&lang=pt";
        
    }
    
    // Convert degrees to directions
    private String convertWindDirection(double degrees) {
        
        if(degrees >= 337.5 || degrees < 22.5) {
            return "Norte";
        }
        
        if (degrees < 67.5) {
            return "Nordeste";
        }
        
        if (degrees < 112.5) {
            return "Leste";
        }
        
        if (degrees < 157.5) {
            return "Sudeste";
        }
        
        if (degrees < 202.5) {
            return "Sul";
        }
        
        if (degrees < 247.5) {
            return "Sudoeste";
        }
        
        if (degrees < 292.5) {
            return "Oeste";
        }
        
        return "Noroeste";
    }
    
    // Search for weather in a specific city
    public WeatherDTO searchWeather(String city) throws IOException, InterruptedException {
        
        // Remove extra space
        city = city.trim();
        
        // Validate city
        if(city == null || city.isBlank()) {
            throw new IllegalArgumentException(
                    "Digite uma cidade."
            );
        }
        
        // Build URL
        String url = buildeURL(city);
        
        // Create HttpClient
        HttpClient client = HttpClient.newHttpClient();
        
        // Create HttpRequest
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
        
        // Send request
        HttpResponse<String> response = client.send(
                request,
                HttpResponse.BodyHandlers.ofString());
        
        // Valid if city exist wos found
        if (response.statusCode() != 200) {
            throw new RuntimeException(
                    "Cidade não encontrada."
            );
        }
        
        // Read JSON
        ObjectMapper mapper = new ObjectMapper();
        
        // Convert JSON with Jackson
        ApiResponse apiResponse = mapper.readValue(
                response.body(),
                ApiResponse.class
        );
        
        // Create WeatherDTO
        CurrentConditions current = apiResponse.getCurrentConditions();
        Day today = apiResponse.getDays().get(0);
        WeatherDTO weatherDTO = new WeatherDTO(current.getTemp(),
                today.getTempmax(),
                today.getTempmin(),
                current.getHumidity(),
                current.getConditions(),
                today.getPrecip(),
                current.getWindspeed(),
                convertWindDirection(current.getWinddir())
                );
        
        // Return WeatherDTO
        return weatherDTO;
    }
}
