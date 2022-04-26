package pl.piotrb.weatherapp.model;

public class DailyWeatherDataContext {
    private String cityName;
    private String unit;

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
