/*
 * Represents daily weather data returned by
 * the API.
 */

package model.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

// ignore everything that doesn't exist in the class
@JsonIgnoreProperties(ignoreUnknown = true)

public class Day {
    
    private double tempmax;
    private double tempmin;
    private double precip;
    
    // Getters
    public double getTempmax() {
        return tempmax;
    }
    
    public double getTempmin() {
        return tempmin;
    }
    
    public double getPrecip() {
        return precip;
    }
}
