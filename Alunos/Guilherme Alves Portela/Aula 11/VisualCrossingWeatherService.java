import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class VisualCrossingWeatherService implements WeatherService {

    private static final String API_KEY = "2T4RXZYXCJRZ6UJLSMFP3ECHU";
    private static final String BASE_URL = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/";
    
    private final HttpClient httpClient;
    // ObjectMapper do Jackson para parsear o JSON
    private final ObjectMapper objectMapper; 

    public VisualCrossingWeatherService() {
        // Inicializa o cliente HTTP nativo
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public WeatherData getWeather(String city) throws Exception {
        // 1. Codifica a cidade para URL (ex: "São Paulo" -> "S%C3%A3o+Paulo")
        String encodedCity = URLEncoder.encode(city, StandardCharsets.UTF_8);
        
        // 2. Montar a URL completa com a sua chave e o unitGroup=metric
        String urlCompleta = BASE_URL + encodedCity + "?unitGroup=metric&key=" + API_KEY + "&contentType=json";

        // 3. Criar a HttpRequest, HttpResponse e disparar com o httpClient
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(urlCompleta))
                .header("Accept", "application/json")
                .GET() // Método padrão, opcional explicitar
                .build();

        HttpResponse<String> response;

        try {
            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            throw e;
        }
        
        // 4. Pegar a String do JSON e usar o JsonNode do Jackson para extrair os campos
        JsonNode jsonNode = objectMapper.readTree(response.body());

        // Navega para o objeto de condições atuais
        JsonNode currentNode = jsonNode.path("currentConditions");
        
        // Navega para o primeiro dia do array 'days' (índice 0)
        JsonNode firstDayNode = jsonNode.path("days").get(0);
        
        // Extração dos dados 

        String cityName = jsonNode.path("address").asText(); // Pegando o nome da cidade do JSON

        double temperature = currentNode.path("temp").asDouble();
        double humidity    = currentNode.path("humidity").asDouble();
        String conditions  = currentNode.path("conditions").asText();
        double precipitation = currentNode.path("precip").asDouble();
        double windSpeed   = currentNode.path("windspeed").asDouble();

        //Nota: A API retorna a direção em graus (double/int). 
        // Atualmente pegando como Texto, mas pode ser necessário 
        // converter para um formato mais legível (N, NE, E, etc.) dependendo do que você deseja mostrar.
        String windDirection = currentNode.path("winddir").asText();

        // Extração das máximas e mínimas do dia
        double maxTemperature = firstDayNode.path("tempmax").asDouble();
        double minTemperature = firstDayNode.path("tempmin").asDouble();

        // 5. Retornar o new WeatherData
        return new WeatherData(cityName,
            temperature,
            minTemperature,
            maxTemperature,
            humidity,
            conditions,
            precipitation,
            windSpeed,
            windDirection
    );
    }
}