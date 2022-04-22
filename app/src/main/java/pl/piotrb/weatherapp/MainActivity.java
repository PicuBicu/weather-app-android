package pl.piotrb.weatherapp;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import pl.piotrb.weatherapp.viewmodel.WeatherDataViewModel;

public class MainActivity extends AppCompatActivity {


    private final FragmentManager fragmentManager = getSupportFragmentManager();
    private Button testButton;
    private WeatherDataViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            fragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainerView, WeatherDataFragment.class, null)
                    .setReorderingAllowed(true)
                    .addToBackStack("name") // name can be null
                    .commit();
            testButton = findViewById(R.id.testButton);
            viewModel = new ViewModelProvider(this).get(WeatherDataViewModel.class);
            testButton.setOnClickListener((cos) -> {
                viewModel.setCityName("Radomsko");
            });
        }
    }


}