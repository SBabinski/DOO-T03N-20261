package tv;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Api {

    private static final String URL_API =
            "https://api.tvmaze.com/search/shows?q=";

    public static List<Serie> buscarSeries(String nome) throws Exception {

        List<Serie> lista = new ArrayList<>();

        String endereco =
                URL_API + nome.replace(" ", "%20");

        URL url = new URL(endereco);

        HttpURLConnection conexao =
                (HttpURLConnection) url.openConnection();

        conexao.setRequestMethod("GET");

        conexao.setConnectTimeout(5000);
        conexao.setReadTimeout(5000);

        BufferedReader leitor =
                new BufferedReader(
                        new InputStreamReader(
                                conexao.getInputStream()));

        StringBuilder resposta =
                new StringBuilder();

        String linha;

        while ((linha = leitor.readLine()) != null) {

            resposta.append(linha);

        }

        leitor.close();

        ObjectMapper mapper =
                new ObjectMapper();

        JsonNode raiz =
                mapper.readTree(resposta.toString());

        for (JsonNode item : raiz) {

            JsonNode show = item.get("show");

            Serie serie = new Serie();

            serie.setNome(
                    show.path("name").asText());

            serie.setIdioma(
                    show.path("language").asText());

            serie.setStatus(
                    show.path("status").asText());

            serie.setDatadeEstréia(
                    show.path("premiered").asText());

            serie.setDataDeEnceramento(
                    show.path("ended").asText());

            if (!show.path("rating")
                    .path("average").isNull()) {

                serie.setNota(
                        show.path("rating")
                                .path("average")
                                .asDouble());

            }

            List<String> generos =
                    new ArrayList<>();

            for (JsonNode genero :
                    show.path("genres")) {

                generos.add(
                        genero.asText());

            }

            serie.setGenero(generos);

            if (!show.path("network")
                    .isMissingNode()) {

                serie.setNomeDaEmissora(

                        show.path("network")
                                .path("name")
                                .asText());

            } else {

                serie.setNomeDaEmissora(
                        "Não informada");

            }

            lista.add(serie);

        }

        return lista;

    }

}