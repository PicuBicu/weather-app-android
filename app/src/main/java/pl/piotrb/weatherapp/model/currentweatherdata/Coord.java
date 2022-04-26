
package pl.piotrb.weatherapp.model.currentweatherdata;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class Coord {
    private Double lon;
    private Double lat;
}
