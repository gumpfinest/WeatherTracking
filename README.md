# Weather Tracking App

A modularized weather tracking application built with Java modules that fetches current weather data using the Open-Meteo API.

## Prerequisites

- Java 17 or higher
- Maven 3.6 or higher

## Setup

### 1. No API Key Required!

Open-Meteo is completely free and doesn't require any API key or registration.

### 2. Build the Project

```bash
mvn clean compile
```

### 3. Run the Application

**Console Version:**
```bash
mvn exec:java -pl src/weather.app
```

**GUI Version:**
```bash
mvn javafx:run -pl src/weather.gui
```

## Usage

The application provides a simple menu:

1. **Get weather for a city**: Enter a city name to get current weather information
2. **Exit**: Quit the application

The app displays:
- Location name
- Current temperature in Celsius
- Weather description (based on WMO weather codes)
- Humidity percentage (estimated, as Open-Meteo doesn't provide real-time humidity in current_weather endpoint)

This project is open source and available under the MIT License.