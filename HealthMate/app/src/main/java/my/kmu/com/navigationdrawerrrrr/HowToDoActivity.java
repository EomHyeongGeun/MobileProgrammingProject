package my.kmu.com.navigationdrawerrrrr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * Created by eomhyeong-geun on 2016. 11. 20..
 */

public class HowToDoActivity extends Activity {
    TextView shoulder1, shoulder2, chest, arm1, arm2, abdominal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.how_to_do);
        shoulder1 = (TextView)findViewById(R.id.shoulder1);
        shoulder2 = (TextView)findViewById(R.id.shoulder2);
        chest     = (TextView)findViewById(R.id.chest);
        arm1      = (TextView)findViewById(R.id.arm1);
        arm2      = (TextView)findViewById(R.id.arm2);
        abdominal = (TextView)findViewById(R.id.abdominal);

    }

    // 몸 부위별 클릭시 각각 이동하는 Activity가 다름.
    public void click_body(View v){
        switch (v.getId()){
            case R.id.shoulder1: case R.id.shoulder2:
                Intent intent1 = new Intent(getApplicationContext(), ShoulderActivity.class);
                startActivity(intent1);
                break;
            case R.id.chest:
                Intent intent2 = new Intent(getApplicationContext(), ChestActivity.class);
                startActivity(intent2);
                break;
            case R.id.arm1:case R.id.arm2:
                Intent intent3 = new Intent(getApplicationContext(), ArmActivity.class);
                startActivity(intent3);
                break;
            case R.id.abdominal:
                Intent intent4 = new Intent(getApplicationContext(), AbdominalActivity.class);
                startActivity(intent4);
                break;
        }
    }
}
