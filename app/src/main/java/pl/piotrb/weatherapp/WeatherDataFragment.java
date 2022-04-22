package pl.piotrb.weatherapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.text.SimpleDateFormat;
import java.util.Date;

import pl.piotrb.weatherapp.model.WeatherData;
import pl.piotrb.weatherapp.service.WeatherDataService;
import pl.piotrb.weatherapp.viewmodel.WeatherDataViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherDataFragment extends Fragment {

    private final WeatherDataService service = new retrofit2.Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherDataService.class);
    private TextView pressureTextView;
    private TextView temperatureTextView;
    private TextView geolocationTextView;
    private TextView cityNameTextView;
    private TextView timeTextView;
    private WeatherDataViewModel viewModel;

    public static WeatherDataFragment newInstance(String arg) {
        WeatherDataFragment fragment = new WeatherDataFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(WeatherDataViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel.getCityName().observe(getViewLifecycleOwner(), cityName -> {
            service.getWeatherData(cityName).enqueue(new Callback<WeatherData>() {
                @Override
                public void onResponse(Call<WeatherData> call, Response<WeatherData> response) {
                    Log.i("RETROFIT", response.toString());
                    if (response.isSuccessful()) {
                        Log.i("RETROFIT", "Udało się poprawnie pobrać dane z bazy");
                        WeatherData object = response.body();
                        Log.i("RETROFIT", response.body().toString());
                        pressureTextView.setText("Ciśnienie: " + object.getMain().getPressure());
                        temperatureTextView.setText("Temperatura: " + object.getMain().getTemp());
                        geolocationTextView.setText("Geolokalizacja: " + object.getCoord());
                        cityNameTextView.setText("Miejscowość: " + cityName);
                        Date date = new Date(object.getDt() * 1000);
                        SimpleDateFormat format = new SimpleDateFormat("E yyyy-MM-dd 'at' hh:mm:ss a zzz");
                        timeTextView.setText("Data:" + format.format(date));
                    }
                }

                @Override
                public void onFailure(Call<WeatherData> call, Throwable t) {
                    Log.e("RETROFIT", t.getMessage());
                    Log.e("RETROFIT", "Nie udało się pobrać poprawnie danych z API");
                }
            });
        });
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
}