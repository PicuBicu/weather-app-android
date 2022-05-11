package pl.piotrb.weatherapp.utils;

public class TemperatureConverter {
    public static double convertFromCelsiusToFahrenheit(double temperature) {
        return (temperature * 9 / 5) + 32;
    }
}
