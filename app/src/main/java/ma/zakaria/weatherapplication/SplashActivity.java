package ma.zakaria.weatherapplication;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        startSplash();
    }

    public void startSplash() {
        try {
            Animation animation = AnimationUtils.loadAnimation(this, R.anim.translate);
            ImageView splash_Img = findViewById(R.id.logo);
            TextView Splash_TextView = findViewById(R.id.splash_text);
            splash_Img.clearAnimation();
            Splash_TextView.clearAnimation();
            splash_Img.startAnimation(animation);
            Splash_TextView.startAnimation(animation);
            Thread thread = new Thread() {
                @Override
                public void run() {
                    int pauseIt = 0;
                    while (pauseIt < 4000) {
                        try {
                            sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        pauseIt += 100;
                    }
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            };
            thread.start();
        } catch (Resources.NotFoundException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
