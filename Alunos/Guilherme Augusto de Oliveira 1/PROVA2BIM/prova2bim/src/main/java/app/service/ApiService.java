package app.service;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import app.model.SearchResult;
import app.model.Serie;

public class ApiService {

    private static final String URL_BASE = "https://api.tvmaze.com/search/shows?q=";

    private final HttpClient client;
    private final Gson gson;

    public ApiService() {
        client = HttpClient.newHttpClient();
        gson = new Gson();
    }

public List<Serie> buscarSeries(String nome) throws IOException, InterruptedException {

    String busca = URLEncoder.encode(nome, StandardCharsets.UTF_8);

    HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(URL_BASE + busca))
            .GET()
            .build();

    HttpResponse<String> response =
            client.send(request, HttpResponse.BodyHandlers.ofString());

    Type tipoLista = new TypeToken<List<SearchResult>>(){}.getType();

    List<SearchResult> resultados =
            gson.fromJson(response.body(), tipoLista);

    return resultados.stream()
            .map(SearchResult::getShow)
            .toList();
}

}