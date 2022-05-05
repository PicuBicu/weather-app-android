package pl.piotrb.weatherapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import lombok.val;
import pl.piotrb.weatherapp.fragment.AdditionalDataFragment;
import pl.piotrb.weatherapp.fragment.ConfigurationFragment;
import pl.piotrb.weatherapp.fragment.WeatherDataFragment;
import pl.piotrb.weatherapp.model.WeeklyForecastDataContext;
import pl.piotrb.weatherapp.repository.WeatherDataRepository;
import pl.piotrb.weatherapp.style.ZoomOutPageTransformer;
import pl.piotrb.weatherapp.subscriber.OnWeatherDataChange;
import pl.piotrb.weatherapp.subscriber.OnWeatherDataError;
import pl.piotrb.weatherapp.subscriber.OnWeeklyForecastError;
import pl.piotrb.weatherapp.viewmodel.WeatherDataViewModel;

public class MainActivity extends AppCompatActivity implements
        OnWeatherDataChange,
        OnWeatherDataError,
        OnWeeklyForecastError {

    private static final int NUM_PAGES = 7;
    private final FragmentManager fragmentManager = getSupportFragmentManager();
    private WeatherDataRepository repository;
    private WeatherDataViewModel viewModel;
    private ViewPager2 viewPager;
    private FragmentStateAdapter pagerAdapter;

    @Override
    public void onBackPressed() {
        if (viewPager.getCurrentItem() == 0) {
            super.onBackPressed();
        } else {
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        repository = WeatherDataRepository.getInstance(
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE),
                getSharedPreferences("GLOBAL_PREFERENCES", Context.MODE_PRIVATE));
        // Handling view model
        viewModel = new ViewModelProvider(this)
                .get(WeatherDataViewModel.class);
        onDailyWeatherDataContextChange();
        onWeatherDataChange();
        onWeeklyForecastError();
        onWeatherDataError();

        // Adding fragment and view pager
        viewPager = findViewById(R.id.pager);
        viewPager.setPageTransformer(new ZoomOutPageTransformer());
        pagerAdapter = new ScreenSlidePageAdapter(this, NUM_PAGES);
        viewPager.setAdapter(pagerAdapter);
        fragmentManager.beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.first_fragment, ConfigurationFragment.class, null)
                .add(R.id.middle_fragment, AdditionalDataFragment.class, null)
                .commit();

    }

    // Setting daily weather context will lead to replacing config fragment with weather data fragment
    private void onDailyWeatherDataContextChange() {
        viewModel.getDailyWeatherContextData().observe(this, context -> {
            Log.i("UI", "Changing fragments");
            fragmentManager.beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.first_fragment, WeatherDataFragment.class, null)
                    .commit();
            repository.getDailyWeatherData(context, viewModel);
        });
    }

    // Weather data change will lead to fetching weekly forecast
    @Override
    public void onWeatherDataChange() {
        viewModel.getWeatherData().observe(this, weatherData -> {
            Log.i("RETROFIT", "Subscribe to Weather Data");
            val unitValue = viewModel.getDailyWeatherContextData().getValue().getUnit();
            assert unitValue != null;
            val forecast = new WeeklyForecastDataContext();
            forecast.setUnits(unitValue);
            forecast.setLatitude(weatherData.getCoord().getLat());
            forecast.setLongitude(weatherData.getCoord().getLon());
            forecast.setCityName(weatherData.getName());
            repository.getWeeklyForecast(forecast, viewModel);
        });
    }

    @Override
    public void onWeatherDataError() {
        viewModel.getWeatherDataError().observe(this, error -> {
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public void onWeeklyForecastError() {
        viewModel.getWeeklyForecastError().observe(this, error -> {
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
        });
    }
}