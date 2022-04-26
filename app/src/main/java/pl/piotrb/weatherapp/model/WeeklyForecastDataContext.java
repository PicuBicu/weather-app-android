package pl.piotrb.weatherapp.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class WeeklyForecastDataContext {
    private Double latitude;
    private Double longitude;
}