import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class ClimaApi {

    private static final String URL_BASE =
            "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/";

    public Clima consultarClima(String cidade, String estado, String chave) throws Exception {

        validarEntradas(cidade, estado, chave);

        String localidade = montarLocalidade(cidade, estado);
        String urlCompleta = montarUrl(localidade, chave);

        String respostaJson = realizarRequisicao(urlCompleta);

        return extrairDados(respostaJson);
    }

    // Validações

    private void validarEntradas(String cidade, String estado, String chave) throws ClimaException {
        if (cidade == null || cidade.isBlank()) {
            throw new ClimaException("Informe o nome de uma cidade.");
        }

        if (chave == null || chave.isBlank()) {
            throw new ClimaException("A chave da API não pode estar vazia.");
        }

        if (!cidade.matches("^[a-zA-ZÀ-ÿ\\s]+$")) {
            throw new ClimaException("Nome de cidade inválido. Use apenas letras.");
        }

        if (estado != null && !estado.isBlank() && !estado.matches("^[a-zA-Z]{2}$")) {
            throw new ClimaException("Estado inválido. Use a sigla com 2 letras (ex: SP).");
        }
    }

    // Montagem da URL

    private String montarLocalidade(String cidade, String estado) {
        String localidade = cidade.trim();

        if (estado != null && !estado.isBlank()) {
            localidade += "," + estado.trim();
        }

        return URLEncoder.encode(localidade, StandardCharsets.UTF_8);
    }

    private String montarUrl(String localidade, String chave) {
        return URL_BASE
                + localidade
                + "/today"
                + "?unitGroup=metric"
                + "&include=current,days"
                + "&elements=temp,tempmax,tempmin,humidity,conditions,precip,windspeed,winddir"
                + "&key=" + chave
                + "&contentType=json";
    }

    // Requisição HTTP

    private String realizarRequisicao(String url) throws Exception {
        HttpClient httpClient = HttpClient.newHttpClient();

        HttpRequest requisicao = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> resposta = httpClient.send(
                requisicao,
                HttpResponse.BodyHandlers.ofString()
        );

        verificarStatusHttp(resposta.statusCode());

        return resposta.body();
    }

    private void verificarStatusHttp(int status) throws ClimaException {
        switch (status) {
            case 200 -> { /* sucesso, segue em frente */ }
            case 400 -> throw new ClimaException("Cidade não encontrada. Verifique o nome informado.");
            case 401 -> throw new ClimaException("Chave da API inválida ou expirada.");
            case 404 -> throw new ClimaException("Recurso não encontrado na API.");
            default  -> throw new ClimaException("Erro inesperado ao consultar a API. Código: " + status);
        }
    }

    // Extração dos dados do JSON

    private Clima extrairDados(String json) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode raiz = mapper.readTree(json);

        JsonNode condicaoAtual = raiz.get("currentConditions");
        JsonNode diaAtual = raiz.get("days").get(0);

        Clima clima = new Clima();

        clima.setTempAtual(condicaoAtual.get("temp").asDouble());
        clima.setTempMax(diaAtual.get("tempmax").asDouble());
        clima.setTempMin(diaAtual.get("tempmin").asDouble());
        clima.setUmidade(condicaoAtual.get("humidity").asDouble());
        clima.setDescricao(condicaoAtual.get("conditions").asText());
        clima.setPrecipitacao(diaAtual.get("precip").asDouble());
        clima.setVelocidadeVento(condicaoAtual.get("windspeed").asDouble());
        clima.setDirecaoVento(condicaoAtual.get("winddir").asDouble());

        return clima;
    }
}