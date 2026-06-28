public class WeatherData {
    private final String city;
    private final double temperature;
    private final double minTemperature;
    private final double maxTemperature; 
    private final double humidity;
    private final String conditions;
    private final double precipitation;
    private final double windSpeed;
    private final String windDirection;

    public WeatherData(
            String city,
            double temperature,
            double minTemperature,
            double maxTemperature,
            double humidity,
            String conditions,
            double precipitation,
            double windSpeed,
            String windDirection) {
        this.city = city;
        this.temperature = temperature;
        this.minTemperature = minTemperature;
        this.maxTemperature = maxTemperature;
        this.humidity = humidity;
        this.conditions = conditions;
        this.precipitation = precipitation;
        this.windSpeed = windSpeed;
        this.windDirection = windDirection;
    }

    // Getters

    public String getCity() {
        return city;
    }

    public double getTemperature() { 
        return temperature; 
    }

    public double getMinTemperature() {
        return minTemperature;
    }

    public double getMaxTemperature() {
        return maxTemperature;
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