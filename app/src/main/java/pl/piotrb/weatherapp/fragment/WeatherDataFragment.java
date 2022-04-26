package pl.piotrb.weatherapp.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.text.SimpleDateFormat;
import java.util.Date;

import pl.piotrb.weatherapp.R;
import pl.piotrb.weatherapp.viewmodel.WeatherDataViewModel;

public class WeatherDataFragment extends Fragment {

    private TextView pressureTextView;
    private TextView temperatureTextView;
    private TextView geolocationTextView;
    private TextView cityNameTextView;
    private TextView timeTextView;
    private WeatherDataViewModel viewModel;

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
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel.getWeatherData().observe(getViewLifecycleOwner(), weatherData -> {
            Log.i("UI", "Updating weather data fragment UI");
            pressureTextView.setText("Ciśnienie: " + weatherData.getMain().getPressure());
            temperatureTextView.setText("Temperatura: " + weatherData.getMain().getTemp());
            geolocationTextView.setText("Geolokalizacja: " + weatherData.getCoord());
            cityNameTextView.setText("Miejscowość: " + weatherData.getName());
            Date date = new Date();
            SimpleDateFormat format = new SimpleDateFormat("E yyyy-MM-dd 'at' hh:mm:ss a zzz");
            timeTextView.setText("Data:" + format.format(date));
        });
        viewModel.getWeatherDataError().observe(getViewLifecycleOwner(), error -> {
            Toast.makeText(requireActivity(), error, Toast.LENGTH_SHORT).show();
        });
    }
}