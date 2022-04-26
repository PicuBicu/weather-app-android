package pl.piotrb.weatherapp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import pl.piotrb.weatherapp.R;
import pl.piotrb.weatherapp.model.DailyWeatherDataContext;
import pl.piotrb.weatherapp.repository.WeatherDataRepository;
import pl.piotrb.weatherapp.viewmodel.WeatherDataViewModel;

public class ConfigurationFragment extends Fragment {

    private Button acceptButton;
    private EditText cityNameText;
    private RadioGroup unitRadioGroup;
    private WeatherDataViewModel viewModel;
    private ConfigurationFragmentListener mainActivity;
    private final WeatherDataRepository repository = WeatherDataRepository.getInstance();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(WeatherDataViewModel.class);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mainActivity = (ConfigurationFragmentListener) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_configuration_text_view, container, false);
        acceptButton = root.findViewById(R.id.accept_config_button);
        cityNameText = root.findViewById(R.id.edit_city_name);
        unitRadioGroup = root.findViewById(R.id.unit_system_group);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        acceptButton.setOnClickListener((button) -> {
            Log.i("UI", "Accept config button has been clicked");
            DailyWeatherDataContext context = new DailyWeatherDataContext();
            context.setCityName(cityNameText.getText().toString());
            if (unitRadioGroup.getCheckedRadioButtonId() == R.id.metric_button) {
                context.setUnit("metric");
            } else {
                context.setUnit("imperial");
            }
            repository.getDailyWeatherData(context, viewModel);
            mainActivity.onChange();
        });
    }

    public interface ConfigurationFragmentListener {
        void onChange();
    }
}