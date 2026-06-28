import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class ServicoClima {

    private static final String API_KEY = ""; //retirada a chave para ninguem mexer

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

        String secaoAtual = JsonParser.isolarSecao(
                json,
                "\"currentConditions\":{",
                "}"
        );

        String secaoHoje = JsonParser.isolarSecao(
                json,
                "\"days\":[{",
                "}"
        );

        return new Clima(
                JsonParser.extrairValor(secaoAtual, "\"temp\":"),
                JsonParser.extrairValor(secaoHoje, "\"tempmax\":"),
                JsonParser.extrairValor(secaoHoje, "\"tempmin\":"),
                JsonParser.extrairValor(secaoAtual, "\"humidity\":"),
                JsonParser.extrairValorTexto(secaoAtual, "\"conditions\":"),
                JsonParser.extrairValor(secaoAtual, "\"precip\":"),
                JsonParser.extrairValor(secaoAtual, "\"windspeed\":"),
                JsonParser.extrairValor(secaoAtual, "\"winddir\":")
        );
    }
}