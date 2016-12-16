package my.kmu.com.navigationdrawerrrrr;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by eomhyeong-geun on 2016. 11. 30..
 */

public class MyDB extends SQLiteOpenHelper {

    public MyDB(Context context) {
        super(context, "myDB", null, 1);    // 1번 버전으로 만들겠다.
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 각각 의 요일에 맞는 테이블 7개 생성
            String sql = "CREATE TABLE IF NOT EXISTS goalone"+
                    "(difficulty INTEGER" +
                    ",pushup INTEGER" +
                    ",chinup INTEGER" +
                    ",situp INTEGER" +
                    ",shoulder INTEGER" +
                    ",rope INTEGER" +
                    ",walk INTEGER" +
                    ");";
            db.execSQL(sql);
            sql = "CREATE TABLE IF NOT EXISTS goaltwo"+
                    "(difficulty INTEGER" +
                    ",pushup INTEGER" +
                    ",chinup INTEGER" +
                    ",situp INTEGER" +
                    ",shoulder INTEGER" +
                    ",rope INTEGER" +
                    ",walk INTEGER" +
                    ");";
            db.execSQL(sql);
            sql = "CREATE TABLE IF NOT EXISTS goalthree"+
                    "(difficulty INTEGER" +
                    ",pushup INTEGER" +
                    ",chinup INTEGER" +
                    ",situp INTEGER" +
                    ",shoulder INTEGER" +
                    ",rope INTEGER" +
                    ",walk INTEGER" +
                    ");";
            db.execSQL(sql);
            sql = "CREATE TABLE IF NOT EXISTS goalfour"+
                    "(difficulty INTEGER" +
                    ",pushup INTEGER" +
                    ",chinup INTEGER" +
                    ",situp INTEGER" +
                    ",shoulder INTEGER" +
                    ",rope INTEGER" +
                    ",walk INTEGER" +
                    ");";
            db.execSQL(sql);
            sql = "CREATE TABLE IF NOT EXISTS goalfive"+
                    "(difficulty INTEGER" +
                    ",pushup INTEGER" +
                    ",chinup INTEGER" +
                    ",situp INTEGER" +
                    ",shoulder INTEGER" +
                    ",rope INTEGER" +
                    ",walk INTEGER" +
                    ");";
            db.execSQL(sql);
            sql = "CREATE TABLE IF NOT EXISTS goalsix"+
                    "(difficulty INTEGER" +
                    ",pushup INTEGER" +
                    ",chinup INTEGER" +
                    ",situp INTEGER" +
                    ",shoulder INTEGER" +
                    ",rope INTEGER" +
                    ",walk INTEGER" +
                    ");";
            db.execSQL(sql);
            sql = "CREATE TABLE IF NOT EXISTS goalseven"+
                    "(difficulty INTEGER" +
                    ",pushup INTEGER" +
                    ",chinup INTEGER" +
                    ",situp INTEGER" +
                    ",shoulder INTEGER" +
                    ",rope INTEGER" +
                    ",walk INTEGER" +
                    ");";
            db.execSQL(sql);

        // BodyRecord의 list item에 사용할 DB
            sql = "CREATE TABLE IF NOT EXISTS photolist"+
                    "(_id INTEGER PRIMARY KEY AUTOINCREMENT" +
                    ", context TEXT, date TEXT, photo_url TEXT);";
            db.execSQL(sql);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS goalone";
        db.execSQL(sql);
        sql = "DROP TABLE IF EXISTS goaltwo";
        db.execSQL(sql);
        sql = "DROP TABLE IF EXISTS goalthree";
        db.execSQL(sql);
        sql = "DROP TABLE IF EXISTS goalfour";
        db.execSQL(sql);
        sql = "DROP TABLE IF EXISTS goalfive";
        db.execSQL(sql);
        sql = "DROP TABLE IF EXISTS goalsix";
        db.execSQL(sql);
        sql = "DROP TABLE IF EXISTS goalseven";
        db.execSQL(sql);
        sql = "DROP TABLE IF EXISTS photolist";
        db.execSQL(sql);
        onCreate(db);   // 다시 생성을 해줘야됨.
    }
}
