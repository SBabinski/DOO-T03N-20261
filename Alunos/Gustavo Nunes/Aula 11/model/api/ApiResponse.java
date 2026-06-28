/*
 * Represents the main response returned by the
 * Visual Crossing API.
 */

package model.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

// ignore everything that doesn't exist in the class
@JsonIgnoreProperties(ignoreUnknown = true)

public class ApiResponse {
    
    private CurrentConditions currentConditions;
    private List<Day> days;
    
    // Getters
    public CurrentConditions getCurrentConditions() {
        return currentConditions;
    }
    
    public List<Day> getDays() {
        return days;
    }
}
