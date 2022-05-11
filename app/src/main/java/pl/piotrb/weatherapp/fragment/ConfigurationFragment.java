package pl.piotrb.weatherapp.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import pl.piotrb.weatherapp.R;
import pl.piotrb.weatherapp.model.DailyWeatherDataContext;
import pl.piotrb.weatherapp.viewmodel.WeatherDataViewModel;

public class ConfigurationFragment extends Fragment {

    private List<String> cities = new ArrayList<>();
    private Spinner spinner;
    private EditText cityNameText;
    private Button acceptButton;
    private ImageButton likedButton;
    private RadioGroup unitRadioGroup;
    private WeatherDataViewModel viewModel;

    private void addNewCity(String cityName) {
        if (!isCityLiked(cityName)) {
            cities.add(cityName);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_configuration, container, false);
        cityNameText = root.findViewById(R.id.edit_city_name);
        unitRadioGroup = root.findViewById(R.id.unit_system_group);
        spinner = root.findViewById(R.id.spinner);
        likedButton = root.findViewById(R.id.liked_button);
        acceptButton = root.findViewById(R.id.accept_config_button);
        return root;
    }

    private boolean isCityLiked(String cityName) {
        return cities
                .stream()
                .anyMatch(city -> city.equals(cityName));
    }

    private void removeCity(String cityName) {
        cities.remove(cityName);
        SharedPreferences sharedPref = requireActivity().getSharedPreferences("GLOBAL_PREFERENCES", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.remove(cityName + "_wd");
        editor.remove(cityName + "_wf");
        editor.apply();
    }

    public void loadCities() {
        SharedPreferences sharedPref = requireActivity().getSharedPreferences("GLOBAL_PREFERENCES", Context.MODE_PRIVATE);
        Set<String> citiesSet = new HashSet<>();
        cities = new ArrayList<>(sharedPref.getStringSet("cities", citiesSet));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        SharedPreferences sharedPref = requireActivity().getSharedPreferences("GLOBAL_PREFERENCES", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putStringSet("cities", new HashSet<>(cities));
        editor.apply();
    }

    private void initSpinner(List<String> items) {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(requireActivity(), R.layout.spinner_item, items);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                cityNameText.setText(cities.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        spinner.setAdapter(arrayAdapter);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(WeatherDataViewModel.class);
        loadCities();
        initSpinner(cities);

        // Setting star button listener
        likedButton.setOnClickListener(button -> {
            String cityName = cityNameText.getText() + "";
            Log.i("UI", cities.toString());
            if (isCityLiked(cityName)) {
                removeCity(cityName);
            } else {
                addNewCity(cityName);
            }
        });

        // Accepting configuration
        acceptButton.setOnClickListener(button -> {
            Log.i("UI", "Accept config button has been clicked");
            DailyWeatherDataContext context = new DailyWeatherDataContext();
            context.setCityName(cityNameText.getText().toString());
            if (unitRadioGroup.getCheckedRadioButtonId() == R.id.metric_button) {
                context.setUnit("C");
            } else {
                context.setUnit("F");
            }
            viewModel.setDailyWeatherContextData(context);
        });
    }
}