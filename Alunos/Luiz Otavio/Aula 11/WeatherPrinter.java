

/**
 * Exibe os dados de clima formatados no console.
 */
public class WeatherPrinter {

    private static final String[] WIND_DIRECTIONS = {
            "Norte", "Norte-Nordeste", "Nordeste", "Leste-Nordeste",
            "Leste", "Leste-Sudeste", "Sudeste", "Sul-Sudeste",
            "Sul", "Sul-Sudoeste", "Sudoeste", "Oeste-Sudoeste",
            "Oeste", "Oeste-Noroeste", "Noroeste", "Norte-Noroeste"
    };

    private WeatherPrinter() {}

    public static void print(WeatherData d) {
        String line = "─".repeat(48);
        System.out.println(line);
        System.out.printf("  %s%n", d.getResolvedAddress());
        System.out.println(line);

        System.out.printf("  Temperatura atual : %s°C%n",       fmt(d.getCurrentTemp(), 1));
        System.out.printf("  Máxima / Mínima   : %s°C / %s°C%n",
                fmt(d.getTempMax(), 1), fmt(d.getTempMin(), 1));
        System.out.printf("  Condição          : %s%n",         nvl(d.getCondition()));
        System.out.printf("  Humidade          : %s%%%n",       fmt(d.getHumidity(), 0));
        System.out.printf("  Precipitação      : %s%n",         precipLabel(d.getPrecip()));
        System.out.printf("  Vento             : %s km/h — %s%n",
                fmt(d.getWindSpeed(), 1), windDirLabel(d.getWindDir()));
        System.out.printf("  Visibilidade      : %s km%n",      fmt(d.getVisibility(), 1));
        System.out.println(line);
    }

    // -------------------------------------------------------------------------

    private static String fmt(Double value, int decimals) {
        if (value == null) return "—";
        return String.format("%." + decimals + "f", value);
    }

    private static String nvl(String value) {
        return (value == null || value.isBlank()) ? "—" : value;
    }

    private static String precipLabel(Double precip) {
        if (precip == null || precip == 0.0) return "Sem precipitação";
        return String.format("%.1f mm", precip);
    }

    private static String windDirLabel(Double degrees) {
        if (degrees == null) return "—";
        int idx = (int) Math.round(degrees / 22.5) % 16;
        return String.format("%s (%.0f°)", WIND_DIRECTIONS[idx], degrees);
    }
}
