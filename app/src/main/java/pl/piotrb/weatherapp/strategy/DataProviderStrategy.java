package pl.piotrb.weatherapp.strategy;

import androidx.lifecycle.ViewModel;

import pl.piotrb.weatherapp.model.DailyWeatherDataContext;
import pl.piotrb.weatherapp.model.WeeklyForecastDataContext;
import pl.piotrb.weatherapp.viewmodel.WeatherDataViewModel;

public interface DataProviderStrategy {
    void provideWeatherData(DailyWeatherDataContext context, WeatherDataViewModel viewModel);
    void provideWeatherForecast(WeeklyForecastDataContext context, WeatherDataViewModel viewModel);
}
