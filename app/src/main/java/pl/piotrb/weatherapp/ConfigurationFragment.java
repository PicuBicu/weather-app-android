package pl.piotrb.weatherapp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import pl.piotrb.weatherapp.viewmodel.WeatherDataViewModel;

public class ConfigurationFragment extends Fragment {

    public interface ConfigurationFragmentListener {
        void onChange();
    }

    private ConfigurationFragmentListener mainActivity;
    private Button acceptButton;
    private EditText cityNameText;
    private RadioGroup unitRadioGroup;
    private WeatherDataViewModel viewModel;

    public ConfigurationFragment() { }

    public static ConfigurationFragment newInstance(String param1, String param2) {
        ConfigurationFragment fragment = new ConfigurationFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) { }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mainActivity = (ConfigurationFragmentListener) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_configuration_text_view, container, false);

        acceptButton = root.findViewById(R.id.accept_config_button);
        cityNameText = root.findViewById(R.id.edit_city_name);
        unitRadioGroup = root.findViewById(R.id.unit_system_group);

        viewModel = new ViewModelProvider(requireActivity()).get(WeatherDataViewModel.class);

        acceptButton.setOnClickListener((button) -> {
            viewModel.setCityName(cityNameText.getText().toString());
            if (unitRadioGroup.getCheckedRadioButtonId() == R.id.metric_button) {
                viewModel.setUnitSystem("metric");
            } else {
                viewModel.setUnitSystem("imperial");
            }
            mainActivity.onChange();
        });

        return root;
    }
}