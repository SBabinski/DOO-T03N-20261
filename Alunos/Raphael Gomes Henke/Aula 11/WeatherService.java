import java.io.FileInputStream;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class WeatherService {

    private String apiKey;

    public WeatherService() throws Exception {

        Properties properties = new Properties();

        properties.load(new FileInputStream("config.properties"));

        apiKey = properties.getProperty("API_KEY");
    }

    public WeatherData buscarClima(String cidade) throws Exception {

        String cidadeFormatada =
                URLEncoder.encode(cidade, StandardCharsets.UTF_8);

        String url =
                "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/"
                + cidadeFormatada
                + "?unitGroup=metric"
                + "&key="
                + apiKey
                + "&contentType=json";

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request =
                HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response =
                client.send(
                        request,
                        HttpResponse.BodyHandlers.ofString());

        JsonObject json =
                JsonParser.parseString(response.body())
                .getAsJsonObject();

        JsonObject atual =
                json.getAsJsonObject("currentConditions");

        JsonArray dias =
                json.getAsJsonArray("days");

        JsonObject hoje =
                dias.get(0).getAsJsonObject();

        double precipitacao = 0;
        double direcaoVento = 0;

        if (!atual.get("precip").isJsonNull()) {
            precipitacao = atual.get("precip").getAsDouble();
        }

        if (!atual.get("winddir").isJsonNull()) {
            direcaoVento = atual.get("winddir").getAsDouble();
        }

        WeatherData clima = new WeatherData(
                atual.get("temp").getAsDouble(),
                hoje.get("tempmax").getAsDouble(),
                hoje.get("tempmin").getAsDouble(),
                atual.get("humidity").getAsDouble(),
                atual.get("conditions").getAsString(),
                precipitacao,
                atual.get("windspeed").getAsDouble(),
                direcaoVento
        );

        return clima;
    }
}