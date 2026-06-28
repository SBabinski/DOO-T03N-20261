package com.weatherapp;

import java.util.Scanner;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.weatherapp.api.WeatherApiClient;
import com.weatherapp.model.WeatherData;

public class Main {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);

		System.out.println("===BEM VINDO AO WEATHER APP===");
		System.out.println("Digite o nome da cidade que deseja consultar:");
		String cidade = scan.nextLine();

		WeatherApiClient clienteApi = new WeatherApiClient();
		String resultadoJson = clienteApi.buscarClima(cidade);

		if (resultadoJson != null) {
			try {
				ObjectMapper mapper = new ObjectMapper();
				JsonNode raiz = mapper.readTree(resultadoJson);

				double tempAtual = raiz.path("currentConditions").path("temp").asDouble();
				double umidade = raiz.path("currentConditions").path("humidity").asDouble();
				String condicoes = raiz.path("currentConditions").path("conditions").asText();

				JsonNode hoje = raiz.path("days").get(0);
				double tempMax = hoje.path("tempmax").asDouble();
				double tempMin = hoje.path("tempmin").asDouble();
				double precip = hoje.path("precip").asDouble();
				double velVento = hoje.path("windspeed").asDouble();
				double dirVento = hoje.path("winddir").asDouble();

				WeatherData clima = new WeatherData(tempAtual, tempMax, tempMin, umidade, condicoes, precip, velVento, dirVento);

				System.out.println("\n=======================================");
				System.out.println("   PREVISÃO DO TEMPO EM: " + cidade.toUpperCase());
				System.out.println("=======================================");
				System.out.printf("Temperatura Atual: %.1f°C\n", clima.getTempAtual());
				System.out.printf("Temperatura Máxima: %.1f°C\n", clima.getTempMaxima());
				System.out.printf("Temperatura Mínima: %.1f°C\n", clima.getTempMinima());
				System.out.printf("Umidade do Ar:      %.1f%%\n", clima.getUmidade());
				System.out.println("Condição do Tempo:  " + clima.getCondicao());
				System.out.printf("Precipitação:       %.1f mm\n", clima.getPrecipitacao());
				System.out.printf("Velocidade Vento:   %.1f km/h\n", clima.getVelocidadeVento());
				System.out.printf("Direção do Vento:   %.1f°\n", clima.getDirecaoVento());
				System.out.println("=======================================");

			} catch (Exception e) {
				System.out.println("Erro ao processar os dados com Jackson: " + e.getMessage());
			}
		} else {
			System.out.println("Não foi possível obter dados para a cidade informada.");
		}

		scan.close();
	}
}