package service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.dtos.ShowDTO;
import model.dtos.TvMazeSearchDTO;
import model.entities.Serie;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class TvMazeAPIService {

    private static final String BASE_URL = "https://api.tvmaze.com/search/shows?q=";

    /// Performs the API query
    public String searchByName(String serie) throws IOException, InterruptedException {

        serie = serie.trim();

        String query = URLEncoder.encode(
                serie,
                StandardCharsets.UTF_8
        );

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + query))
                .GET()
                .build();

        HttpResponse<String> response = client.send(
                request,
                HttpResponse.BodyHandlers.ofString()
        );

        return response.body();

    }

    /// Processes the returned JSON
    public List<Serie> searchSeries(String name)
            throws IOException, InterruptedException {

        // Armazena o Json retornado
        String json = searchByName(name);

        Gson gson = new Gson();

        Type type =
                new TypeToken<List<TvMazeSearchDTO>>() {
                }.getType();

        // Converte JSON em TvMazeSearchDTO
        List<TvMazeSearchDTO> results =
                gson.fromJson(json, type);

        List<Serie> series = new ArrayList<>();

        // Converte TvMazeSearchDTO em Objetos Serie
        for (TvMazeSearchDTO result : results) {

            ShowDTO show = result.getShow();

            Serie serie = new Serie(show.getId());

            serie.setName(show.getName());
            serie.setLanguage(show.getLanguage());

            if (show.getGenres() != null) {
                serie.setGenres(
                        new ArrayList<>(show.getGenres())
                );
            }

            if (show.getRating() != null &&
                    show.getRating().getAverage() != null) {

                serie.setAverage(
                        show.getRating()
                                .getAverage()
                                .floatValue()
                );
            }

            serie.setStatus(show.getStatus());
            serie.setPremiered(show.getPremiered());
            serie.setEnded(show.getEnded());

            if (show.getNetwork() != null) {
                serie.setBroadcaster(
                        show.getNetwork().getName()
                );
            }

            series.add(serie);
        }

        // Retorna uma lista de series
        return series;

    }
}
