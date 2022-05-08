package pl.piotrb.weatherapp.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import lombok.val;
import pl.piotrb.weatherapp.R;
import pl.piotrb.weatherapp.subscriber.OnWeeklyForecastChange;
import pl.piotrb.weatherapp.viewmodel.WeatherDataViewModel;

public class WeeklyForecastFragment extends Fragment implements OnWeeklyForecastChange {

    private ImageView imageView;
    private TextView tempTextView;
    private TextView unitsTextView;
    private TextView dayNameTextView;
    private TextView humidityTextView;
    private TextView windDirectionTextView;
    private TextView windSpeedTextView;

    private WeatherDataViewModel viewModel;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_weekly_forecast, container, false);
        imageView = root.findViewById(R.id.wf_image_view);
        tempTextView = root.findViewById(R.id.wf_temp_text_view);
        unitsTextView = root.findViewById(R.id.wf_units_text_view);
        dayNameTextView = root.findViewById(R.id.wf_day_name_text_view);
        humidityTextView = root.findViewById(R.id.wf_humiditiy_text_view);
        windDirectionTextView = root.findViewById(R.id.wf_wind_direction_text_view);
        windSpeedTextView = root.findViewById(R.id.wf_wind_speed_text_view);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        weekDay = getArguments().getInt("weekDay");
        viewModel = new ViewModelProvider(requireActivity()).get(WeatherDataViewModel.class);
        onWeeklyForecastChange();
    }

    @Override
    public void onWeeklyForecastChange() {
        viewModel.getWeeklyForecast().observe(getViewLifecycleOwner(), weeklyForecast -> {
            val daily = weeklyForecast.getDailyForecast().get(weekDay);
            assert daily != null;
            val tempValue = daily.getTemp().getDay() + "";
            val url = "https://openweathermap.org/img/wn/" + daily.getWeather().get(0).getIcon() + "@4x.png";
            val windDirection = daily.getWindDeg() + "";
            val windSpeed = daily.getWindSpeed() + "";
            val humidity = daily.getHumidity() + "";
            Log.i("PICASSO", url);
            Picasso.get()
                    .load(url)
                    .resize(250, 250)
                    .into(imageView);
            long timeInMillis = TimeUnit.SECONDS.toMillis(daily.getDt());
            Date date = new Date(timeInMillis);
            DateFormat formatter = new SimpleDateFormat("EEEE", Locale.getDefault());
            dayNameTextView.setText(formatter.format(date));
            tempTextView.setText(tempValue);
            unitsTextView.setText("C");
            humidityTextView.setText(humidity);
            windDirectionTextView.setText(windDirection);
            windSpeedTextView.setText(windSpeed);
        });
    }
}