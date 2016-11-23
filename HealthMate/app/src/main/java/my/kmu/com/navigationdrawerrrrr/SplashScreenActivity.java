package my.kmu.com.navigationdrawerrrrr;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by eomhyeong-geun on 2016. 11. 17..
 */

public class SplashScreenActivity extends Activity {

    // Set Duration of the Splash Screen
    long Delay = 3000;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // Get the view from splash_screen.xml
        setContentView(R.layout.splash_screen);

        // Play animation
        ImageView imageView01 = (ImageView)findViewById(R.id.imageView01);
        final AnimationDrawable drawable = (AnimationDrawable)imageView01.getBackground();
        drawable.start();


        // Create a Timer
        Timer RunSplash = new Timer();
        // Task to do when the timer ends
        TimerTask ShowSplash = new TimerTask() {
            @Override
            public void run() {
                // Close SplashScreenActivity.class
                finish();

                // Start MainActivity.class
                Intent myIntent = new Intent(SplashScreenActivity.this, MainActivity.class);
                startActivity(myIntent);
            }
        };

        // Start the timer
        RunSplash.schedule(ShowSplash, Delay);
    }
}