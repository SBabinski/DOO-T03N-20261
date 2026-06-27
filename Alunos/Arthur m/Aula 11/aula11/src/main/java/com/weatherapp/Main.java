package com.weatherapp;

import com.weatherapp.service.WeatherService;
import com.weatherapp.model.WeatherDTO;
import com.weatherapp.view.WeatherInterface;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);
        WeatherService service = new WeatherService();
        WeatherInterface ui = new WeatherInterface();

        System.out.print("Digite o nome da cidade: ");
        String city = scan.nextLine();
        scan.close();

        try {
            WeatherDTO weather = service.searchWeather(city);
            ui.displayWeather(weather, city);
        } catch (Exception e) {
            ui.displayError(e.getMessage());
        }
    }
}