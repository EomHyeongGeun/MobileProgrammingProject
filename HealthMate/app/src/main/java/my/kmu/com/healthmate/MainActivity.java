package my.kmu.com.healthmate;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    // 메인화면에 있는 4가지 레이아웃 클릭시 해당 액티비티로 이동
    public void goToTimeTable(View v){
        Intent intent = new Intent(getApplicationContext(), TimeTableActivity.class);
        startActivity(intent);
    }
    public void goToHowToDo(View v){
        Intent intent = new Intent(getApplicationContext(), HowToDoActivity.class);
        startActivity(intent);
    }
    public void goToPedometer(View v){
        Intent intent = new Intent(getApplicationContext(), PedometerActivity.class);
        startActivity(intent);
    }
    public void goToBodyRecord(View v){
        Intent intent = new Intent(getApplicationContext(), BodyRecordActivity.class);
        startActivity(intent);
    }
}
