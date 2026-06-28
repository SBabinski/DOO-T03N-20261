package fag;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ApiTvMaze {

	// retornara lissta de seres de acordo com o nome pesquisado
	public List<Serie> buscarSeries(String nome) {

		try {

			if (nome == null || nome.isBlank()) {
				throw new Exception("Digite o nome da série.");
			}

			// criar cliente
			HttpClient client = HttpClient.newHttpClient();

			// transformara nomes com espacos compativeis pra url
			String nomeCodificado = URLEncoder.encode(nome, StandardCharsets.UTF_8);

			// cria url
			URI url = URI.create("https://api.tvmaze.com/search/shows?q=" + nomeCodificado);

			// cria a requisicao
			HttpRequest request = HttpRequest.newBuilder(url).GET().build();

// COMENTARIO PRA TESTE//
			
			System.out.println("Conectando...");
			// cria a response
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
			System.out.println("Resposta recebida!");
			
// COMENTARIO PRA TESTE//
			
			
			if (response.statusCode() != 200) {
				throw new Exception("Erro HTTP: " + response.statusCode());
			}

			// cria lista de series que trara a resposta do json, caso haja mais series com
			// msm nome
			List<Serie> series = new ArrayList<>();

			JsonArray resultados = JsonParser.parseString(response.body()).getAsJsonArray();

			for (int i = 0; i < resultados.size(); i++) {

				JsonObject resultado = resultados.get(i).getAsJsonObject();

				JsonObject show = resultado.getAsJsonObject("show");

				Serie serie = new Serie();

				// id
				if (show.has("id")) {
					serie.setId(show.get("id").getAsInt());
				}

				// nome
				if (show.has("name")) {
					serie.setNome(show.get("name").getAsString());
				}

				// idioma
				if (show.has("language") && !show.get("language").isJsonNull()) {
					serie.setIdioma(show.get("language").getAsString());
				}else {
					serie.setIdioma("Não informado");
				}

				// status
				if (show.has("status") && !show.get("status").isJsonNull()) {
					serie.setStatus(show.get("status").getAsString());
				}else {
				    serie.setStatus("Não informado");
				    }

				// estreia
				if (show.has("premiered") && !show.get("premiered").isJsonNull()) {

					serie.setDataInicio(show.get("premiered").getAsString());
				} else {
					serie.setDataInicio("Não informado");
				}

				// término
				if (show.has("ended") && !show.get("ended").isJsonNull()) {

					serie.setDataFim(show.get("ended").getAsString());
				} else {
					serie.setDataFim("Não informado");
				}

				// nota
				if (show.has("rating") && !show.get("rating").isJsonNull()) {

					JsonObject rating = show.getAsJsonObject("rating");

					if (rating.has("average") && !rating.get("average").isJsonNull()) {

						serie.setNotaGeral(rating.get("average").getAsDouble());
					} else {
						serie.setNotaGeral(0);
					}
				}

				// gêneros
				if (show.has("genres") &&
					    !show.get("genres").isJsonNull() &&
					    show.getAsJsonArray("genres").size() > 0) {

					JsonArray generos = show.getAsJsonArray("genres");

					for (int j = 0; j < generos.size(); j++) {

						serie.getGeneros().add(generos.get(j).getAsString());
					}
				}else {
					serie.getGeneros().add("Não informado");
				}

				// emissora normal
				if (show.has("network") && !show.get("network").isJsonNull()) {

					JsonObject network = show.getAsJsonObject("network");

					serie.setEmissora(network.get("name").getAsString());
					
				}else if (show.has("webChannel") && !show.get("webChannel").isJsonNull()) {//streaming

					JsonObject webChannel = show.getAsJsonObject("webChannel");

					serie.setEmissora(webChannel.get("name").getAsString());
				}else {
					serie.setEmissora("Não informado");
				}

				series.add(serie);
			}

			return series;

		} catch (Exception e) {
			System.out.println("Não foi possível conectar à API.");

			return new ArrayList<>();
		}
	}
}