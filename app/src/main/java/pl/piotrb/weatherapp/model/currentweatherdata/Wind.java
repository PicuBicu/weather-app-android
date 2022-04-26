package pl.piotrb.weatherapp.model.currentweatherdata;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class Wind {
    private Double speed;
    private Integer deg;
}
