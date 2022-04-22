package pl.piotrb.weatherapp.service;

import java.util.List;

import pl.piotrb.weatherapp.model.City;
import pl.piotrb.weatherapp.model.WeatherData;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherDataService {
    @GET("/geo/1.0/direct")
    Call<List<City>> getCity(@Query("q") String cityName,
                             @Query("appid") String apiKey);
    @GET("/data/2.5/weather")
    Call<WeatherData> getWeatherData(@Query("lat") Double latitude,
                              @Query("lat") Double longitude,
                              @Query("limit") Integer limit,
                              @Query("units") String units,
                              @Query("appid") String apiKey);
}
