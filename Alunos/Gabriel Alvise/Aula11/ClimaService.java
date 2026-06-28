import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class ClimaService {

    private static final String API_KEY = Config.getApiKey();

    private String obterValor(JSONObject obj, String campo) {

        if (!obj.has(campo) || obj.isNull(campo)) {
            return "Não informado";
        }

        return obj.get(campo).toString();
    }

    public Map<String, String> buscarClima(String cidade) throws Exception {

        String cidadeFormatada = URLEncoder.encode(cidade, StandardCharsets.UTF_8);

        String url =
                "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/"
                        + cidadeFormatada
                        + "?unitGroup=metric"
                        + "&key=" + API_KEY
                        + "&contentType=json";

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {

            String mensagem = "Erro ao consultar o clima.";

            if (response.statusCode() == 400) {
                mensagem = "Cidade não encontrada.";
            }

            throw new RuntimeException(mensagem);
        }

        JSONObject json = new JSONObject(response.body());

        JSONObject current = json.getJSONObject("currentConditions");

        JSONArray days = json.getJSONArray("days");

        JSONObject today = days.getJSONObject(0);

        Map<String, String> dados = new HashMap<>();

        dados.put("cidade", obterValor(json, "resolvedAddress"));

        dados.put("tempAtual", obterValor(current, "temp"));
        dados.put("tempMax", obterValor(today, "tempmax"));
        dados.put("tempMin", obterValor(today, "tempmin"));

        dados.put("umidade", obterValor(current, "humidity"));
        dados.put("condicao", obterValor(current, "conditions"));
        dados.put("precipitacao", obterValor(current, "precip"));

        dados.put("ventoVelocidade", obterValor(current, "windspeed"));
        dados.put("ventoDirecao", obterValor(current, "winddir"));

        return dados;
    }
}