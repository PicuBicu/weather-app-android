package pl.piotrb.weatherapp.service;

import pl.piotrb.weatherapp.BuildConfig;
import pl.piotrb.weatherapp.model.currentweatherdata.WeatherData;
import pl.piotrb.weatherapp.model.oncecallapi.WeeklyForecast;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherDataService {
    @GET("/data/2.5/weather?units=metric&appid=" + BuildConfig.API_KEY)
    Call<WeatherData> getWeatherData(@Query("q") String cityName);
    @GET("/data/2.5/onecall?units=metric&exclude=alerts,minutely,hourly&appid=" + BuildConfig.API_KEY)
    Call<WeeklyForecast> getWeeklyForecast(@Query("lat") Double latitude, @Query("lon") Double longitude);
}
