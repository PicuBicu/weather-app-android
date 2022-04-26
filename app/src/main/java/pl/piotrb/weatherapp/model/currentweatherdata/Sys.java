package pl.piotrb.weatherapp.model.currentweatherdata;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class Sys {
    private Integer type;
    private Integer id;
    private Double message;
    private String country;
    private Integer sunrise;
    private Integer sunset;
}
