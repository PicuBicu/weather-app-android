package pl.piotrb.weatherapp.model.oncecallapi;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class Current {
    private Integer dt;
    private Integer sunrise;
    private Integer sunset;
    private Double temp;
    @SerializedName("feels_like")
    private Double feelsLike;
    private Integer pressure;
    private Integer humidity;
    @SerializedName("dew_point")
    private Double dewPoint;
    private Double uvi;
    private Integer clouds;
    private Integer visibility;
    @SerializedName("wind_speed")
    private Double windSpeed;
    @SerializedName("wind_deg")
    private Integer windDeg;
    private List<Weather> weather = null;
    private Rain rain;
}