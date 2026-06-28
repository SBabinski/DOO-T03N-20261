package com.weatherapp;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * Serviço de clima — Visual Crossing Timeline Weather API.
 *
 * A API Key é carregada em tempo de execução a partir de:
 *   1. Variável de ambiente:  WEATHER_API_KEY
 *   2. Arquivo .env           (mesmo diretório do JAR, ou diretório atual)
 *   3. Arquivo .env           na home do usuário (~/.env)
 *
 * O arquivo .env deve conter a linha:
 *   WEATHER_API_KEY=sua_chave_aqui
 *
 * NUNCA coloque a chave diretamente no código.
 * O arquivo .env está listado no .gitignore e nunca vai para o GitHub.
 */
public class WeatherService {

    private static final String KEY_NAME = "WEATHER_API_KEY";
    private static final String BASE_URL =
            "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/";

    // -------------------------------------------------------------------------
    //  Carregamento da API Key
    // -------------------------------------------------------------------------

    /**
     * Tenta carregar a API Key nas seguintes ordens de prioridade:
     *  1. Variável de ambiente (ideal para CI/CD ou ambientes containerizados)
     *  2. Arquivo .env no mesmo diretório do JAR
     *  3. Arquivo .env no diretório de trabalho atual
     *  4. Arquivo .env na home do usuário
     */
    private String loadApiKey() throws Exception {
        // 1. Variável de ambiente
        String fromEnv = System.getenv(KEY_NAME);
        if (fromEnv != null && !fromEnv.isBlank()) {
            return fromEnv.trim();
        }

        // 2-4. Tenta arquivos .env em três locais
        String[] candidates = {
            getJarDirectory() + "/WeatherApp/.env",          // ao lado do JAR
            System.getProperty("user.dir") + "/WeatherApp/.env",  // diretório atual
            System.getProperty("user.home") + "/WeatherApp/.env"  // home do usuário
        };

        for (String path : candidates) {
            String key = readKeyFromEnvFile(path);
            if (key != null) return key;
        }

        // Nenhuma fonte encontrou a chave
        throw new Exception(
            "API Key não encontrada!\n\n" +
            "Crie o arquivo  .env  ao lado do WeatherApp.jar com o conteúdo:\n" +
            "   WEATHER_API_KEY=sua_chave_aqui\n\n" +
            "Ou defina a variável de ambiente:\n" +
            "   " + KEY_NAME + "=sua_chave_aqui\n\n" +
            "Obtenha sua chave gratuita em:\n" +
            "   https://www.visualcrossing.com/weather-api"
        );
    }

    /** Lê WEATHER_API_KEY de um arquivo .env no formato  CHAVE=VALOR. */
    private String readKeyFromEnvFile(String filePath) {
        try (FileInputStream fis = new FileInputStream(filePath)) {
            Properties props = new Properties();
            props.load(fis);
            String value = props.getProperty(KEY_NAME);
            if (value != null && !value.isBlank()) {
                return value.trim();
            }
        } catch (IOException ignored) {
            // Arquivo não existe ou não pôde ser lido — tenta o próximo
        }
        return null;
    }

    /** Retorna o diretório onde o JAR está sendo executado. */
    private String getJarDirectory() {
        try {
            Path jarPath = Paths.get(
                WeatherService.class.getProtectionDomain()
                    .getCodeSource().getLocation().toURI()
            );
            Path parent = jarPath.getParent();
            return (parent != null) ? parent.toString() : ".";
        } catch (Exception e) {
            return ".";
        }
    }

    // -------------------------------------------------------------------------
    //  Consulta à API
    // -------------------------------------------------------------------------

    /**
     * Busca clima atual + resumo do dia para a localização informada.
     *
     * @param location "Cidade, Estado, País"
     * @return WeatherData preenchido
     */
    public WeatherData fetchWeather(String location) throws Exception {
        String apiKey = loadApiKey();

        String encodedLocation = URLEncoder.encode(location, StandardCharsets.UTF_8);

        String urlStr = BASE_URL + encodedLocation + "/today"
                + "?unitGroup=metric"
                + "&include=current%2Cdays"
                + "&lang=pt"
                + "&key=" + apiKey
                + "&contentType=json";

        HttpURLConnection conn = null;
        try {
            URL url = new URL(urlStr);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(10_000);
            conn.setReadTimeout(10_000);
            conn.setRequestProperty("Accept", "application/json");

            int status = conn.getResponseCode();
            if (status == 401) throw new Exception("API Key inválida ou sem permissão (HTTP 401).");
            if (status == 429) throw new Exception("Limite de consultas atingido (HTTP 429). Tente mais tarde.");
            if (status != 200) throw new Exception("Erro ao consultar a API (HTTP " + status + ").");

            StringBuilder sb = new StringBuilder();
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                String line;
                while ((line = br.readLine()) != null) sb.append(line);
            }

            return parseJson(sb.toString(), location);

        } finally {
            if (conn != null) conn.disconnect();
        }
    }

    // -------------------------------------------------------------------------
    //  Parser JSON (sem dependências externas)
    // -------------------------------------------------------------------------

    private WeatherData parseJson(String json, String location) throws Exception {
        WeatherData data = new WeatherData();
        data.setCity(location);
        data.setResolvedAddress(extractString(json, "resolvedAddress"));

        int curStart = json.indexOf("\"currentConditions\"");
        if (curStart == -1)
            throw new Exception("Resposta inesperada: 'currentConditions' não encontrado.");

        String currentBlock = extractBlock(json, json.indexOf('{', curStart));
        data.setTempCurrent(extractDouble(currentBlock, "temp"));
        data.setHumidity(extractDouble(currentBlock, "humidity"));
        data.setPrecipitation(extractDouble(currentBlock, "precip"));
        data.setWindSpeed(extractDouble(currentBlock, "windspeed"));
        data.setWindDir(extractDouble(currentBlock, "winddir"));
        data.setConditions(extractString(currentBlock, "conditions"));
        data.setDatetime(extractString(currentBlock, "datetime"));

        int daysStart = json.indexOf("\"days\"");
        if (daysStart != -1) {
            int firstObj = json.indexOf('{', json.indexOf('[', daysStart));
            if (firstObj != -1) {
                String dayBlock = extractBlock(json, firstObj);
                data.setTempMax(extractDouble(dayBlock, "tempmax"));
                data.setTempMin(extractDouble(dayBlock, "tempmin"));
                if (data.getConditions() == null || data.getConditions().isEmpty())
                    data.setConditions(extractString(dayBlock, "conditions"));
            }
        }
        return data;
    }

    private String extractBlock(String json, int openBrace) {
        int depth = 0;
        StringBuilder sb = new StringBuilder();
        for (int i = openBrace; i < json.length(); i++) {
            char c = json.charAt(i);
            if (c == '{') depth++;
            else if (c == '}') depth--;
            sb.append(c);
            if (depth == 0) break;
        }
        return sb.toString();
    }

    private String extractString(String json, String key) {
        String search = "\"" + key + "\"";
        int idx = json.indexOf(search);
        if (idx == -1) return "";
        int colon = json.indexOf(':', idx);
        if (colon == -1) return "";
        int q1 = json.indexOf('"', colon + 1);
        if (q1 == -1) return "";
        int q2 = json.indexOf('"', q1 + 1);
        if (q2 == -1) return "";
        return json.substring(q1 + 1, q2);
    }

    private double extractDouble(String json, String key) {
        String search = "\"" + key + "\"";
        int idx = json.indexOf(search);
        if (idx == -1) return 0.0;
        int colon = json.indexOf(':', idx);
        if (colon == -1) return 0.0;
        int start = colon + 1;
        while (start < json.length() && Character.isWhitespace(json.charAt(start))) start++;
        int end = start;
        while (end < json.length() &&
               (Character.isDigit(json.charAt(end)) || json.charAt(end) == '.' || json.charAt(end) == '-'))
            end++;
        if (start == end) return 0.0;
        try { return Double.parseDouble(json.substring(start, end)); }
        catch (NumberFormatException e) { return 0.0; }
    }
}
