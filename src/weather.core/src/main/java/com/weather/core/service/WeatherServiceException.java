package com.weather.core.service;

/**
 * Exception thrown when weather service operations fail
 */
public class WeatherServiceException extends Exception {
    public WeatherServiceException(String message) {
        super(message);
    }

    public WeatherServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}