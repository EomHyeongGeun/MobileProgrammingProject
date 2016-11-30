package my.kmu.com.navigationdrawerrrrr;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.util.Calendar;
import android.icu.util.GregorianCalendar;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by eomhyeong-geun on 2016. 11. 20..
 */

public class TimeTableActivity extends Activity {

    TextView sunday, monday, tuesday, wednesday, thursday, friday, saturday;
    TextView today_date;
    TextView sunday_goal, monday_goal, tuesday_goal, wednesday_goal, thursday_goal, friday_goal, saturday_goal;
    Button sunday_btn, monday_btn, tuesday_btn, wednesday_btn, thursday_btn, friday_btn, saturday_btn, reset_btn;
    Calendar cal = new GregorianCalendar();
    MyDB mydb;
    SQLiteDatabase sqlite;
    String today_goal;
    String sql_goal;    // getGoal에서 쓰일 String
    String sql_dialog;  // createDialogBox에서 쓰일 String
    String difficulty;  // 난이도 표시용
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.time_table);
        sunday         = (TextView)findViewById(R.id.sunday);
        monday         = (TextView)findViewById(R.id.monday);
        tuesday        = (TextView)findViewById(R.id.tuesday);
        wednesday      = (TextView)findViewById(R.id.wednesday);
        thursday       = (TextView)findViewById(R.id.thursday);
        friday         = (TextView)findViewById(R.id.friday);
        saturday       = (TextView)findViewById(R.id.saturday);
        today_date     = (TextView)findViewById(R.id.today_date);
        sunday_goal    = (TextView)findViewById(R.id.sunday_goal);
        monday_goal    = (TextView)findViewById(R.id.monday_goal);
        tuesday_goal   = (TextView)findViewById(R.id.tuesday_goal);
        wednesday_goal = (TextView)findViewById(R.id.wednesday_goal);
        thursday_goal  = (TextView)findViewById(R.id.thurs_goal);
        friday_goal    = (TextView)findViewById(R.id.friday_goal);
        saturday_goal  = (TextView)findViewById(R.id.saturday_goal);
        sunday_btn     = (Button)findViewById(R.id.sunday_btn);
        monday_btn     = (Button)findViewById(R.id.monday_btn);
        tuesday_btn    = (Button)findViewById(R.id.tuesday_btn);
        wednesday_btn  = (Button)findViewById(R.id.wednesday_btn);
        thursday_btn   = (Button)findViewById(R.id.thursday_btn);
        friday_btn     = (Button)findViewById(R.id.friday_btn);
        saturday_btn   = (Button)findViewById(R.id.saturday_btn);
        reset_btn      = (Button)findViewById(R.id.reset_btn);

        mydb = new MyDB(this);

        // 초기화
        reset_btn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                sqlite = mydb.getWritableDatabase();
                mydb.onUpgrade(sqlite, 1, 2);   // 1번 버전 지우고 2번 버전 만들겠다.
                sqlite.close();
                Toast.makeText(getApplicationContext(), "이번주 일정이 초기화되었습니다." , Toast.LENGTH_SHORT).show();
            }
        });

        // 설정 버튼 각각 onClickListener
        sunday_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = createDialogBox(1);
                dialog.show();
            }
        });
        monday_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = createDialogBox(2);
                dialog.show();
            }
        });
        tuesday_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = createDialogBox(3);
                dialog.show();
            }
        });
        wednesday_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = createDialogBox(4);
                dialog.show();
            }
        });
        thursday_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = createDialogBox(5);
                dialog.show();
            }
        });
        friday_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = createDialogBox(6);
                dialog.show();
            }
        });
        saturday_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = createDialogBox(7);
                dialog.show();
            }
        });



        getGoal(1);
        sunday_goal.setText(today_goal);
        getGoal(2);
        monday_goal.setText(today_goal);
        getGoal(3);
        tuesday_goal.setText(today_goal);
        getGoal(4);
        wednesday_goal.setText(today_goal);
        getGoal(5);
        thursday_goal.setText(today_goal);
        getGoal(6);
        friday_goal.setText(today_goal);
        getGoal(7);
        saturday_goal.setText(today_goal);


        getDate();

    }

    // 데이터베이스로부터 각요일별로 운동 목표량을 받아와서 스트링에 저장한 후 onCreate에서 setText로 각 TextView의 내용 변경
    public void getGoal(int dayOfWeek){
        sqlite = mydb.getReadableDatabase();
        if(dayOfWeek == 1) {
            sql_goal = "SELECT * FROM goalone";
        }
        else if(dayOfWeek == 2){
            sql_goal = "SELECT * FROM goaltwo";
        }
        else if(dayOfWeek == 3){
            sql_goal = "SELECT * FROM goalthree";
        }
        else if(dayOfWeek == 4){
            sql_goal = "SELECT * FROM goalfour";
        }
        else if(dayOfWeek == 5){
            sql_goal = "SELECT * FROM goalfive";
        }
        else if(dayOfWeek == 6){
            sql_goal = "SELECT * FROM goalsix";
        }
        else if(dayOfWeek == 7){
            sql_goal = "SELECT * FROM goalseven";
        }

        // 테이블을 돌아다니면서 데이터를 읽어 올 Cursor
        Cursor cursor;
        cursor = sqlite.rawQuery(sql_goal, null);

        while (cursor.moveToNext()) {
            if(Integer.parseInt(cursor.getString(0)) == 1){
                difficulty ="상";
            }
            else if(Integer.parseInt(cursor.getString(0)) == 2){
                difficulty ="중";
            }
            else if(Integer.parseInt(cursor.getString(0))==3) {
                difficulty ="하";
            }
            today_goal = difficulty + "\n\n" + cursor.getString(1) + "\n\n" + cursor.getString(2) + "\n\n" + cursor.getString(3)
                    + "\n\n" + cursor.getString(4) + "\n\n" + cursor.getString(5) + "\n\n" + cursor.getString(6);
        }
        cursor.close();
        sqlite.close();
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

    // 운동 난이도에 따라 난수를 다르게 주어 각 운동의 횟수가 다르게 각 요일의 데이터베이스에 입력된다.
    private AlertDialog createDialogBox(final int dayOfWeek){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("난이도선택");
        builder.setMessage("운동 난이도를 선택하세요.");

        builder.setNegativeButton("중", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int pushup,chinup,situp,shoulder,rope,walk;
                pushup   =(int)(Math.random()*30)+70;         // 70~100개
                chinup   =(int)(Math.random()*10) +20;        // 20~30개
                situp    =(int)(Math.random()*50) +100;       // 100~150개
                shoulder =(int)(Math.random()*5) +15;         // 15~20분
                rope     =(int)(Math.random()*50) +200;       // 200~ 250회
                walk     =(int)(Math.random()*1000) +5000;    // 5000~6000걸음

                sqlite = mydb.getWritableDatabase();
                if(dayOfWeek == 1) {
                    sql_dialog = "INSERT INTO goalone(difficulty,pushup,chinup,situp,shoulder,rope,walk) " +
                            "VALUES('2', '" + pushup + "', '" + chinup + "', '" + situp + "', '" + shoulder + "', '" + rope + "', '" + walk + "') ";
                }
                else if(dayOfWeek == 2) {
                    sql_dialog = "INSERT INTO goaltwo(difficulty,pushup,chinup,situp,shoulder,rope,walk) " +
                            "VALUES('2', '" + pushup + "', '" + chinup + "', '" + situp + "', '" + shoulder + "', '" + rope + "', '" + walk + "') ";
                }
                else if(dayOfWeek == 3) {
                    sql_dialog = "INSERT INTO goalthree(difficulty,pushup,chinup,situp,shoulder,rope,walk) " +
                            "VALUES('2', '" + pushup + "', '" + chinup + "', '" + situp + "', '" + shoulder + "', '" + rope + "', '" + walk + "') ";
                }
                else if(dayOfWeek == 4) {
                    sql_dialog = "INSERT INTO goalfour(difficulty,pushup,chinup,situp,shoulder,rope,walk) " +
                            "VALUES('2', '" + pushup + "', '" + chinup + "', '" + situp + "', '" + shoulder + "', '" + rope + "', '" + walk + "') ";
                }
                else if(dayOfWeek == 5) {
                    sql_dialog = "INSERT INTO goalfive(difficulty,pushup,chinup,situp,shoulder,rope,walk) " +
                            "VALUES('2', '" + pushup + "', '" + chinup + "', '" + situp + "', '" + shoulder + "', '" + rope + "', '" + walk + "') ";
                }
                else if(dayOfWeek == 6) {
                    sql_dialog = "INSERT INTO goalsix(difficulty,pushup,chinup,situp,shoulder,rope,walk) " +
                            "VALUES('2', '" + pushup + "', '" + chinup + "', '" + situp + "', '" + shoulder + "', '" + rope + "', '" + walk + "') ";
                }
                else if(dayOfWeek == 7) {
                    sql_dialog = "INSERT INTO goalseven(difficulty,pushup,chinup,situp,shoulder,rope,walk) " +
                            "VALUES('2', '" + pushup + "', '" + chinup + "', '" + situp + "', '" + shoulder + "', '" + rope + "', '" + walk + "') ";
                }

                sqlite.execSQL(sql_dialog);
                sqlite.close();
                Toast.makeText(getApplicationContext(), "난이도 '중'으로 저장되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNeutralButton("상", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int pushup,chinup,situp,shoulder,rope,walk;
                pushup   =(int)(Math.random()*50)+100;       // 100~150개
                chinup   =(int)(Math.random()*10) +30;        // 30~40개
                situp    =(int)(Math.random()*50) +150;       // 150~199개
                shoulder =(int)(Math.random()*10) +20;        // 20~30분
                rope     =(int)(Math.random()*50) +300;       // 300~ 350회
                walk     =(int)(Math.random()*1000) +8000;    // 8000~9000걸음

                sqlite = mydb.getWritableDatabase();
                if(dayOfWeek == 1) {
                    sql_dialog = "INSERT INTO goalone(difficulty,pushup,chinup,situp,shoulder,rope,walk) " +
                            "VALUES('1', '" + pushup + "', '" + chinup + "', '" + situp + "', '" + shoulder + "', '" + rope + "', '" + walk + "') ";
                }
                else if(dayOfWeek == 2) {
                    sql_dialog = "INSERT INTO goaltwo(difficulty,pushup,chinup,situp,shoulder,rope,walk) " +
                            "VALUES('1', '" + pushup + "', '" + chinup + "', '" + situp + "', '" + shoulder + "', '" + rope + "', '" + walk + "') ";
                }
                else if(dayOfWeek == 3) {
                    sql_dialog = "INSERT INTO goalthree(difficulty,pushup,chinup,situp,shoulder,rope,walk) " +
                            "VALUES('1', '" + pushup + "', '" + chinup + "', '" + situp + "', '" + shoulder + "', '" + rope + "', '" + walk + "') ";
                }
                else if(dayOfWeek == 4) {
                    sql_dialog = "INSERT INTO goalfour(difficulty,pushup,chinup,situp,shoulder,rope,walk) " +
                            "VALUES('1', '" + pushup + "', '" + chinup + "', '" + situp + "', '" + shoulder + "', '" + rope + "', '" + walk + "') ";
                }
                else if(dayOfWeek == 5) {
                    sql_dialog = "INSERT INTO goalfive(difficulty,pushup,chinup,situp,shoulder,rope,walk) " +
                            "VALUES('1', '" + pushup + "', '" + chinup + "', '" + situp + "', '" + shoulder + "', '" + rope + "', '" + walk + "') ";
                }
                else if(dayOfWeek == 6) {
                    sql_dialog = "INSERT INTO goalsix(difficulty,pushup,chinup,situp,shoulder,rope,walk) " +
                            "VALUES('1', '" + pushup + "', '" + chinup + "', '" + situp + "', '" + shoulder + "', '" + rope + "', '" + walk + "') ";
                }
                else if(dayOfWeek == 7) {
                    sql_dialog = "INSERT INTO goalseven(difficulty,pushup,chinup,situp,shoulder,rope,walk) " +
                            "VALUES('1', '" + pushup + "', '" + chinup + "', '" + situp + "', '" + shoulder + "', '" + rope + "', '" + walk + "') ";
                }
                sqlite.execSQL(sql_dialog);
                sqlite.close();
                Toast.makeText(getApplicationContext(), "난이도 '상'으로 저장되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setPositiveButton("하", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int pushup,chinup,situp,shoulder,rope,walk;
                pushup   =(int)(Math.random()*10)+50;         // 50~60개
                chinup   =(int)(Math.random()*5) +10;         // 10~15개
                situp    =(int)(Math.random()*50) +50;        // 50~100개
                shoulder =(int)(Math.random()*5) +10;         // 10~15분
                rope     =(int)(Math.random()*50) +100;       // 100~150회
                walk     =(int)(Math.random()*500) +2500;     // 2500~3000걸음

                sqlite = mydb.getWritableDatabase();
                if(dayOfWeek == 1) {
                    sql_dialog = "INSERT INTO goalone(difficulty,pushup,chinup,situp,shoulder,rope,walk) " +
                            "VALUES('3', '" + pushup + "', '" + chinup + "', '" + situp + "', '" + shoulder + "', '" + rope + "', '" + walk + "') ";
                }
                else if(dayOfWeek == 2) {
                    sql_dialog = "INSERT INTO goaltwo(difficulty,pushup,chinup,situp,shoulder,rope,walk) " +
                            "VALUES('3', '" + pushup + "', '" + chinup + "', '" + situp + "', '" + shoulder + "', '" + rope + "', '" + walk + "') ";
                }
                else if(dayOfWeek == 3) {
                    sql_dialog = "INSERT INTO goalthree(difficulty,pushup,chinup,situp,shoulder,rope,walk) " +
                            "VALUES('3', '" + pushup + "', '" + chinup + "', '" + situp + "', '" + shoulder + "', '" + rope + "', '" + walk + "') ";
                }
                else if(dayOfWeek == 4) {
                    sql_dialog = "INSERT INTO goalfour(difficulty,pushup,chinup,situp,shoulder,rope,walk) " +
                            "VALUES('3', '" + pushup + "', '" + chinup + "', '" + situp + "', '" + shoulder + "', '" + rope + "', '" + walk + "') ";
                }
                else if(dayOfWeek == 5) {
                    sql_dialog = "INSERT INTO goalfive(difficulty,pushup,chinup,situp,shoulder,rope,walk) " +
                            "VALUES('3', '" + pushup + "', '" + chinup + "', '" + situp + "', '" + shoulder + "', '" + rope + "', '" + walk + "') ";
                }
                else if(dayOfWeek == 6) {
                    sql_dialog = "INSERT INTO goalsix(difficulty,pushup,chinup,situp,shoulder,rope,walk) " +
                            "VALUES('3', '" + pushup + "', '" + chinup + "', '" + situp + "', '" + shoulder + "', '" + rope + "', '" + walk + "') ";
                }
                else if(dayOfWeek == 7) {
                    sql_dialog = "INSERT INTO goalseven(difficulty,pushup,chinup,situp,shoulder,rope,walk) " +
                            "VALUES('3', '" + pushup + "', '" + chinup + "', '" + situp + "', '" + shoulder + "', '" + rope + "', '" + walk + "') ";
                }
                sqlite.execSQL(sql_dialog);
                sqlite.close();
                Toast.makeText(getApplicationContext(), "난이도 '하'로 저장되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });

        AlertDialog dialog = builder.create();
        return dialog;
    }
}
