# Weather Tracking App

A modularized weather tracking application built with Java modules that fetches current weather data using the Open-Meteo API.

## Features

- **Modular Architecture**: Built using Java Platform Module System (JPMS) with separate modules for:
  - `weather.core`: Core data models and service interfaces
  - `weather.api`: API integration with Open-Meteo
  - `weather.app`: Main console application
  - `weather.gui`: JavaFX graphical user interface
- **Free Weather API**: Uses Open-Meteo, a completely free weather API with no API key required
- **Multiple Interfaces**: Both console and GUI versions available
- **Modern UI**: Beautiful JavaFX interface with gradient backgrounds and responsive design

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
│   ├── weather.app/                 # Console application module
│   │   ├── pom.xml
│   │   └── src/main/java/
│   │       ├── module-info.java
│   │       └── com/weather/app/
│   │           └── Main.java
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

## API Response

The app displays:
- Location name
- Current temperature in Celsius
- Weather description (based on WMO weather codes)
- Humidity percentage (estimated, as Open-Meteo doesn't provide real-time humidity in current_weather endpoint)

## Testing

The application has been tested and successfully retrieves weather data from Open-Meteo API.

## Extending the Application

The modular design makes it easy to:
- Add new weather APIs
- Create GUI interfaces
- Add weather forecasting
- Implement caching
- Add multiple location tracking

## License

This project is open source and available under the MIT License.