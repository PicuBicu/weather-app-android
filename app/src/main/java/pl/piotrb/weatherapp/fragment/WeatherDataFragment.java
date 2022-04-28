package pl.piotrb.weatherapp.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import pl.piotrb.weatherapp.R;
import pl.piotrb.weatherapp.model.WeeklyForecastDataContext;
import pl.piotrb.weatherapp.model.currentweatherdata.WeatherData;
import pl.piotrb.weatherapp.repository.WeatherDataRepository;
import pl.piotrb.weatherapp.viewmodel.WeatherDataViewModel;

public class WeatherDataFragment extends Fragment {

    private TextView pressureTextView;
    private TextView temperatureTextView;
    private TextView geolocationTextView;
    private TextView cityNameTextView;
    private TextView timeTextView;
    private TextView tempUnitTextView;
    private TextView pressureUnitTextView;
    private ImageView imageView;
    private WeatherDataViewModel viewModel;
    private WeatherDataRepository repository = WeatherDataRepository.getInstance();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(WeatherDataViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_weather_data, container, false);
        pressureTextView = root.findViewById(R.id.pressure_text_view);
        temperatureTextView = root.findViewById(R.id.temp_text_view);
        geolocationTextView = root.findViewById(R.id.geolocation_text_view);
        cityNameTextView = root.findViewById(R.id.city_text_view);
        timeTextView = root.findViewById(R.id.time_text_view);
        tempUnitTextView = root.findViewById(R.id.temp_unit_text_view);
        pressureUnitTextView = root.findViewById(R.id.pressure_unit_text_view);
        imageView = root.findViewById(R.id.image_view);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel.getWeatherData().observe(getViewLifecycleOwner(), weatherData -> {
            Log.i("UI", "Updating weather data fragment UI");
            updateUI(weatherData);
        });
    }

    private void updateUI(WeatherData weatherData) {
        pressureTextView.setText(
                String.format(Locale.getDefault(),
                        "%d",
                        weatherData.getMain().getPressure()));
        temperatureTextView.setText(String.format(
                Locale.getDefault(),
                "%f",
                weatherData.getMain().getTemp()
        ));
        geolocationTextView.setText(String.format(
                Locale.getDefault(),
                "lat=%f lon=%f",
                weatherData.getCoord().getLat(),
                weatherData.getCoord().getLon()
        ));
//            TODO K or C
        tempUnitTextView.setText("C");
        String iconText = weatherData.getWeather().get(0).getIcon();
        String url = "https://openweathermap.org/img/wn/" + iconText + "@4x.png";
        Log.i("PICASSO", url);
        Picasso.get()
                .load(url)
                .into(imageView);
        pressureUnitTextView.setText("hPA");
        cityNameTextView.setText(weatherData.getName());
        DateFormat format = SimpleDateFormat.getDateInstance();
        timeTextView.setText(format.format(new Date()));
    }
}