
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;


public class WeatherService {

    private static final String BASE_URL =
            "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/";

    private final String apiKey;

    public WeatherService(String apiKey) {
        this.apiKey = apiKey;
    }

    public WeatherData fetch(String location) throws WeatherException {
        String url = buildUrl(location);
        String json = doGet(url);
        return parse(json);
    }

    // -------------------------------------------------------------------------
    // URL
    // -------------------------------------------------------------------------

    private String buildUrl(String location) throws WeatherException {
        try {
            String encoded = URLEncoder.encode(location, StandardCharsets.UTF_8);
            return BASE_URL + encoded + "/today"
                    + "?unitGroup=metric"
                    + "&include=current,days"
                    + "&key=" + apiKey
                    + "&contentType=json";
        } catch (Exception e) {
            throw new WeatherException("Erro ao montar URL: " + e.getMessage(), e);
        }
    }

    // -------------------------------------------------------------------------
    // HTTP
    // -------------------------------------------------------------------------

    private String doGet(String url) throws WeatherException {
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) URI.create(url).toURL().openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(8_000);
            conn.setReadTimeout(10_000);
            conn.setRequestProperty("Accept", "application/json");

            int status = conn.getResponseCode();
            handleHttpError(status, conn);

            try (InputStream in = conn.getInputStream()) {
                return new String(in.readAllBytes(), StandardCharsets.UTF_8);
            }

        } catch (WeatherException e) {
            throw e;
        } catch (IOException e) {
            throw new WeatherException("Falha de conexão: " + e.getMessage(), e);
        } finally {
            if (conn != null) conn.disconnect();
        }
    }

    private void handleHttpError(int status, HttpURLConnection conn) throws WeatherException, IOException {
        if (status == 200) return;

        String body = "";
        try (InputStream err = conn.getErrorStream()) {
            if (err != null) body = new String(err.readAllBytes(), StandardCharsets.UTF_8);
        }

        switch (status) {
            case 400 -> throw new WeatherException("Requisição inválida. Verifique o nome da cidade.");
            case 401 -> throw new WeatherException("Chave de API inválida ou sem permissão.");
            case 404 -> throw new WeatherException("Localização não encontrada: tente outro nome ou use coordenadas.");
            case 429 -> throw new WeatherException("Limite de requisições atingido. Aguarde e tente novamente.");
            case 500 -> throw new WeatherException("Erro interno no servidor da API.");
            default  -> throw new WeatherException("Erro HTTP " + status + ": " + body.strip());
        }
    }

    // -------------------------------------------------------------------------
    // JSON parser manual (sem dependências externas)
    // -------------------------------------------------------------------------

    private WeatherData parse(String json) throws WeatherException {
        try {
            String resolvedAddress = extractString(json, "resolvedAddress");

            String currentBlock = extractBlock(json, "currentConditions");
            Double currentTemp = extractDouble(currentBlock, "temp");
            Double humidity    = extractDouble(currentBlock, "humidity");
            String condition   = extractString(currentBlock, "conditions");
            Double precip      = extractDouble(currentBlock, "precip");
            Double windSpeed   = extractDouble(currentBlock, "windspeed");
            Double windDir     = extractDouble(currentBlock, "winddir");
            Double visibility  = extractDouble(currentBlock, "visibility");

            String daysArray = extractArrayFirstElement(json, "days");
            Double tempMax = extractDouble(daysArray, "tempmax");
            Double tempMin = extractDouble(daysArray, "tempmin");

            // Fallback: precipitação do dia quando currentConditions não tiver
            if (precip == null) precip = extractDouble(daysArray, "precip");

            return new WeatherData(
                    resolvedAddress, currentTemp, tempMax, tempMin,
                    humidity, condition, precip, windSpeed, windDir, visibility);

        } catch (WeatherException e) {
            throw e;
        } catch (Exception e) {
            throw new WeatherException("Erro ao processar resposta JSON: " + e.getMessage(), e);
        }
    }

    // -------------------------------------------------------------------------
    // Helpers de parsing JSON — leves, sem regex complexa
    // -------------------------------------------------------------------------

    /** Extrai o valor de uma chave string: "key":"value" */
    private String extractString(String json, String key) {
        String search = "\"" + key + "\"";
        int idx = json.indexOf(search);
        if (idx < 0) return null;
        int colon = json.indexOf(':', idx + search.length());
        if (colon < 0) return null;
        int start = json.indexOf('"', colon + 1);
        if (start < 0) return null;
        int end = json.indexOf('"', start + 1);
        if (end < 0) return null;
        return json.substring(start + 1, end);
    }

    /** Extrai o valor de uma chave numérica: "key": 12.3 */
    private Double extractDouble(String json, String key) {
        if (json == null) return null;
        String search = "\"" + key + "\"";
        int idx = json.indexOf(search);
        if (idx < 0) return null;
        int colon = json.indexOf(':', idx + search.length());
        if (colon < 0) return null;
        int start = colon + 1;
        while (start < json.length() && json.charAt(start) == ' ') start++;
        if (json.charAt(start) == 'n') return null; // null
        int end = start;
        while (end < json.length() && ",}\n\r\t ".indexOf(json.charAt(end)) < 0) end++;
        try {
            return Double.parseDouble(json.substring(start, end).trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /** Extrai o conteúdo de um objeto: "key": { ... } */
    private String extractBlock(String json, String key) throws WeatherException {
        String search = "\"" + key + "\"";
        int idx = json.indexOf(search);
        if (idx < 0) throw new WeatherException("Campo '" + key + "' não encontrado na resposta.");
        int brace = json.indexOf('{', idx + search.length());
        if (brace < 0) throw new WeatherException("Objeto '" + key + "' mal formatado.");
        int depth = 0, i = brace;
        while (i < json.length()) {
            char c = json.charAt(i);
            if (c == '{') depth++;
            else if (c == '}') { depth--; if (depth == 0) return json.substring(brace, i + 1); }
            i++;
        }
        throw new WeatherException("Objeto '" + key + "' não fechado corretamente.");
    }

    /** Extrai o primeiro elemento do array: "key": [ { ... }, ... ] */
    private String extractArrayFirstElement(String json, String key) throws WeatherException {
        String search = "\"" + key + "\"";
        int idx = json.indexOf(search);
        if (idx < 0) return null;
        int bracket = json.indexOf('[', idx + search.length());
        if (bracket < 0) return null;
        int brace = json.indexOf('{', bracket);
        if (brace < 0) return null;
        int depth = 0, i = brace;
        while (i < json.length()) {
            char c = json.charAt(i);
            if (c == '{') depth++;
            else if (c == '}') { depth--; if (depth == 0) return json.substring(brace, i + 1); }
            i++;
        }
        return null;
    }
}
