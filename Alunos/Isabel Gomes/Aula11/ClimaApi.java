package fag;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ClimaApi {
	
	public Clima buscarClima(String cidade, String estado, String chaveApi) throws Exception {
		
		//excessoes
		if(cidade.isBlank()) {
			throw new ClimaException("\nInforme uma cidade!");
		}
		
		if(chaveApi.isBlank()) {
			throw new ClimaException("\nInsira sua chave api");
		}
		
		//impede de inserir numeros
		if (!cidade.matches("^[a-zA-ZÀ-ÿ\\s]+$")) {
		    throw new ClimaException(
		            "A cidade deve conter apenas letras.");
		}
		
		if (!estado.isBlank() &&
			!estado.matches("^[a-zA-Z]{2}$")) {
			    throw new ClimaException(
			            "O estado deve conter apenas letras.");
			}
		
		//criar cliente
		HttpClient cliente = HttpClient.newHttpClient();
		
		String lugar = cidade;
		
		if(!estado.isBlank()) {
			lugar += "," + estado;
		}
		
		//aqui vaui iguinorar dimbolos e acentos nas palavras
		lugar = URLEncoder.encode(
                lugar,
                StandardCharsets.UTF_8);
		
		//criarurl
		URI url = URI.create("https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/"
							+ lugar
							+ "/today"
							+ "?unitGroup=metric" 
							+ "&include=current,days"
							+ "&elements=temp,tempmax,tempmin,humidity,conditions,precip,windspeed,winddir"
							+ "&key=" + chaveApi 
							+ "&contentType=json");
	
		
		//criar request
		HttpRequest request = HttpRequest.newBuilder(url).GET().build();
		
		//criar response
		HttpResponse<String> response = cliente.send(request, HttpResponse.BodyHandlers.ofString());
		
		if(response.statusCode() == 400) {
		    throw new ClimaException("Cidade não encontrada ou inválida.");
		}
		
		if (response.statusCode() == 401) {
            throw new ClimaException("Chave da API inválida.");
        }

        if (response.statusCode() == 404) {
            throw new ClimaException("Cidade não encontrada.");
        }
        
		if(response.statusCode() != 200) {
			throw new ClimaException("\nErro HTTP: " + response.statusCode());
		}
		
		//extrair json;
		ObjectMapper mapper = new ObjectMapper();
		
		JsonNode root = mapper.readTree(response.body());
		
		Clima clima = new Clima();
		
		
		clima.setTemperatura(root.get("currentConditions")
				.get("temp").asDouble());
		
		clima.setTempMaxima(root.get("days")
				.get(0)
				.get("tempmax").asDouble());
		
		clima.setTempMinima(root.get("days")
				.get(0)
				.get("tempmin").asDouble());
		
		clima.setHumidade(root.get("currentConditions")
				.get("humidity").asDouble());
		
		clima.setCondicao(root.get("currentConditions")
				.get("conditions").asText());
		
		clima.setQtdChuva(root.get("days")
				.get(0)
				.get("precip").asDouble());
		
		clima.setVelVento(root.get("currentConditions")
				.get("windspeed").asDouble());
		
		clima.setDirVento(root.get("currentConditions")
				.get("winddir").asDouble());
		
		return clima;
		
	}
	
}
