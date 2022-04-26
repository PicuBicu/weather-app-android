package pl.piotrb.weatherapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import pl.piotrb.weatherapp.fragment.AdditionalDataFragment;
import pl.piotrb.weatherapp.fragment.ConfigurationFragment;
import pl.piotrb.weatherapp.fragment.WeatherDataFragment;

public class MainActivity extends AppCompatActivity implements ConfigurationFragment.ConfigurationFragmentListener {

    private final FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            fragmentManager.beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.first_fragment, AdditionalDataFragment.class, null)
                    .add(R.id.first_fragment, ConfigurationFragment.class, null)
                    .commit();
        }
    }

    @Override
    public void onChange() {
        fragmentManager.beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.first_fragment, WeatherDataFragment.class,null)
        .commit();
    }
}