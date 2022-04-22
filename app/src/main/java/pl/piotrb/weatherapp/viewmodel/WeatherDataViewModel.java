package pl.piotrb.weatherapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class WeatherDataViewModel extends ViewModel {
    private MutableLiveData<String> cityName = new MutableLiveData<>();
    public LiveData<String> getCityName() {
        return cityName;
    }
    public void setCityName(String cityName) {
        this.cityName.setValue(cityName);
    }
}
