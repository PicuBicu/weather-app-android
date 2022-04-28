package pl.piotrb.weatherapp.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import lombok.val;
import pl.piotrb.weatherapp.R;
import pl.piotrb.weatherapp.subscriber.OnWeatherDataChange;
import pl.piotrb.weatherapp.viewmodel.WeatherDataViewModel;

public class AdditionalDataFragment extends Fragment implements OnWeatherDataChange {

    private TextView windSpeedTextView;
    private TextView windDirectionTextView;
    private TextView humidityTextView;
    private TextView visibilityTextView;

    private WeatherDataViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_additional_data, container, false);
        windSpeedTextView = root.findViewById(R.id.wind_speed_text_view);
        windDirectionTextView = root.findViewById(R.id.wind_direction_text_view);
        humidityTextView = root.findViewById(R.id.humidity_text_view);
        visibilityTextView = root.findViewById(R.id.visibility_text_view);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(WeatherDataViewModel.class);
        onWeatherDataChange();
    }

    @Override
    public void onWeatherDataChange() {
        viewModel.getWeatherData().observe(getViewLifecycleOwner(), weatherData -> {
            val speedValue = weatherData.getWind().getSpeed() + "m/s";
            val directionValue = weatherData.getWind().getDeg() + " degrees";
            val humidityValue = weatherData.getWind().getSpeed() + "%";
            val visibilityValue = weatherData.getWind().getSpeed() + " m";
            windSpeedTextView.setText(speedValue);
            windDirectionTextView.setText(directionValue);
            humidityTextView.setText(humidityValue);
            visibilityTextView.setText(visibilityValue);
        });
    }
}