import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class ClimaEngine {
//sem a api key por questoes de segurança
    private static final String API_KEY = "";

    public Clima buscarClima(String cidade)
            throws IOException, InterruptedException {

        String cidadeCodificada =
                URLEncoder.encode(cidade, StandardCharsets.UTF_8);

        String url = String.format(
                "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/%s?unitGroup=metric&key=%s&contentType=json",
                cidadeCodificada,
                API_KEY
        );

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new RuntimeException(
                    "Erro na API: " + response.statusCode());
        }

        String json = response.body();

        String secaoAtual = Json.isolarSecao(
                json,
                "\"currentConditions\":{",
                "}"
        );

        String secaoHoje = Json.isolarSecao(
                json,
                "\"days\":[{",
                "}"
        );

        return new Clima(
                Json.extrairValor(secaoAtual, "\"temp\":"),
                Json.extrairValor(secaoHoje, "\"tempmax\":"),
                Json.extrairValor(secaoHoje, "\"tempmin\":"),
                Json.extrairValor(secaoAtual, "\"humidity\":"),
                Json.extrairValorTexto(secaoAtual, "\"conditions\":"),
                Json.extrairValor(secaoAtual, "\"precip\":"),
                Json.extrairValor(secaoAtual, "\"windspeed\":"),
                Json.extrairValor(secaoAtual, "\"winddir\":")
        );
    }
}