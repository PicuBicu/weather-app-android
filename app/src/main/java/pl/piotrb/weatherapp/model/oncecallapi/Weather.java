package pl.piotrb.weatherapp.model.oncecallapi;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class Weather {
    private Integer id;
    private String main;
    private String description;
    private String icon;
}