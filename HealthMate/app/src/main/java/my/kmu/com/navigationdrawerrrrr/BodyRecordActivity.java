package my.kmu.com.navigationdrawerrrrr;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by eomhyeong-geun on 2016. 11. 20..
 */

public class BodyRecordActivity extends Activity {
    Button resetbody_btn, upload_btn;
    MyDB myDB;
    SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.body_record);
        ListView listView = (ListView)findViewById(R.id.listView);
        ListViewAdapter adapter = new ListViewAdapter();
        myDB = new MyDB(this);

        listView.setAdapter(adapter);

        // DB 초기화 버튼
        resetbody_btn = (Button)findViewById(R.id.resetbody_btn);
        resetbody_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqLiteDatabase = myDB.getWritableDatabase();
                myDB.onUpgrade(sqLiteDatabase, 1, 2);   // 1번 버전 지우고 2번 버전 만들겠다.
                sqLiteDatabase.close();
                Toast.makeText(getApplicationContext(), "이번주 일정이 초기화되었습니다." , Toast.LENGTH_SHORT).show();
            }
        });

        // UploadActivity 호출하는 글 올리기 버튼
        upload_btn = (Button)findViewById(R.id.upload_btn);
        upload_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), UploadActivity.class);
                startActivity(intent);
            }
        });

        // adapter에 DB내용 장착 후 리스트뷰 띄우기
        sqLiteDatabase = myDB.getReadableDatabase();

        String sql_str = "SELECT * FROM photolist";
        Cursor cursor;
        cursor = sqLiteDatabase.rawQuery(sql_str, null);
        while (cursor.moveToNext()) {
            adapter.addItem(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3));
        }
        cursor.close();
        sqLiteDatabase.close();

    }
}
