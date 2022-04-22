package pl.piotrb.weatherapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import pl.piotrb.weatherapp.service.WeatherDataService;
import pl.piotrb.weatherapp.viewmodel.WeatherDataViewModel;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherDataFragment extends Fragment {

    private final WeatherDataService service = new retrofit2.Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherDataService.class);
    private TextView outputView;
    private WeatherDataViewModel viewModel;

    public static WeatherDataFragment newInstance(String arg) {
        WeatherDataFragment fragment = new WeatherDataFragment();
        Bundle args = new Bundle();
        args.putString("test", arg);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(WeatherDataViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_weather_data, container, false);
        outputView = root.findViewById(R.id.outputView);
        viewModel.getCityName().observe(getViewLifecycleOwner(), item -> {
            Log.i("DATA", item);
        });
        return root;
    }
}