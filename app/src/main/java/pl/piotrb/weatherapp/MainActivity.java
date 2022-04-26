package pl.piotrb.weatherapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import pl.piotrb.weatherapp.fragment.AdditionalDataFragment;
import pl.piotrb.weatherapp.fragment.ConfigurationFragment;
import pl.piotrb.weatherapp.fragment.WeatherDataFragment;
import pl.piotrb.weatherapp.model.WeeklyForecastDataContext;
import pl.piotrb.weatherapp.repository.WeatherDataRepository;
import pl.piotrb.weatherapp.style.ZoomOutPageTransformer;
import pl.piotrb.weatherapp.viewmodel.WeatherDataViewModel;

public class MainActivity extends AppCompatActivity implements ConfigurationFragment.ConfigurationFragmentListener {

    private final WeatherDataRepository repository = WeatherDataRepository.getInstance();
    private final FragmentManager fragmentManager = getSupportFragmentManager();
    private WeatherDataViewModel viewModel;
    private static final int NUM_PAGES = 7;
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

        if (savedInstanceState == null) {

            viewModel = new ViewModelProvider(this)
                    .get(WeatherDataViewModel.class);

            viewPager = findViewById(R.id.pager);
            viewPager.setPageTransformer(new ZoomOutPageTransformer());
            pagerAdapter = new ScreenSlidePageAdapter(this, NUM_PAGES);
            viewPager.setAdapter(pagerAdapter);

            onDailyWeatherDataError();
            onDailyWeatherData();

            fragmentManager.beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.middle_fragment, AdditionalDataFragment.class, null)
                    .add(R.id.first_fragment, ConfigurationFragment.class, null)
                    .commit();
        }
    }

    @Override
    public void onChange() {
        fragmentManager.beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.first_fragment, WeatherDataFragment.class,null)
        .commit();
    }

    private void onDailyWeatherDataError() {
        viewModel.getWeatherDataError().observe(this, error -> {
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
        });
    }

    private void onDailyWeatherData() {
        viewModel.getWeatherData().observe(this, weatherData -> {
            Log.i("RETROFIT", "Subscribe to Weather Data");
            WeeklyForecastDataContext forecast = new WeeklyForecastDataContext();
            forecast.setLatitude(weatherData.getCoord().getLat());
            forecast.setLongitude(weatherData.getCoord().getLon());
            repository.getWeeklyForecast(forecast, viewModel);
        });
    }
}