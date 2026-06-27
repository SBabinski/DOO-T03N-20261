import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class ClimaService {

    private static final String API_KEY = "ESH66MVMCQAVRTGPG2C99EQ8A";

    public Clima buscarClima(String cidade) throws Exception {

        String cidadeFormatada =
                URLEncoder.encode(cidade, StandardCharsets.UTF_8);

        String url =
                "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/"
                        + cidadeFormatada
                        + "?unitGroup=metric&key="
                        + API_KEY
                        + "&contentType=json";

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request =
                HttpRequest.newBuilder()
                        .uri(URI.create(url))
                        .GET()
                        .build();

        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        String json = response.body();

        Clima clima = new Clima();

        clima.setTemperatura(
                JsonUtil.extrairNumero(json, "temp"));

        clima.setTemperaturaMaxima(
                JsonUtil.extrairNumero(json, "tempmax"));

        clima.setTemperaturaMinima(
                JsonUtil.extrairNumero(json, "tempmin"));

        clima.setUmidade(
                JsonUtil.extrairNumero(json, "humidity"));

        clima.setCondicao(
                JsonUtil.extrairTexto(json, "conditions"));

        clima.setPrecipitacao(
                JsonUtil.extrairNumero(json, "precip"));

        clima.setVelocidadeVento(
                JsonUtil.extrairNumero(json, "windspeed"));

        clima.setDirecaoVento(
                JsonUtil.extrairNumero(json, "winddir"));
                

        return clima;
    }
}