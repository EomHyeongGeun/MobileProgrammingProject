package my.kmu.com.navigationdrawerrrrr;

import android.app.Activity;
import android.icu.util.Calendar;
import android.icu.util.GregorianCalendar;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by eomhyeong-geun on 2016. 11. 20..
 */

public class TimeTableActivity extends Activity {

    TextView sunday, monday, tuesday, wednesday, thursday, friday, saturday;
    TextView today_date;
    Calendar cal = new GregorianCalendar();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.time_table);
        sunday      = (TextView)findViewById(R.id.sunday);
        monday      = (TextView)findViewById(R.id.monday);
        tuesday     = (TextView)findViewById(R.id.tuesday);
        wednesday   = (TextView)findViewById(R.id.wednesday);
        thursday    = (TextView)findViewById(R.id.thursday);
        friday      = (TextView)findViewById(R.id.friday);
        saturday    = (TextView)findViewById(R.id.saturday);
        today_date  = (TextView)findViewById(R.id.today_date);
        getDate();
    }

    public void getDate(){
        Calendar calendar = Calendar.getInstance();
        int day = cal.get(Calendar.DAY_OF_MONTH)+1;
        int month = cal.get(Calendar.MONTH)+1;
        int year = cal.get(Calendar.YEAR);
        today_date.setText(year + "년 " + month + "월 " + day + "일 ");

        int week  = cal.get(Calendar.DAY_OF_WEEK);
        int count = -week;    // 무슨요일이냐에 따라 달력 출력이 달라지게 하기 위해

        sunday.setText((day + count++) + "");
        monday.setText((day + count++) + "");
        tuesday.setText((day + count++) + "");
        wednesday.setText((day + count++) + "");
        thursday.setText((day + count++) + "");
        friday.setText((day + count++) + "");
        saturday.setText((day + count++) + "");
    }
}
