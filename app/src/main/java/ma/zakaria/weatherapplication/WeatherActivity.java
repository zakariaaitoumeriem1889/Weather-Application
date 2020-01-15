package ma.zakaria.weatherapplication;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import ma.zakaria.weatherapplication.model.WeatherResult;
import ma.zakaria.weatherapplication.service.OpenWeatherService;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherActivity extends AppCompatActivity {
    String key_api = "1aae8b69408f2fd02210dcc6c29c3a67";
    TextView tvaddress, tvstatus, tvupdated, tvtemp, tvtemp_min, tvtemp_max, tvsunrise, tvsunset, tvwind,
            tvpressure, tvhumidity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        tvaddress = (TextView) findViewById(R.id.address);
        tvstatus = (TextView) findViewById(R.id.status);
        tvupdated = (TextView) findViewById(R.id.updated_at);
        tvtemp = (TextView) findViewById(R.id.temp);
        tvtemp_min = (TextView) findViewById(R.id.temp_min);
        tvtemp_max = (TextView) findViewById(R.id.temp_max);
        tvsunrise = (TextView) findViewById(R.id.sunrise);
        tvsunset = (TextView) findViewById(R.id.sunset);
        tvwind = (TextView) findViewById(R.id.wind);
        tvpressure = (TextView) findViewById(R.id.pressure);
        tvhumidity = (TextView) findViewById(R.id.humidity);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle = getIntent().getExtras();
        String city = bundle.getString("city");
        getWeather(city, key_api);
    }

    private void getWeather(String city, String key_api) {
        CompositeDisposable disposable = new CompositeDisposable();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        OpenWeatherService service = retrofit.create(OpenWeatherService.class);

        disposable.add(service.getWeather(city, key_api, "metric")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<WeatherResult>() {
                    @Override
                    public void accept(WeatherResult weatherResult) throws Exception {
                        System.out.println(weatherResult.getWeathers().get(0).getMain());
                        tvaddress.setText(weatherResult.getName());
                        tvstatus.setText(weatherResult.getWeathers().get(0).getDescription());
                        Long updateAt = weatherResult.getDt();
                        tvupdated.setText("Updated at: " + new SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.getDefault()).format(new Date(updateAt)));
                        tvtemp.setText(new StringBuilder(String.valueOf(weatherResult.getMain().getTemp())).append("ºC").toString());
                        tvtemp_min.setText(new StringBuilder().append("Temp. max. : ").append(weatherResult.getMain().getTemp_min()).append("°C").toString());
                        tvtemp_max.setText(new StringBuilder().append("Temp. min. : ").append(weatherResult.getMain().getTemp_max()).append("°C").toString());
                        tvsunrise.setText(new SimpleDateFormat("hh:mm a", Locale.FRENCH).format(new Date(weatherResult.getSys().getSunrise())));
                        tvsunset.setText(new SimpleDateFormat("hh:mm a", Locale.FRENCH).format(new Date(weatherResult.getSys().getSunset())));
                        tvwind.setText(new StringBuilder(String.valueOf(weatherResult.getWind().getSpeed())).append(" m/s").toString());
                        tvpressure.setText(new StringBuilder(String.valueOf(weatherResult.getMain().getPressure())).append("hPa").toString());
                        tvhumidity.setText(new StringBuilder(String.valueOf(weatherResult.getMain().getHumidity())).append("%").toString());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(getApplicationContext(),""+throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                })
        );
    }


}
