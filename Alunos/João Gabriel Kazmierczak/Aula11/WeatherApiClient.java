package com.weatherapp.api;

public class WeatherApiClient {

	public String buscarClima(String cidade) {
	
		String apiKey = System.getenv("WEATHER_API_KEY");
		
		if (apiKey == null || apiKey.isEmpty()) {
			System.out.println("Erro: A chave da API (WEATHER_API_KEY) nao foi configurada no sistema!");
			return null;
		}
		
		String cidadeCodificada = java.net.URLEncoder.encode(cidade, java.nio.charset.StandardCharsets.UTF_8);
		String urlCompleta = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/" + cidadeCodificada + "/?unitGroup=metric&key=" + apiKey;
		
		java.net.http.HttpClient cliente = java.net.http.HttpClient.newHttpClient();
		
		java.net.http.HttpRequest requisicao = java.net.http.HttpRequest.newBuilder()
				.uri(java.net.URI.create(urlCompleta))
				.GET()
				.build();
		
		try {
			java.net.http.HttpResponse<String> resposta = cliente.send(requisicao, java.net.http.HttpResponse.BodyHandlers.ofString());
			return resposta.body();
		} catch (Exception e) {
			System.out.println("Erro ao conectar na API: " + e.getMessage());
			return null;
		}
	}
}