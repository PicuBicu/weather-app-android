package pl.piotrb.weatherapp.repository;

import android.util.Log;

import pl.piotrb.weatherapp.model.DailyWeatherDataContext;
import pl.piotrb.weatherapp.model.WeeklyForecastDataContext;
import pl.piotrb.weatherapp.model.currentweatherdata.WeatherData;
import pl.piotrb.weatherapp.model.oncecallapi.WeeklyForecast;
import pl.piotrb.weatherapp.service.WeatherDataService;
import pl.piotrb.weatherapp.viewmodel.WeatherDataViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherDataRepository {

    private static WeatherDataRepository INSTANCE = null;
    private final WeatherDataService service = new retrofit2.Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherDataService.class);

    public static WeatherDataRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new WeatherDataRepository();
        }
        return INSTANCE;
    }

    public void getDailyWeatherData(DailyWeatherDataContext context, WeatherDataViewModel model) {
        service.getWeatherData(
                context.getCityName(),
                context.getUnit()
        ).enqueue(new Callback<WeatherData>() {
            @Override
            public void onResponse(Call<WeatherData> call, Response<WeatherData> response) {
                Log.i("RETROFIT", response.toString());
                if (response.isSuccessful()) {
                    Log.i("RETROFIT", "Successfully fetched data from OpenWeatherApi");
                    assert response.body() != null;
                    model.setWeatherData(response.body());
                    Log.i("RETROFIT", response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<WeatherData> call, Throwable t) {
                Log.e("RETROFIT", t.getMessage());
                model.setWeatherDataError(t.getMessage());
                Log.e("RETROFIT", "Problem with fetching data from OpenWeatherApi");
            }
        });
    }

    public void getWeeklyForecast(WeeklyForecastDataContext context, WeatherDataViewModel model) {
        service.getWeeklyForecast(
                context.getLatitude(),
                context.getLongitude()
        ).enqueue(new Callback<WeeklyForecast>() {
            @Override
            public void onResponse(Call<WeeklyForecast> call, Response<WeeklyForecast> response) {
                Log.i("RETROFIT", response.toString());
                if (response.isSuccessful()) {
                    Log.i("RETROFIT", "Successfully fetched data from OpenWeatherApi");
                    assert response.body() != null;
                    model.setWeeklyForecast(response.body());
                    Log.i("RETROFIT", response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<WeeklyForecast> call, Throwable t) {
                Log.e("RETROFIT", t.getMessage());
                model.setWeeklyForecastError(t.getMessage());
                Log.e("RETROFIT", "Problem with fetching data from OpenWeatherApi");
            }
        });
    }
}
