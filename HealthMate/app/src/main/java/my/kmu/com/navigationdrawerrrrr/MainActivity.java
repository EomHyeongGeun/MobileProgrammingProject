package my.kmu.com.navigationdrawerrrrr;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.util.Calendar;
import android.icu.util.GregorianCalendar;
import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView today_exercise;
    MyDB myDB;
    SQLiteDatabase sqLiteDatabase;
    Calendar cal = new GregorianCalendar();
    int dayOfWeek;
    String sql_goal;
    String goal_str;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        today_exercise = (TextView)findViewById(R.id.today_exercise);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        // 무슨 요일인지에 따라 데이터베이스로부터 TimeTable.class에서 생성한 오늘의 목표량 받아오기
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
                goal_str = "       < 오늘의 목표량 > \n 팔굽혀펴기 : " + cursor.getString(1) + "\n 턱걸이 : " + cursor.getString(2) + "\n 윗몸일으키기 : " + cursor.getString(3)
                        + "\n 어깨운동(분) : " + cursor.getString(4) + "\n 줄넘기 : " + cursor.getString(5) + "\n 걷기(보):" + cursor.getString(6);
        }

        if(goal_str == null){
            today_exercise.setText("\nTimeTable에서 오늘의 운동 난이도를 선택해 주세요!\n");
        }
        else {
            today_exercise.setText(goal_str);
        }
        cursor.close();
        sqLiteDatabase.close();
        // 목표량 받아오기 끝

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_back) {
            return true;
        }
        if (id == R.id.action_home) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.navTimeTable) {
            Intent intent = new Intent(getApplicationContext(), TimeTableActivity.class);
            startActivity(intent);
        } else if (id == R.id.navBodyRecord) {
            Intent intent = new Intent(getApplicationContext(), BodyRecordActivity.class);
            startActivity(intent);
        } else if (id == R.id.navHowToDo) {
            Intent intent = new Intent(getApplicationContext(), HowToDoActivity.class);
            startActivity(intent);
        } else if (id == R.id.navPedometer) {
            Intent intent = new Intent(getApplicationContext(), PedometerActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
