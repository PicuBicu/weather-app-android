package pl.piotrb.weatherapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import pl.piotrb.weatherapp.model.currentweatherdata.WeatherData;

public class WeatherDataViewModel extends ViewModel {
    private MutableLiveData<WeatherData> weatherData = new MutableLiveData<>();
    private MutableLiveData<String> weatherDataError = new MutableLiveData<>();

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
