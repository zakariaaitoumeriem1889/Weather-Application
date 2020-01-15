package ma.zakaria.weatherapplication.service;

import io.reactivex.Observable;
import ma.zakaria.weatherapplication.model.WeatherResult;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OpenWeatherService {
    @GET("weather")
    Observable<WeatherResult> getWeather(@Query("q") String city,
                                         @Query("APPID") String apikey,
                                         @Query("units") String unit);
}
