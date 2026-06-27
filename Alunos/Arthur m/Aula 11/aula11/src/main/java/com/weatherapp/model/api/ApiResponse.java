package com.weatherapp.model.api;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/*
 * Model class representing the full response
 * returned by the Visual Crossing API.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiResponse {

    private CurrentConditions currentConditions;
    private List<Day> days;

    public CurrentConditions getCurrentConditions() { return currentConditions; }
    public List<Day> getDays()                      { return days; }

    public void setCurrentConditions(CurrentConditions currentConditions) {
        this.currentConditions = currentConditions;
    }

    public void setDays(List<Day> days) {
        this.days = days;
    }
}