package com.weather.gui;

import com.weather.core.model.WeatherData;
import com.weather.core.service.WeatherService;
import com.weather.core.service.WeatherServiceException;
import com.weather.api.OpenMeteoService;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

/**
 * Controller class for the Weather GUI
 */
public class WeatherController {

    @FXML
    private TextField cityTextField;

    @FXML
    private Button searchButton;

    @FXML
    private VBox weatherDisplay;

    @FXML
    private Label locationLabel;

    @FXML
    private Label temperatureLabel;

    @FXML
    private Label descriptionLabel;

    @FXML
    private Label humidityLabel;

    @FXML
    private Label statusLabel;

    private WeatherService weatherService;

    @FXML
    public void initialize() {
        // Initialize the weather service
        weatherService = new OpenMeteoService();

        // Check if service is available
        if (!weatherService.isAvailable()) {
            statusLabel.setText("⚠️ Weather service is currently unavailable");
            statusLabel.setStyle("-fx-text-fill: #FF6B6B;");
            searchButton.setDisable(true);
        } else {
            statusLabel.setText("✅ Ready to search for weather!");
            statusLabel.setStyle("-fx-text-fill: #4CAF50;");
        }
    }

    @FXML
    private void getWeather() {
        String city = cityTextField.getText().trim();

        if (city.isEmpty()) {
            statusLabel.setText("⚠️ Please enter a city name");
            statusLabel.setStyle("-fx-text-fill: #FF6B6B;");
            return;
        }

        // Disable the search button and show loading state
        searchButton.setDisable(true);
        searchButton.setText("🔄 Loading...");
        statusLabel.setText("🔄 Fetching weather data...");
        statusLabel.setStyle("-fx-text-fill: #2196F3;");
        weatherDisplay.setVisible(false);

        // Run the weather fetch in a background thread to avoid blocking the UI
        Thread weatherThread = new Thread(() -> {
            try {
                WeatherData weather = weatherService.getCurrentWeather(city);

                // Update UI on JavaFX Application Thread
                Platform.runLater(() -> {
                    displayWeather(weather);
                    statusLabel.setText("✅ Weather data updated!");
                    statusLabel.setStyle("-fx-text-fill: #4CAF50;");
                });

            } catch (WeatherServiceException e) {
                Platform.runLater(() -> {
                    statusLabel.setText("❌ Error: " + e.getMessage());
                    statusLabel.setStyle("-fx-text-fill: #FF6B6B;");
                    weatherDisplay.setVisible(false);
                });
            } finally {
                // Re-enable the search button
                Platform.runLater(() -> {
                    searchButton.setDisable(false);
                    searchButton.setText("🔍 Search");
                });
            }
        });

        weatherThread.setDaemon(true);
        weatherThread.start();
    }

    private void displayWeather(WeatherData weather) {
        locationLabel.setText("📍 " + weather.getLocation());
        temperatureLabel.setText(String.format("%.1f°C", weather.getTemperature()));
        descriptionLabel.setText("🌤️ " + capitalizeFirstLetter(weather.getDescription()));
        humidityLabel.setText(String.format("💧 Humidity: %.1f%%", weather.getHumidity()));

        weatherDisplay.setVisible(true);
    }

    private String capitalizeFirstLetter(String text) {
        if (text == null || text.isEmpty()) {
            return text;
        }
        return text.substring(0, 1).toUpperCase() + text.substring(1).toLowerCase();
    }
}