import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import java.util.regex.*;

public class WeatherEngine {

    private static final String WEATHER_URL =
        "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/";

    private static final String NOMINATIM_URL =
        "https://nominatim.openstreetmap.org/search";

    public static class CityCandidate {
        public final String displayName;
        public final String lat;
        public final String lon;

        CityCandidate(String displayName, String lat, String lon) {
            this.displayName = displayName;
            this.lat         = lat;
            this.lon         = lon;
        }

        @Override public String toString() { return displayName; }
    }

    public static class WeatherData {
        public final String resolvedAddress;
        public final String conditions;
        public final double temp;
        public final double tempMax;
        public final double tempMin;
        public final double humidity;
        public final double precip;
        public final double windSpeed;
        public final double windDir;

        WeatherData(String resolvedAddress, String conditions,
                    double temp, double tempMax, double tempMin,
                    double humidity, double precip,
                    double windSpeed, double windDir) {
            this.resolvedAddress = resolvedAddress;
            this.conditions      = conditions;
            this.temp            = temp;
            this.tempMax         = tempMax;
            this.tempMin         = tempMin;
            this.humidity        = humidity;
            this.precip          = precip;
            this.windSpeed       = windSpeed;
            this.windDir         = windDir;
        }

        public String windDirLabel() {
            String[] dirs = {"N","NNE","NE","ENE","E","ESE","SE","SSE",
                             "S","SSO","SO","OSO","O","ONO","NO","NNO"};
            int idx = (int) Math.round(windDir / 22.5) % 16;
            return dirs[idx < 0 ? idx + 16 : idx];
        }
    }

    public static Map<String, String> loadDotEnv() {
        Map<String, String> env = new LinkedHashMap<>();
        Path path = Paths.get(".env");
        if (!Files.exists(path)) return env;
        try (BufferedReader br = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) continue;
                int eq = line.indexOf('=');
                if (eq < 1) continue;
                String key   = line.substring(0, eq).trim();
                String value = line.substring(eq + 1).trim();
                if ((value.startsWith("\"") && value.endsWith("\"")) ||
                    (value.startsWith("'")  && value.endsWith("'")))
                    value = value.substring(1, value.length() - 1);
                env.put(key, value);
            }
        } catch (IOException ignored) {}
        return env;
    }

    public static String loadApiKey() throws WeatherException {
        String key = loadDotEnv().get("WEATHER_API_KEY");
        if (key == null || key.isBlank())
            throw new WeatherException(
                "Chave de API nao encontrada.\n" +
                "Crie um arquivo .env na mesma pasta com:\n\n" +
                "WEATHER_API_KEY=sua_chave_aqui");
        return key;
    }

    public List<CityCandidate> searchCandidates(String query) throws WeatherException {
        try {
            String url = NOMINATIM_URL + "?q="
                + URLEncoder.encode(query, StandardCharsets.UTF_8)
                + "&format=json&limit=6&featuretype=city&addressdetails=0";

            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(10_000);
            conn.setReadTimeout(10_000);
            conn.setRequestProperty("User-Agent", "WeatherApp/1.0 (educational project)");
            conn.setRequestProperty("Accept-Language", "pt-BR,pt;q=0.9");

            if (conn.getResponseCode() != 200)
                throw new WeatherException("Falha ao buscar cidades (Nominatim HTTP "
                    + conn.getResponseCode() + ").");

            return parseCandidates(readStream(conn.getInputStream()));

        } catch (WeatherException e) {
            throw e;
        } catch (Exception e) {
            throw new WeatherException("Falha de conexao ao buscar cidades: " + e.getMessage(), e);
        }
    }

    private List<CityCandidate> parseCandidates(String json) {
        List<CityCandidate> list = new ArrayList<>();
        int pos = 0;
        while (true) {
            int start = json.indexOf('{', pos);
            if (start < 0) break;
            String obj = extractObjectAt(json, start);
            if (obj.isEmpty()) break;

            String name = extractField(obj, "display_name");
            String lat  = extractField(obj, "lat");
            String lon  = extractField(obj, "lon");

            if (!name.isEmpty() && !lat.isEmpty() && !lon.isEmpty())
                list.add(new CityCandidate(name, lat, lon));

            pos = start + obj.length();
        }
        return list;
    }

    public WeatherData fetchByCoords(String lat, String lon) throws WeatherException {
        String apiKey = loadApiKey();
        String coords = lat + "," + lon;
        String url = WEATHER_URL
            + URLEncoder.encode(coords, StandardCharsets.UTF_8)
            + "/today?unitGroup=metric&include=current%2Cdays"
            + "&key=" + URLEncoder.encode(apiKey, StandardCharsets.UTF_8)
            + "&contentType=json&lang=pt";
        return parse(request(url));
    }

    public WeatherData fetch(String city) throws WeatherException {
        String apiKey = loadApiKey();
        String url = WEATHER_URL
            + URLEncoder.encode(city, StandardCharsets.UTF_8)
            + "/today?unitGroup=metric&include=current%2Cdays"
            + "&key=" + URLEncoder.encode(apiKey, StandardCharsets.UTF_8)
            + "&contentType=json&lang=pt";
        return parse(request(url));
    }

    private String request(String urlStr) throws WeatherException {
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(urlStr).openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(12_000);
            conn.setReadTimeout(12_000);
            conn.setRequestProperty("Accept", "application/json");

            int code = conn.getResponseCode();
            if (code == 401) throw new WeatherException("Chave de API invalida ou expirada (HTTP 401).");
            if (code == 400) throw new WeatherException("Cidade nao encontrada. Verifique o nome e tente novamente.");
            if (code != 200) throw new WeatherException("Erro HTTP " + code + ": " + readStream(conn.getErrorStream()));

            return readStream(conn.getInputStream());
        } catch (WeatherException e) {
            throw e;
        } catch (Exception e) {
            throw new WeatherException("Falha de conexao: " + e.getMessage(), e);
        }
    }

    private WeatherData parse(String json) throws WeatherException {
        try {
            String current = extractBlock(json, "currentConditions");
            String day0    = extractFirstDay(json);
            return new WeatherData(
                extractField(json,    "resolvedAddress"),
                extractField(current, "conditions"),
                toDouble(extractField(current, "temp")),
                toDouble(extractField(day0,    "tempmax")),
                toDouble(extractField(day0,    "tempmin")),
                toDouble(extractField(current, "humidity")),
                toDouble(extractField(current, "precip")),
                toDouble(extractField(current, "windspeed")),
                toDouble(extractField(current, "winddir"))
            );
        } catch (Exception e) {
            throw new WeatherException("Erro ao interpretar resposta da API.", e);
        }
    }

    private String extractBlock(String json, String key) {
        int idx   = json.indexOf("\"" + key + "\"");
        if (idx < 0) return "{}";
        int start = json.indexOf('{', idx);
        if (start < 0) return "{}";
        return extractObjectAt(json, start);
    }

    private String extractFirstDay(String json) {
        int idx   = json.indexOf("\"days\"");
        if (idx < 0) return "{}";
        int arr   = json.indexOf('[', idx);
        if (arr < 0) return "{}";
        int start = json.indexOf('{', arr);
        if (start < 0) return "{}";
        return extractObjectAt(json, start);
    }

    private String extractObjectAt(String json, int start) {
        int depth = 0;
        for (int i = start; i < json.length(); i++) {
            char c = json.charAt(i);
            if (c == '{') depth++;
            else if (c == '}') { if (--depth == 0) return json.substring(start, i + 1); }
        }
        return "";
    }

    private String extractField(String json, String key) {
        Matcher m = Pattern.compile(
            "\"" + Pattern.quote(key) + "\"\\s*:\\s*(\"[^\"]*\"|[^,}\\]\\s]+)"
        ).matcher(json);
        if (!m.find()) return "";
        return m.group(1).trim().replaceAll("^\"|\"$", "");
    }

    private double toDouble(String s) {
        try { return Double.parseDouble(s); } catch (Exception e) { return 0; }
    }

    private String readStream(InputStream is) throws IOException {
        if (is == null) return "";
        try (BufferedReader r = new BufferedReader(
                new InputStreamReader(is, StandardCharsets.UTF_8))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = r.readLine()) != null) sb.append(line);
            return sb.toString();
        }
    }
}
