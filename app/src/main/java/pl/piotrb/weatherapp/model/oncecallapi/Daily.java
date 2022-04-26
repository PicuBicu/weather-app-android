package pl.piotrb.weatherapp.model.oncecallapi;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class Daily {
    private Integer dt;
    private Integer sunrise;
    private Integer sunset;
    private Integer moonrise;
    private Integer moonset;
    @SerializedName("moon_phase")
    private Double moonPhase;
    private Temp temp;
    @SerializedName("feels_like")
    private FeelsLike feelsLike;
    private Integer pressure;
    private Integer humidity;
    @SerializedName("dew_point")
    private Double dewPoint;
    @SerializedName("wind_speed")
    private Double windSpeed;
    @SerializedName("wind_deg")
    private Integer windDeg;
    private List<Weather> weather = null;
    private Integer clouds;
    private Double pop;
    private Double rain;
    private Double uvi;
}