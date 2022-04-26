package pl.piotrb.weatherapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import pl.piotrb.weatherapp.fragment.WeeklyForecastFragment;

public class ScreenSlidePageAdapter extends FragmentStateAdapter {

    private Integer numOfPages;

    public ScreenSlidePageAdapter(@NonNull AppCompatActivity activity, Integer numOfPages) {
        super(activity);
        this.numOfPages = numOfPages;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return WeeklyForecastFragment.newInstance(position);
    }

    @Override
    public int getItemCount() {
        return numOfPages;
    }
}
