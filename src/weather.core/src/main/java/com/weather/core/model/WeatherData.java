package com.weather.core.model;

/**
 * Represents weather data for a location
 */
public class WeatherData {
    private final String location;
    private final double temperature;
    private final double humidity;
    private final String description;
    private final String icon;
    private final long timestamp;

    public WeatherData(String location, double temperature, double humidity,
                      String description, String icon, long timestamp) {
        this.location = location;
        this.temperature = temperature;
        this.humidity = humidity;
        this.description = description;
        this.icon = icon;
        this.timestamp = timestamp;
    }

    public String getLocation() {
        return location;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getHumidity() {
        return humidity;
    }

    public String getDescription() {
        return description;
    }

    public String getIcon() {
        return icon;
    }

    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return String.format("Weather in %s: %.1f°C, %s (Humidity: %.1f%%)",
                           location, temperature, description, humidity);
    }
}