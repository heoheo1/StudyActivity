package com.hj.studyactivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class SubjectActivity extends AppCompatActivity implements View.OnClickListener {

    String  databaseName ="MemberDB";
    SharedPreferences sharedPreferences;
    DBHelper dbHelper;
    String spemail;
    SQLiteDatabase db;
    String subject;
    long pressedTime =0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);

        findViewById(R.id.btn_speech).setOnClickListener(this);
        findViewById(R.id.btn_math).setOnClickListener(this);
        findViewById(R.id.btn_english).setOnClickListener(this);
        findViewById(R.id.btn_gluttony).setOnClickListener(this);
        findViewById(R.id.btn_satam).setOnClickListener(this);

        sharedPreferences = getSharedPreferences("pref", MODE_PRIVATE);
        spemail = sharedPreferences.getString("email", "");
        dbHelper =new DBHelper(this,databaseName,null,1,"member"+spemail);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case (R.id.btn_speech) :
                subject="국어";
                subject(subject);
                break;

            case (R.id.btn_math) :
                subject="수학";
                subject(subject);
                break;

            case (R.id.btn_english) :
                subject="영어";
                subject(subject);
                break;

            case (R.id.btn_gluttony) :
                subject="과탐";
                subject(subject);
                break;

            case (R.id.btn_satam) :
                subject="사탐";
                subject(subject);
                break;


        }
    }

    public void subject(String subject){
        db =dbHelper.getWritableDatabase();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class); //데이터를 생성후 Login 화면으로 이동
        sharedPreferences =getSharedPreferences("pref",MODE_PRIVATE);
        SharedPreferences.Editor editor= sharedPreferences.edit();
        editor.putString("subject",subject);
        editor.commit();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }


    @Override
    public void onBackPressed() {
        if ( pressedTime == 0 ) {
            Toast.makeText(SubjectActivity.this, " 한 번 더 누르면 종료됩니다." , Toast.LENGTH_LONG).show();
            pressedTime = System.currentTimeMillis();
        }
        else {
            int seconds = (int) (System.currentTimeMillis() - pressedTime);

            if ( seconds > 2000 ) {
                Toast.makeText(SubjectActivity.this, " 한 번 더 누르면 종료됩니다." , Toast.LENGTH_LONG).show();
                pressedTime = 0 ;
            }
            else {
                ActivityCompat.finishAffinity(this);
                System.exit(0);
            }
        }
    }
}