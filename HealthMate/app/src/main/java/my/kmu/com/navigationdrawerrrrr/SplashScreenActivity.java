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

        // splash_screen.xml으로부터 layout을 가져온다
        setContentView(R.layout.splash_screen);

        // 애니메이션 실행
        ImageView imageView01 = (ImageView)findViewById(R.id.imageView01);
        final AnimationDrawable drawable = (AnimationDrawable)imageView01.getBackground();
        drawable.start();


        // 타이머 생성
        Timer RunSplash = new Timer();
        // 타이머 시간동안 실행
        TimerTask ShowSplash = new TimerTask() {
            @Override
            public void run() {

                finish();

                Intent myIntent = new Intent(SplashScreenActivity.this, MainActivity.class);
                startActivity(myIntent);
            }
        };

        // 타이머 시작
        RunSplash.schedule(ShowSplash, Delay);
    }
}