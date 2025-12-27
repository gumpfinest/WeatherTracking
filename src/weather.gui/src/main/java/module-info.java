module weather.gui {
    requires weather.core;
    requires weather.api;
    requires javafx.controls;
    requires javafx.fxml;

    opens com.weather.gui to javafx.fxml;
    exports com.weather.gui;
}