/*
 * Data Transfer Object of the application.
 * Stores only the weather information required
 * for display in the graphical interface.
 */

package model;

public class WeatherDTO {
    
    private final double temperature;
    private final double maxTemperature;
    private final double minTemperature;
    private final double humidity;
    private final String conditions;
    private final double precipitation;
    private final double windSpeed;
    private final String windDirection;
    
    public WeatherDTO(double temperature, double maxTemperature,
                      double minTemperature, double humidity,
                      String conditions, double precipitation,
                      double windSpeed, String windDirection) {
        
        this.temperature = temperature;
        this.maxTemperature = maxTemperature;
        this.minTemperature = minTemperature;
        this.humidity = humidity;
        this.conditions = conditions;
        this.precipitation = precipitation;
        this.windSpeed = windSpeed;
        this.windDirection = windDirection;
        
    }
    
    //getters
    public double getTemperature() {
        return temperature;
    }
    
    public double getMaxTemperature() {
        return maxTemperature;
    }
    
    public double getMinTemperature() {
        return minTemperature;
    }
    
    public double getHumidity() {
        return humidity;
    }
    
    public String getConditions() {
        return conditions;
    }
    
    public double getPrecipitation() {
        return precipitation;
    }
    
    public double getWindSpeed() {
        return windSpeed;
    }
    
    public String getWindDirection() {
        return windDirection;
    }
    
}
