package com.weather.core.service;

import com.weather.core.model.WeatherData;

/**
 * Service interface for weather operations
 */
public interface WeatherService {
    /**
     * Get current weather data for a location
     * @param location the location (city name or coordinates)
     * @return WeatherData for the location
     * @throws WeatherServiceException if weather data cannot be retrieved
     */
    WeatherData getCurrentWeather(String location) throws WeatherServiceException;

    /**
     * Check if the service is available
     * @return true if service is available
     */
    boolean isAvailable();
}