/*
 * Represents the current weather conditions
 * returned by the API.
 */

package model.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

// ignore everything that doesn't exist in the class
@JsonIgnoreProperties(ignoreUnknown = true)

public class CurrentConditions {
    
    private double temp;
    private double humidity;
    private Double precip;
    private double windspeed;
    private double winddir;
    private String conditions;
    
    // Getters
    public double getTemp() {
        return temp;
    }
    
    public double getHumidity() {
        return humidity;
    }
    
    public Double getPrecip() {
        return precip;
    }
    
    public double getWindspeed() {
        return windspeed;
    }
    
    public double getWinddir() {
        return winddir;
    }
    
    public String getConditions() {
        return conditions;
    }
}
