package pl.piotrb.weatherapp.repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import pl.piotrb.weatherapp.model.DailyWeatherDataContext;
import pl.piotrb.weatherapp.model.WeeklyForecastDataContext;
import pl.piotrb.weatherapp.strategy.DataProviderStrategy;
import pl.piotrb.weatherapp.strategy.SharedPreferencesStrategy;
import pl.piotrb.weatherapp.strategy.WeatherApiStrategy;
import pl.piotrb.weatherapp.viewmodel.WeatherDataViewModel;


public class WeatherDataRepository {

    private static WeatherDataRepository INSTANCE = null;

    private ConnectivityManager connectivityManager;
    private SharedPreferences sharedPreferences;

    private WeatherDataRepository() {

    }

    private WeatherDataRepository(ConnectivityManager connectivityManager, SharedPreferences sharedPreferences) {
        this.connectivityManager = connectivityManager;
        this.sharedPreferences = sharedPreferences;
    }

    public static WeatherDataRepository getInstance(ConnectivityManager connectivityManager, SharedPreferences sharedPreferences) {
        if (INSTANCE == null) {
            INSTANCE = new WeatherDataRepository(connectivityManager, sharedPreferences);
        }
        return INSTANCE;
    }

    private boolean isNetworkAvailable() {
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void getDailyWeatherData(DailyWeatherDataContext context, WeatherDataViewModel model) {
        DataProviderStrategy providerStrategy;
        if (isNetworkAvailable()) {
            providerStrategy = new WeatherApiStrategy();
        } else {
            providerStrategy = new SharedPreferencesStrategy(this.sharedPreferences);
        }
        providerStrategy.provideWeatherData(context, model);
    }

    public void getWeeklyForecast(WeeklyForecastDataContext context, WeatherDataViewModel model) {
        DataProviderStrategy providerStrategy;
        if (isNetworkAvailable()) {
            providerStrategy = new WeatherApiStrategy();
        } else {
            providerStrategy = new SharedPreferencesStrategy(this.sharedPreferences);
        }
        providerStrategy.provideWeatherForecast(context, model);
    }
}
