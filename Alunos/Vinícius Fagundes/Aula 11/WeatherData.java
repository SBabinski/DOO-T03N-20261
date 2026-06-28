
import java.util.ArrayList;
import java.util.List;

public class WeatherData {
    public String resolvedAddress;
    public double currentTemp;
    public double currentFeelsLike;
    public double currentHumidity;
    public double currentWindSpeed;
    public String currentConditions;
    public String currentIcon;
    public List<ForecastDay> forecast = new ArrayList<>();

    public static class ForecastDay {
        public String date;
        public double tempMax;
        public double tempMin;
        public String conditions;
        public String icon;

        public ForecastDay(String date, double tempMax, double tempMin, String conditions, String icon) {
            this.date = date;
            this.tempMax = tempMax;
            this.tempMin = tempMin;
            this.conditions = conditions;
            this.icon = icon;
        }
    }
}
