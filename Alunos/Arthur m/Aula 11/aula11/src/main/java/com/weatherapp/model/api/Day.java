package com.weatherapp.model.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/*
 * Model class representing a day returned
 * by the Visual Crossing API.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Day {

    private double tempmax;
    private double tempmin;
    private double precip;

    public double getTempmax() { return tempmax; }
    public double getTempmin() { return tempmin; }
    public double getPrecip()  { return precip; }

    public void setTempmax(double tempmax) { this.tempmax = tempmax; }
    public void setTempmin(double tempmin) { this.tempmin = tempmin; }
    public void setPrecip(double precip)   { this.precip = precip; }
}