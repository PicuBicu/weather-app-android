package pl.piotrb.weatherapp.service;

import pl.piotrb.weatherapp.BuildConfig;
import pl.piotrb.weatherapp.model.currentweatherdata.WeatherData;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherDataService {
    @GET("/data/2.5/weather?appid=" + BuildConfig.API_KEY)
    Call<WeatherData> getWeatherData(@Query("q") String cityName, @Query("units") String units);
}
