package pl.piotrb.weatherapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import pl.piotrb.weatherapp.model.DailyWeatherDataContext;
import pl.piotrb.weatherapp.model.currentweatherdata.WeatherData;
import pl.piotrb.weatherapp.model.oncecallapi.WeeklyForecast;

public class WeatherDataViewModel extends ViewModel {

    private final MutableLiveData<DailyWeatherDataContext> dailyWeatherContextData = new MutableLiveData<>();
    private final MutableLiveData<WeatherData> weatherData = new MutableLiveData<>();
    private final MutableLiveData<String> weatherDataError = new MutableLiveData<>();
    private final MutableLiveData<WeeklyForecast> weeklyForecast = new MutableLiveData<>();
    private final MutableLiveData<String> weeklyForecastError = new MutableLiveData<>();

    public LiveData<DailyWeatherDataContext> getDailyWeatherContextData() {
        return dailyWeatherContextData;
    }

    public void setDailyWeatherContextData(DailyWeatherDataContext dailyWeatherContextData) {
        this.dailyWeatherContextData.setValue(dailyWeatherContextData);
    }

    public LiveData<String> getWeeklyForecastError() {
        return weeklyForecastError;
    }

    public void setWeeklyForecastError(String error) {
        weeklyForecastError.setValue(error);
    }

    public LiveData<WeeklyForecast> getWeeklyForecast() {
        return weeklyForecast;
    }

    public void setWeeklyForecast(WeeklyForecast weeklyForecast) {
        this.weeklyForecast.setValue(weeklyForecast);
    }

    public LiveData<String> getWeatherDataError() {
        return weatherDataError;
    }

    public void setWeatherDataError(String weatherDataError) {
        this.weatherDataError.setValue(weatherDataError);
    }

    public LiveData<WeatherData> getWeatherData() {
        return weatherData;
    }

    public void setWeatherData(WeatherData weatherData) {
        this.weatherData.setValue(weatherData);
    }
}
