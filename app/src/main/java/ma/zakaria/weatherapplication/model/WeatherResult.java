package ma.zakaria.weatherapplication.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeatherResult {
    @SerializedName("coord")
    Coordinate coordinate;

    @SerializedName("weather")
    List<Weather> weathers;

    private String base;

    @SerializedName("main")
    Main Main;

    private float visibility;

    @SerializedName("wind")
    Wind Wind;

    @SerializedName("clouds")
    Clouds Clouds;

    private Long dt;

    @SerializedName("sys")
    Sys Sys;

    private float timezone;

    private float id;

    private String name;

    private float cod;

    public WeatherResult() {
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public List<Weather> getWeathers() {
        return weathers;
    }

    public void setWeathers(List<Weather> weathers) {
        this.weathers = weathers;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public ma.zakaria.weatherapplication.model.Main getMain() {
        return Main;
    }

    public void setMain(ma.zakaria.weatherapplication.model.Main main) {
        Main = main;
    }

    public float getVisibility() {
        return visibility;
    }

    public void setVisibility(float visibility) {
        this.visibility = visibility;
    }

    public ma.zakaria.weatherapplication.model.Wind getWind() {
        return Wind;
    }

    public void setWind(ma.zakaria.weatherapplication.model.Wind wind) {
        Wind = wind;
    }

    public ma.zakaria.weatherapplication.model.Clouds getClouds() {
        return Clouds;
    }

    public void setClouds(ma.zakaria.weatherapplication.model.Clouds clouds) {
        Clouds = clouds;
    }

    public Long getDt() {
        return dt;
    }

    public void setDt(Long dt) {
        this.dt = dt;
    }

    public ma.zakaria.weatherapplication.model.Sys getSys() {
        return Sys;
    }

    public void setSys(ma.zakaria.weatherapplication.model.Sys sys) {
        Sys = sys;
    }

    public float getTimezone() {
        return timezone;
    }

    public void setTimezone(float timezone) {
        this.timezone = timezone;
    }

    public float getId() {
        return id;
    }

    public void setId(float id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getCod() {
        return cod;
    }

    public void setCod(float cod) {
        this.cod = cod;
    }
}