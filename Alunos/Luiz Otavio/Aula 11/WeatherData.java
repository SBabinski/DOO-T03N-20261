
public class WeatherData {

    private final String resolvedAddress;
    private final Double currentTemp;
    private final Double tempMax;
    private final Double tempMin;
    private final Double humidity;
    private final String condition;
    private final Double precip;
    private final Double windSpeed;
    private final Double windDir;
    private final Double visibility;

    public WeatherData(
            String resolvedAddress,
            Double currentTemp,
            Double tempMax,
            Double tempMin,
            Double humidity,
            String condition,
            Double precip,
            Double windSpeed,
            Double windDir,
            Double visibility) {

        this.resolvedAddress = resolvedAddress;
        this.currentTemp     = currentTemp;
        this.tempMax         = tempMax;
        this.tempMin         = tempMin;
        this.humidity        = humidity;
        this.condition       = condition;
        this.precip          = precip;
        this.windSpeed       = windSpeed;
        this.windDir         = windDir;
        this.visibility      = visibility;
    }

    public String getResolvedAddress() { return resolvedAddress; }
    public Double getCurrentTemp()     { return currentTemp; }
    public Double getTempMax()         { return tempMax; }
    public Double getTempMin()         { return tempMin; }
    public Double getHumidity()        { return humidity; }
    public String getCondition()       { return condition; }
    public Double getPrecip()          { return precip; }
    public Double getWindSpeed()       { return windSpeed; }
    public Double getWindDir()         { return windDir; }
    public Double getVisibility()      { return visibility; }
}
