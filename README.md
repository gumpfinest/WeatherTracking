# Weather Tracking App

A modularized weather tracking application built with Java modules and JavaFX GUI that fetches current weather data using the Open-Meteo API.

## Prerequisites

- Java 17 or higher
- Maven 3.6 or higher

## Setup

### 1. Build the Project

```bash
mvn clean compile
```

### 2. Run the Application

```bash
mvn javafx:run -pl src/weather.gui
```

## Features

- **Modular Architecture**: Built using Java Platform Module System (JPMS) with separate modules for:
  - `weather.core`: Core data models and service interfaces
  - `weather.api`: API integration with Open-Meteo
  - `weather.gui`: JavaFX graphical user interface
- **Free Weather API**: Uses Open-Meteo, a completely free weather API with no API key required
- **Modern GUI**: Beautiful JavaFX interface with gradient backgrounds and responsive design

## Usage

The GUI application provides an intuitive interface:

- Enter a city name in the text field
- Click the "🔍 Search" button to get current weather
- View results showing temperature, weather description, and humidity
- The interface includes loading states and error handling

## Project Structure

```
weather-tracking/
├── pom.xml                          # Parent POM
├── src/
│   ├── weather.core/                # Core module
│   │   ├── pom.xml
│   │   └── src/main/java/
│   │       ├── module-info.java
│   │       └── com/weather/core/
│   │           ├── model/WeatherData.java
│   │           └── service/
│   │               ├── WeatherService.java
│   │               └── WeatherServiceException.java
│   ├── weather.api/                 # API module
│   │   ├── pom.xml
│   │   └── src/main/java/
│   │       ├── module-info.java
│   │       └── com/weather/api/
│   │           └── OpenMeteoService.java
│   └── weather.gui/                 # GUI module
│       ├── pom.xml
│       └── src/main/java/
│           ├── module-info.java
│           └── com/weather/gui/
│               ├── WeatherApplication.java
│               └── WeatherController.java
│       └── src/main/resources/
│           └── com/weather/gui/
│               └── weather-view.fxml
```

This project is open source and available under the MIT License.