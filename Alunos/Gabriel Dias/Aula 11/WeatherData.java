package com.weatherapp;

public class WeatherData {
    private String city;
    private double tempCurrent;
    private double tempMax;
    private double tempMin;
    private double humidity;
    private String conditions;
    private double precipitation;
    private double windSpeed;
    private double windDir;
    private String datetime;
    private String resolvedAddress;

    public WeatherData() {}

    // --- Getters e Setters ---

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public double getTempCurrent() { return tempCurrent; }
    public void setTempCurrent(double tempCurrent) { this.tempCurrent = tempCurrent; }

    public double getTempMax() { return tempMax; }
    public void setTempMax(double tempMax) { this.tempMax = tempMax; }

    public double getTempMin() { return tempMin; }
    public void setTempMin(double tempMin) { this.tempMin = tempMin; }

    public double getHumidity() { return humidity; }
    public void setHumidity(double humidity) { this.humidity = humidity; }

    public String getConditions() { return conditions; }
    public void setConditions(String conditions) { this.conditions = conditions; }

    public double getPrecipitation() { return precipitation; }
    public void setPrecipitation(double precipitation) { this.precipitation = precipitation; }

    public double getWindSpeed() { return windSpeed; }
    public void setWindSpeed(double windSpeed) { this.windSpeed = windSpeed; }

    public double getWindDir() { return windDir; }
    public void setWindDir(double windDir) { this.windDir = windDir; }

    public String getDatetime() { return datetime; }
    public void setDatetime(String datetime) { this.datetime = datetime; }

    public String getResolvedAddress() { return resolvedAddress; }
    public void setResolvedAddress(String resolvedAddress) { this.resolvedAddress = resolvedAddress; }

    /**
     * Converte graus (0-360) para nome da direção do vento.
     */
    public String getWindDirectionName() {
        if (windDir < 0) return "N/D";
        String[] dirs = {"N", "NNE", "NE", "ENE", "E", "ESE", "SE", "SSE",
                         "S", "SSO", "SO", "OSO", "O", "ONO", "NO", "NNO"};
        int idx = (int) Math.round(windDir / 22.5) % 16;
        return dirs[idx];
    }

    /**
     * Traduz a condição de tempo para português.
     */
    public String getConditionsPT() {
        if (conditions == null) return "N/D";
        String c = conditions.toLowerCase();
        if (c.contains("clear")) return "Céu Limpo";
        if (c.contains("sunny")) return "Ensolarado";
        if (c.contains("partly cloudy")) return "Parcialmente Nublado";
        if (c.contains("overcast")) return "Encoberto";
        if (c.contains("cloudy")) return "Nublado";
        if (c.contains("rain") && c.contains("thunder")) return "Tempestade com Chuva";
        if (c.contains("thunder")) return "Tempestade";
        if (c.contains("heavy rain")) return "Chuva Intensa";
        if (c.contains("rain")) return "Chuva";
        if (c.contains("drizzle")) return "Garoa";
        if (c.contains("snow")) return "Neve";
        if (c.contains("fog")) return "Névoa/Neblina";
        if (c.contains("mist")) return "Neblina";
        if (c.contains("wind")) return "Ventoso";
        return conditions;
    }
}
