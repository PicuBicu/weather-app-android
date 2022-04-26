package pl.piotrb.weatherapp.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DailyWeatherDataContext {
    private String cityName;
    private String unit;
}
