package com.weather.app;

import com.weather.core.model.WeatherData;
import com.weather.core.service.WeatherService;
import com.weather.core.service.WeatherServiceException;
import com.weather.api.OpenMeteoService;

import java.util.Scanner;

/**
 * Main application class for the Weather Tracking App
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("=== Weather Tracking App ===");

        // Create weather service (Open-Meteo doesn't require an API key)
        WeatherService weatherService = new OpenMeteoService();

        // Check if service is available
        if (!weatherService.isAvailable()) {
            System.err.println("Error: Weather service is not available. Please check your API key.");
            System.exit(1);
        }

        // Main application loop
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\nOptions:");
            System.out.println("1. Get weather for a city");
            System.out.println("2. Exit");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    getWeatherForCity(weatherService, scanner);
                    break;
                case "2":
                    running = false;
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }

        scanner.close();
    }

    private static void getWeatherForCity(WeatherService weatherService, Scanner scanner) {
        System.out.print("Enter city name: ");
        String city = scanner.nextLine().trim();

        if (city.isEmpty()) {
            System.out.println("City name cannot be empty.");
            return;
        }

        try {
            WeatherData weather = weatherService.getCurrentWeather(city);
            System.out.println("\n" + weather);
        } catch (WeatherServiceException e) {
            System.err.println("Error getting weather data: " + e.getMessage());
        }
    }
}