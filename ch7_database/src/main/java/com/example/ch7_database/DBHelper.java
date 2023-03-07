package com.example.ch7_database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context){
        // db file명을 변수처리해서 여러개 작업처리 가능
        // 상위 클래스에 db version 명시함 (현재 1)
        super(context, "testdb", null,1);
    }

    //app install될때 최초에 한번만 불림 수정해도 다시 실행안됨
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //table 준비
        // 잘 아는 sql create 문 넣으면 됨
        sqLiteDatabase.execSQL("create table tb_memo("+
                "_id integer primary key autoincrement,"+
                "memo not null)");

    }

    //version이 변경될때마다 호출됨
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
