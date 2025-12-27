package com.weather.api;

import com.weather.core.model.WeatherData;
import com.weather.core.service.WeatherService;
import com.weather.core.service.WeatherServiceException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

/**
 * Open-Meteo API implementation of WeatherService
 */
public class OpenMeteoService implements WeatherService {
    private static final String GEOCODING_BASE_URL = "https://geocoding-api.open-meteo.com/v1/search";
    private static final String WEATHER_BASE_URL = "https://api.open-meteo.com/v1/forecast";
    private static final int TIMEOUT_SECONDS = 10;

    private final HttpClient httpClient;

    public OpenMeteoService() {
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(TIMEOUT_SECONDS))
                .build();
    }

    @Override
    public WeatherData getCurrentWeather(String location) throws WeatherServiceException {
        try {
            // First, get coordinates for the location
            double[] coordinates = getCoordinates(location);

            // Then get weather data for those coordinates
            String url = String.format("%s?latitude=%.4f&longitude=%.4f&current_weather=true",
                                     WEATHER_BASE_URL, coordinates[0], coordinates[1]);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request,
                    HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new WeatherServiceException("API request failed with status: " + response.statusCode());
            }

            return parseWeatherData(response.body(), location);

        } catch (IOException | InterruptedException e) {
            throw new WeatherServiceException("Failed to fetch weather data", e);
        }
    }

    @Override
    public boolean isAvailable() {
        try {
            // Simple test request to check API availability
            String url = String.format("%s?latitude=51.5074&longitude=-0.1278&current_weather=true",
                                     WEATHER_BASE_URL);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request,
                    HttpResponse.BodyHandlers.ofString());

            return response.statusCode() == 200;

        } catch (Exception e) {
            return false;
        }
    }

    private double[] getCoordinates(String location) throws WeatherServiceException, IOException, InterruptedException {
        String encodedLocation = URLEncoder.encode(location, StandardCharsets.UTF_8);
        String url = String.format("%s?name=%s&count=1&language=en&format=json",
                                 GEOCODING_BASE_URL, encodedLocation);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request,
                HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new WeatherServiceException("Geocoding request failed with status: " + response.statusCode());
        }

        JsonObject json = JsonParser.parseString(response.body()).getAsJsonObject();
        if (!json.has("results") || json.getAsJsonArray("results").size() == 0) {
            throw new WeatherServiceException("Location not found: " + location);
        }

        JsonObject result = json.getAsJsonArray("results").get(0).getAsJsonObject();
        double latitude = result.get("latitude").getAsDouble();
        double longitude = result.get("longitude").getAsDouble();

        return new double[]{latitude, longitude};
    }

    private WeatherData parseWeatherData(String jsonResponse, String location) throws WeatherServiceException {
        try {
            JsonObject json = JsonParser.parseString(jsonResponse).getAsJsonObject();

            JsonObject currentWeather = json.getAsJsonObject("current_weather");
            double temperature = currentWeather.get("temperature").getAsDouble();
            double windspeed = currentWeather.get("windspeed").getAsDouble();
            int weathercode = currentWeather.get("weathercode").getAsInt();

            // Convert weather code to description
            String description = getWeatherDescription(weathercode);
            String icon = getWeatherIcon(weathercode);

            // Open-Meteo doesn't provide humidity in current_weather, so we'll use a default
            double humidity = 50.0; // Default humidity since Open-Meteo doesn't provide it in current_weather

            long timestamp = System.currentTimeMillis();

            return new WeatherData(location, temperature, humidity, description, icon, timestamp);

        } catch (Exception e) {
            throw new WeatherServiceException("Failed to parse weather data", e);
        }
    }

    private String getWeatherDescription(int weathercode) {
        switch (weathercode) {
            case 0: return "Clear sky";
            case 1: return "Mainly clear";
            case 2: return "Partly cloudy";
            case 3: return "Overcast";
            case 45: return "Fog";
            case 48: return "Depositing rime fog";
            case 51: return "Light drizzle";
            case 53: return "Moderate drizzle";
            case 55: return "Dense drizzle";
            case 56: return "Light freezing drizzle";
            case 57: return "Dense freezing drizzle";
            case 61: return "Slight rain";
            case 63: return "Moderate rain";
            case 65: return "Heavy rain";
            case 66: return "Light freezing rain";
            case 67: return "Heavy freezing rain";
            case 71: return "Slight snow fall";
            case 73: return "Moderate snow fall";
            case 75: return "Heavy snow fall";
            case 77: return "Snow grains";
            case 80: return "Slight rain showers";
            case 81: return "Moderate rain showers";
            case 82: return "Violent rain showers";
            case 85: return "Slight snow showers";
            case 86: return "Heavy snow showers";
            case 95: return "Thunderstorm";
            case 96: return "Thunderstorm with slight hail";
            case 99: return "Thunderstorm with heavy hail";
            default: return "Unknown";
        }
    }

    private String getWeatherIcon(int weathercode) {
        // Simplified icon mapping - you could expand this
        if (weathercode == 0 || weathercode == 1) return "01d"; // Clear
        if (weathercode >= 2 && weathercode <= 3) return "02d"; // Cloudy
        if (weathercode >= 45 && weathercode <= 48) return "50d"; // Fog
        if (weathercode >= 51 && weathercode <= 67) return "09d"; // Rain
        if (weathercode >= 71 && weathercode <= 86) return "13d"; // Snow
        if (weathercode >= 95) return "11d"; // Thunderstorm
        return "01d"; // Default
    }
}