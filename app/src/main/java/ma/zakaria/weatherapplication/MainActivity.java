package ma.zakaria.weatherapplication;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import ma.zakaria.weatherapplication.interfaces.Main_menu;
import ma.zakaria.weatherapplication.tracker.GPSTracker;

public class MainActivity extends AppCompatActivity {
    private SharedPreferences preferences;
    private String country, city;
    private CardView weatherCard;
    private BottomNavigationView bottomNavigationView;

    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    switch (menuItem.getItemId()) {
                        case Main_menu.gps:
                            GPSTracker tracker = new GPSTracker(MainActivity.this);
                            Location location = tracker.getLocation();
                            Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
                            ActivityCompat.requestPermissions(MainActivity.this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 123);
                            if (location != null) {
                                double latitude = location.getLatitude();
                                double longitude = location.getLongitude();
                                try {
                                    List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
                                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                                    String geocity = addresses.get(0).getLocality();
                                    setDefaults("city", addresses.get(0).getLocality(), MainActivity.this);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            return true;
                        case Main_menu.city_location:
                            location();
                            return true;
                    }
                    return false;
                }
            };

    public void location() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        View view = getLayoutInflater().inflate(R.layout.dialog_input, null);
        final EditText etCity = (EditText) view.findViewById(R.id.etcity);
        Button save = (Button) view.findViewById(R.id.btnsave);
        Button cancel = (Button) view.findViewById(R.id.btncancel);
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        dialog.show();
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strCity = etCity.getText().toString();
                setDefaults("city", strCity, MainActivity.this);
                dialog.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        weatherCard = findViewById(R.id.weather);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

        preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        city = preferences.getString("city", "");
        country = preferences.getString("country", "");

        weatherCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, WeatherActivity.class);
                preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                city = preferences.getString("city", "");
                intent.putExtra("city", city);
                startActivity(intent);
            }
        });
    }

    public static void setDefaults(String key, String value, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }
}
