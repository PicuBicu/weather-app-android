package pl.piotrb.weatherapp.strategy;

import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import pl.piotrb.weatherapp.model.DailyWeatherDataContext;
import pl.piotrb.weatherapp.model.WeeklyForecastDataContext;
import pl.piotrb.weatherapp.model.currentweatherdata.WeatherData;
import pl.piotrb.weatherapp.model.oncecallapi.WeeklyForecast;
import pl.piotrb.weatherapp.viewmodel.WeatherDataViewModel;

public class SharedPreferencesStrategy implements DataProviderStrategy {

    private final SharedPreferences sharedPreferences;
    private final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    public SharedPreferencesStrategy(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    @Override
    public void provideWeatherData(DailyWeatherDataContext context, WeatherDataViewModel viewModel) {
        String value = sharedPreferences.getString(context.getCityName(), "");
        WeatherData weatherData = gson.fromJson(value, WeatherData.class);
        if (weatherData == null) {
            viewModel.setWeatherDataError("No data for city " + context.getCityName());
        } else {
            viewModel.setWeatherData(weatherData);
        }
    }

    @Override
    public void provideWeatherForecast(WeeklyForecastDataContext context, WeatherDataViewModel viewModel) {
        String value = sharedPreferences.getString(context.getCityName(), "");
        WeeklyForecast weeklyForecast = gson.fromJson(value, WeeklyForecast.class);
        if (weeklyForecast == null) {
            viewModel.setWeeklyForecastError("No data for city " + context.getCityName());
        } else {
            viewModel.setWeeklyForecast(weeklyForecast);
        }
    }
}
