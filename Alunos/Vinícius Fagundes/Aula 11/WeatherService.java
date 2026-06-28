
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WeatherService {
    private static final String BASE_URL = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/";

    private static String getApiKey() throws Exception {
        java.util.Properties prop = new java.util.Properties();
        try (java.io.InputStream input = new java.io.FileInputStream("config.properties")) {
            prop.load(input);
            String key = prop.getProperty("api.key");
            if (key != null && !key.trim().isEmpty()) {
                return key.trim();
            }
        } catch (java.io.IOException ex) {
            // Arquivo não existe ou não pôde ser lido
        }

        // Solicita ao usuário via janela gráfica caso não encontre
        String key = javax.swing.JOptionPane.showInputDialog(null,
                "Chave de API não encontrada em 'config.properties'.\nPor favor, insira sua chave da Visual Crossing:",
                "Configuração da API Key",
                javax.swing.JOptionPane.QUESTION_MESSAGE);

        if (key != null && !key.trim().isEmpty()) {
            key = key.trim();
            try (java.io.OutputStream output = new java.io.FileOutputStream("config.properties")) {
                prop.setProperty("api.key", key);
                prop.store(output, "Configuracoes da API de Clima");
            } catch (java.io.IOException ioEx) {
                // Falha silenciosa ao tentar salvar o arquivo
            }
            return key;
        }

        throw new RuntimeException("Chave de API é obrigatória para buscar dados climáticos.");
    }

    public static WeatherData fetchWeather(String city) throws Exception {
        String apiKey = getApiKey();
        String encodedCity = URLEncoder.encode(city, StandardCharsets.UTF_8.toString());
        String urlString = BASE_URL + encodedCity + "?key=" + apiKey + "&unitGroup=metric&lang=pt&contentType=json";

        URL url = java.net.URI.create(urlString).toURL();
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(8000);
        conn.setReadTimeout(8000);

        int status = conn.getResponseCode();
        if (status != 200) {
            throw new RuntimeException("Erro ao buscar dados do clima. Código HTTP: " + status);
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        conn.disconnect();

        return parseJson(content.toString());
    }

    private static WeatherData parseJson(String json) {
        WeatherData data = new WeatherData();

        // 1. Extrair resolvedAddress
        data.resolvedAddress = extractStringField(json, "resolvedAddress");

        // 2. Extrair bloco currentConditions
        // Encontra o objeto currentConditions
        int currentConditionsIdx = json.indexOf("\"currentConditions\"");
        if (currentConditionsIdx != -1) {
            String currentBlock = json.substring(currentConditionsIdx);
            // Encontra o fim do bloco
            int closingBrace = currentBlock.indexOf("}");
            if (closingBrace != -1) {
                currentBlock = currentBlock.substring(0, closingBrace);
            }
            data.currentTemp = extractDoubleField(currentBlock, "temp");
            data.currentFeelsLike = extractDoubleField(currentBlock, "feelslike");
            data.currentHumidity = extractDoubleField(currentBlock, "humidity");
            data.currentWindSpeed = extractDoubleField(currentBlock, "windspeed");
            data.currentConditions = extractStringField(currentBlock, "conditions");
            data.currentIcon = extractStringField(currentBlock, "icon");
        }

        // 3. Extrair previsão dos próximos dias (days)
        int daysIdx = json.indexOf("\"days\"");
        if (daysIdx != -1) {
            String daysBlock = json.substring(daysIdx);

            Pattern datePattern = Pattern.compile("\"datetime\"\\s*:\\s*\"(\\d{4}-\\d{2}-\\d{2})\"");
            Matcher dateMatcher = datePattern.matcher(daysBlock);

            int count = 0;
            while (dateMatcher.find() && count < 6) {
                String date = dateMatcher.group(1);
                int start = dateMatcher.start();
                int end = Math.min(daysBlock.length(), start + 1000);
                String dayChunk = daysBlock.substring(start, end);

                int hoursIndex = dayChunk.indexOf("\"hours\"");
                if (hoursIndex != -1) {
                    dayChunk = dayChunk.substring(0, hoursIndex);
                }

                double tempmax = extractDoubleField(dayChunk, "tempmax");
                double tempmin = extractDoubleField(dayChunk, "tempmin");
                String conditions = extractStringField(dayChunk, "conditions");
                String icon = extractStringField(dayChunk, "icon");

                if (date != null) {
                    data.forecast.add(new WeatherData.ForecastDay(date, tempmax, tempmin, conditions, icon));
                    count++;
                }
            }
        }

        return data;
    }

    private static String extractStringField(String source, String fieldName) {
        Pattern pattern = Pattern.compile("\"" + fieldName + "\"\\s*:\\s*\"([^\"]+)\"");
        Matcher matcher = pattern.matcher(source);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    private static double extractDoubleField(String source, String fieldName) {
        Pattern pattern = Pattern.compile("\"" + fieldName + "\"\\s*:\\s*(-?\\d+\\.?\\d*)");
        Matcher matcher = pattern.matcher(source);
        if (matcher.find()) {
            try {
                return Double.parseDouble(matcher.group(1));
            } catch (NumberFormatException e) {
                return 0.0;
            }
        }
        return 0.0;
    }
}
