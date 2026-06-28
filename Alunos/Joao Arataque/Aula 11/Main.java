package com.weatherapp;

import java.util.Scanner;

import com.weatherapp.model.WeatherInfo;
import com.weatherapp.service.WeatherService;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.print("Digite o nome da cidade: ");
        String city = scanner.nextLine();

        scanner.close();

        if (city.isBlank()) {
            System.out.println("Cidade inválida.");
            return;
        }

        WeatherService service = new WeatherService();

        try {

            WeatherInfo weather = service.getWeather(city);

            System.out.println("\n==================================");
            System.out.println("      CONSULTA CLIMÁTICA");
            System.out.println("==================================");

            System.out.println("Cidade: " + city);

            System.out.printf("Temperatura atual: %.1f °C%n",
                    weather.getCurrentTemperature());

            System.out.printf("Temperatura máxima: %.1f °C%n",
                    weather.getMaximumTemperature());

            System.out.printf("Temperatura mínima: %.1f °C%n",
                    weather.getMinimumTemperature());

            System.out.printf("Umidade do ar: %.1f%%%n",
                    weather.getHumidity());

            System.out.println("Condição do tempo: "
                    + weather.getCondition());

            System.out.printf("Precipitação: %.1f mm%n",
                    weather.getPrecipitation());

            System.out.printf("Velocidade do vento: %.1f km/h%n",
                    weather.getWindSpeed());

            System.out.println("Direção do vento: "
                    + weather.getWindDirection());

            System.out.println("==================================");

        } catch (Exception e) {

            System.out.println("Erro ao consultar dados climáticos.");
            System.out.println(e.getMessage());

        }
    }
}