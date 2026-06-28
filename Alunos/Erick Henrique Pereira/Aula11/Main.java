package com.weatherapp;

import java.util.Scanner;
import com.weatherapp.api.WeatherApiClient;
import com.weatherapp.model.WeatherData;
import com.weatherapp.util.WeatherFormatter;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Digite o nome da cidade");
        String city = scan.nextLine(); 
        scan.close();

        WeatherApiClient client = new WeatherApiClient();
        WeatherFormatter formatter = new WeatherFormatter();
        
        try{
            WeatherData data = client.fetchWeather(city);
            formatter.display(data, city);
        } catch (Exception erouuu){
            System.out.println("Erro ao fazer uma busca de dados: " + erouuu.getMessage());
            
        }

    }
    
}
