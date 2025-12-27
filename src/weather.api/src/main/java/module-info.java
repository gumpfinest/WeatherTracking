module weather.api {
    requires weather.core;
    requires com.google.gson;
    requires java.net.http;

    exports com.weather.api;
}