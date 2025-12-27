package com.weather.core.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class WeatherDataTest {

    @Test
    void testWeatherDataCreation() {
        // Given
        String location = "London";
        double temperature = 15.5;
        double humidity = 65.0;
        String description = "clear sky";
        String icon = "01d";
        long timestamp = System.currentTimeMillis();

        // When
        WeatherData weatherData = new WeatherData(location, temperature, humidity,
                                                description, icon, timestamp);

        // Then
        assertEquals(location, weatherData.getLocation());
        assertEquals(temperature, weatherData.getTemperature());
        assertEquals(humidity, weatherData.getHumidity());
        assertEquals(description, weatherData.getDescription());
        assertEquals(icon, weatherData.getIcon());
        assertEquals(timestamp, weatherData.getTimestamp());
    }

    @Test
    void testToString() {
        // Given
        WeatherData weatherData = new WeatherData("Paris", 20.3, 70.0,
                                                "few clouds", "02d", 1234567890L);

        // When
        String result = weatherData.toString();

        // Then
        assertTrue(result.contains("Paris"));
        assertTrue(result.contains("20.3"));
        assertTrue(result.contains("few clouds"));
        assertTrue(result.contains("70.0"));
    }
}