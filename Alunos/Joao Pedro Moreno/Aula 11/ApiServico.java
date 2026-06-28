import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;
import java.net.URLEncoder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;

import java.nio.charset.StandardCharsets;

public class ApiServico {

    public Clima buscarClima(String cidade, String estado, String chaveApi) throws Exception {

        if (cidade.isBlank()) {
            throw new ClimaException("Informe uma cidade!");
        }

        if (chaveApi.isBlank()) {
            throw new ClimaException("Insira sua chave da API.");
        }

        if (!cidade.matches("^[a-zA-ZÀ-ÿ\\s]+$")) {
            throw new ClimaException("A cidade deve conter apenas letras.");
        }

        if (!estado.isBlank() && !estado.matches("^[a-zA-Z]{2}$")) {
            throw new ClimaException("O estado deve conter apenas duas letras.");
        }

        HttpClient cliente = HttpClient.newHttpClient();

        String lugar = cidade;
        if (!estado.isBlank()) {
            lugar += "," + estado;
        }

        lugar = URLEncoder.encode(lugar, StandardCharsets.UTF_8);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/"
                        + lugar + "/today" + "?unitGroup=metric" + "&include=current,days"
                        + "&elements=temp,tempmax,tempmin,humidity,conditions,precip,windspeed,winddir"
                        + "&key=" + chaveApi + "&contentType=json")).build();

        HttpResponse<String> response = cliente.send(request, HttpResponse.BodyHandlers.ofString());

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response.body());
        JsonNode current = root.get("currentConditions");
        JsonNode day     = root.get("days").get(0);

        Clima clima = new Clima(
        current.get("temp").asDouble(),
        day.get("tempmax").asDouble(),
        day.get("tempmin").asDouble(),
        current.get("humidity").asDouble(),
        current.get("conditions").asText(),
        day.get("precip").asDouble(),
        current.get("windspeed").asDouble(),
        current.get("winddir").asDouble()
        );

        return clima;
    }
}