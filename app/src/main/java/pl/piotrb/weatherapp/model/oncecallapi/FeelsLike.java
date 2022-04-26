package pl.piotrb.weatherapp.model.oncecallapi;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class FeelsLike {
    private Double day;
    private Double night;
    private Double eve;
    private Double morn;
}