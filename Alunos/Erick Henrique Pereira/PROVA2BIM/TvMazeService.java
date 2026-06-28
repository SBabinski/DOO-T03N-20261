package servicos;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import objetos.Serie;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class TvMazeService {

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public TvMazeService() {
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    public List<Serie> buscarPorNome(String nome) {
        List<Serie> series = new ArrayList<>();
        try {
            String url = "https://api.tvmaze.com/search/shows?q=" + URLEncoder.encode(nome, StandardCharsets.UTF_8);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                JsonNode root = objectMapper.readTree(response.body());
                if (root.isArray()) {
                    for (JsonNode item : root) {
                        JsonNode show = item.path("show");
                        if (!show.isMissingNode()) {
                            int id = show.path("id").asInt();
                            String name = show.path("name").asText(null);
                            String status = show.path("status").asText(null);
                            String premiered = show.path("premiered").asText(null);
                            String ended = show.path("ended").asText(null);
                            
                            double rating = 0.0;
                            JsonNode ratingNode = show.path("rating");
                            if (!ratingNode.isMissingNode()) {
                                rating = ratingNode.path("average").asDouble(0.0);
                            }

                            String networkName = null;
                            JsonNode networkNode = show.path("network");
                            if (!networkNode.isMissingNode()) {
                                networkName = networkNode.path("name").asText(null);
                            }
                            if (networkName == null) {
                                JsonNode webChannelNode = show.path("webChannel");
                                if (!webChannelNode.isMissingNode()) {
                                    networkName = webChannelNode.path("name").asText(null);
                                }
                            }
                            if (networkName == null) {
                                networkName = "Desconhecida";
                            }

                            List<String> genresList = new ArrayList<>();
                            JsonNode genresNode = show.path("genres");
                            if (genresNode.isArray()) {
                                for (JsonNode g : genresNode) {
                                    genresList.add(g.asText());
                                }
                            }
                            String genres = String.join(", ", genresList);

                            String summary = show.path("summary").asText(null);
                            if (summary != null) {
                                summary = summary.replaceAll("<[^>]*>", ""); // remove HTML tags
                            }

                            String language = show.path("language").asText("Desconhecido");

                            Serie serie = new Serie(id, name, status, premiered, ended, rating, networkName, genres, summary, language);
                            series.add(serie);
                        }
                    }
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return series;
    }
}
