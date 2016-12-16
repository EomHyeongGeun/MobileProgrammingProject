package my.kmu.com.navigationdrawerrrrr;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.icu.util.Calendar;
import android.icu.util.GregorianCalendar;
import android.os.Bundle;
import android.widget.TextView;
import android.view.View;
import android.view.Window;
import android.widget.Toast;


/**
 * Created by eomhyeong-geun on 2016. 11. 20..
 */

public class PedometerActivity extends Activity implements SensorEventListener {
    MyDB myDB;
    SQLiteDatabase sqLiteDatabase;
    int dayOfWeek;

    public static int cnt = 0;

    private TextView tv_count, tv_remain, tv_goal;
    Calendar cal = new GregorianCalendar();
    String sql_goal;
    String goal_str;

    private long lastTime;
    private float speed;
    private float lastX;
    private float lastY;
    private float lastZ;
    private float x, y, z;

    private static final int SHAKE_THRESHOLD = 800;
    private static final int DATA_X = SensorManager.DATA_X;
    private static final int DATA_Y = SensorManager.DATA_Y;
    private static final int DATA_Z = SensorManager.DATA_Z;

    private SensorManager sensorManager;
    private Sensor accelerormeterSensor;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pedometer);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerormeterSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        tv_count = (TextView)findViewById(R.id.tv_count);
        tv_remain= (TextView)findViewById(R.id.tv_remain);
        tv_goal  = (TextView)findViewById(R.id.tv_goal);

        // 무슨 요일인지에 따라 데이터베이스로부터 TimeTable.class에서 생성한 오늘 걸어야 할 양 받아오기
        myDB = new MyDB(this);
        sqLiteDatabase = myDB.getReadableDatabase();
        dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek == 1) {
            sql_goal = "SELECT * FROM goalone";
        } else if (dayOfWeek == 2) {
            sql_goal = "SELECT * FROM goaltwo";
        } else if (dayOfWeek == 3) {
            sql_goal = "SELECT * FROM goalthree";
        } else if (dayOfWeek == 4) {
            sql_goal = "SELECT * FROM goalfour";
        } else if (dayOfWeek == 5) {
            sql_goal = "SELECT * FROM goalfive";
        } else if (dayOfWeek == 6) {
            sql_goal = "SELECT * FROM goalsix";
        } else if (dayOfWeek == 7) {
            sql_goal = "SELECT * FROM goalseven";
        }

        Cursor cursor = sqLiteDatabase.rawQuery(sql_goal, null);
        while (cursor.moveToNext()) {
                goal_str =  cursor.getString(6);
        }

        tv_goal.setText(goal_str+" 걸음");
        tv_remain.setText(goal_str+" 걸음");
        cursor.close();
        sqLiteDatabase.close();
        // 목표량 받아오기 끝

    }


    @Override
    public void onStart() {
        super.onStart();
        if (accelerormeterSensor != null)
            sensorManager.registerListener(this, accelerormeterSensor,
                    SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (sensorManager != null)
            sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            long currentTime = System.currentTimeMillis();
            long gabOfTime = (currentTime - lastTime);
            if (gabOfTime > 100) {
                lastTime = currentTime;
                x = event.values[SensorManager.DATA_X];
                y = event.values[SensorManager.DATA_Y];
                z = event.values[SensorManager.DATA_Z];

                speed = Math.abs(x + y + z - lastX - lastY - lastZ) / gabOfTime * 10000;

                if (speed > SHAKE_THRESHOLD) {
                    tv_count.setText("" + (++cnt) + "걸음");

                    // 목표량이 설정되지 않은 상태에서 pedometer 실행 시 목표량 설정해달라는 Toast와 함께 메인페이지로.
                    try {
                        tv_remain.setText("" + (Integer.parseInt(goal_str) - cnt) + "걸음");
                    }catch (Exception e){
                        finish();
                        Toast.makeText(getApplicationContext(), "목표량을 설정해주세요!", Toast.LENGTH_SHORT).show();
                    }
                }

                lastX = event.values[DATA_X];
                lastY = event.values[DATA_Y];
                lastZ = event.values[DATA_Z];
            }

        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}