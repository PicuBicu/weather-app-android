package pl.piotrb.weatherapp.strategy;

import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashSet;

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

public class WeatherApiStrategy implements DataProviderStrategy {

    private final SharedPreferences sharedPreferences;
    private final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();
    private final WeatherDataService service = new retrofit2.Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherDataService.class);

    public WeatherApiStrategy(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    @Override
    public void provideWeatherData(DailyWeatherDataContext context, WeatherDataViewModel viewModel) {
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
                    WeatherData weatherData = response.body();
                    viewModel.setWeatherData(weatherData);
                    Log.i("RETROFIT", response.body().toString());
                    addWeatherDataIfCityIsLiked(context.getCityName(), weatherData);
                } else {
                    viewModel.setWeatherDataError(response.message());
                }
            }

            @Override
            public void onFailure(Call<WeatherData> call, Throwable t) {
                Log.e("RETROFIT", t.getMessage());
                viewModel.setWeatherDataError(t.getMessage());
                Log.e("RETROFIT", "Problem with fetching data from OpenWeatherApi");
            }
        });
    }

    @Override
    public void provideWeatherForecast(WeeklyForecastDataContext context, WeatherDataViewModel viewModel) {
        service.getWeeklyForecast(
                context.getLatitude(),
                context.getLongitude(),
                context.getUnits()
        ).enqueue(new Callback<WeeklyForecast>() {
            @Override
            public void onResponse(Call<WeeklyForecast> call, Response<WeeklyForecast> response) {
                Log.i("RETROFIT", response.toString());
                if (response.isSuccessful()) {
                    Log.i("RETROFIT", "Successfully fetched data from OpenWeatherApi");
                    assert response.body() != null;
                    WeeklyForecast weeklyForecast = response.body();
                    viewModel.setWeeklyForecast(response.body());
                    Log.i("RETROFIT", response.body().toString());
                    addWeeklyForecastIfCityIsLiked(context.getCityName(), weeklyForecast);
                } else {
                    viewModel.setWeatherDataError(response.message());
                }
            }

            @Override
            public void onFailure(Call<WeeklyForecast> call, Throwable t) {
                Log.e("RETROFIT", t.getMessage());
                viewModel.setWeeklyForecastError(t.getMessage());
                Log.e("RETROFIT", "Problem with fetching data from OpenWeatherApi");
            }
        });
    }

    private boolean isCityLiked(String cityName) {
        return sharedPreferences.getStringSet("cities", new HashSet<>())
                .stream()
                .anyMatch(city -> city.equals(cityName));
    }

    private void addWeatherDataIfCityIsLiked(String cityName, WeatherData weatherData) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (isCityLiked(cityName)) {
            Log.i("APP", "Saving data for city " + cityName);
            String value = gson.toJson(weatherData);
            editor.putString(cityName + "_wd", value);
        }
        editor.apply();
    }

    private void addWeeklyForecastIfCityIsLiked(String cityName, WeeklyForecast weeklyForecast) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (isCityLiked(cityName)) {
            Log.i("APP", "Saving data for city " + cityName);
            String value = gson.toJson(weeklyForecast);
            editor.putString(cityName + "_wf", value);
        }
        editor.apply();
    }
}
