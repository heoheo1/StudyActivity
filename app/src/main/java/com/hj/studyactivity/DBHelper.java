package com.hj.studyactivity;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import javax.security.auth.Destroyable;

class DBHelper extends SQLiteOpenHelper {
    String table;

    public DBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version,String table) {
        super(context, name, factory, version);
        this.table=table;
    }

    @Override
    public void onCreate(SQLiteDatabase db) { //처음에 한번만 실행
        db.execSQL("CREATE TABLE IF NOT EXISTS " +table + "("
                + " email TEXT PRIMARY KEY,password TEXT)"); //테이블이 존재하지않으면 테이블 생성
        Log.d("DbClassTableName",table);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.disableWriteAheadLogging();
    }



}