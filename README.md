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

- **Modular Architecture**: Built using Java Platform Module System (JPMS) with separate modules for:
  - `weather.core`: Core data models and service interfaces
  - `weather.api`: API integration with Open-Meteo
  - `weather.gui`: JavaFX graphical user interface
- **Free Weather API**: Uses Open-Meteo, a completely free weather API with no API key required

## Usage

- Enter a city name in the text field

This project is open source and available under the MIT License.