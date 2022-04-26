package pl.piotrb.weatherapp.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import pl.piotrb.weatherapp.R;
import pl.piotrb.weatherapp.viewmodel.WeatherDataViewModel;

public class WeeklyForecastFragment extends Fragment {

    private WeatherDataViewModel viewModel;
    private TextView dayTextView;
    private Integer weekDay;

    public static WeeklyForecastFragment newInstance(Integer weekDay) {
        WeeklyForecastFragment fragment = new WeeklyForecastFragment();
        Bundle args = new Bundle();
        args.putInt("weekDay", weekDay);
        Log.i("UI", "Setting new fragment with weekday " + weekDay);
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
        View root = inflater.inflate(R.layout.fragment_weekly_forecast, container, false);
        dayTextView = root.findViewById(R.id.day_text_view);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        weekDay = getArguments().getInt("weekDay");
        onWeeklyForecastChange();
        onWeeklyForecastError();
    }

    private void onWeeklyForecastChange() {
        viewModel.getWeeklyForecast().observe(getViewLifecycleOwner(), weeklyForecast -> {
            dayTextView.setText(weeklyForecast.getDailyForecast().get(weekDay).toString());
        });
    }

    private void onWeeklyForecastError() {
        viewModel.getWeeklyForecastError().observe(getViewLifecycleOwner(), error -> {
            Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
        });
    }
}