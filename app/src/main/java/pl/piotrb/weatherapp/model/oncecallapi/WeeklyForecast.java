package pl.piotrb.weatherapp.model.oncecallapi;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class WeeklyForecast {
    private Double lat;
    private Double lon;
    private String timezone;
    @SerializedName("timezone_offset")
    private Integer timezoneOffset;
    private Current current;
    private Weather weather;
    private Rain rain;
    @SerializedName("daily")
    private List<Daily> dailyForecast;
}
