package com.hj.studyactivity;

import androidx.appcompat.app.AppCompatActivity;

import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton btn_fb;
    Intent intent;
    RecyclerView recyclerView;
    ArrayList itemArrayList;
    String cursorEmail;
    String  databaseName ="MemberDB";
    SQLiteDatabase db;
    MyAdapter adapter;
    DBHelper dbHelper;
    Cursor cursor;
    SharedPreferences sharedPreferences;
    String spemail;
    long pressedTime =0 ;
    String spsubject;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView =findViewById(R.id.recyclerView);


        itemArrayList =new ArrayList();
        adapter =new MyAdapter(this,itemArrayList);
        sharedPreferences = getSharedPreferences("pref", MODE_PRIVATE);
        spemail = sharedPreferences.getString("email", "");
        spsubject =sharedPreferences.getString("subject","");
        dbHelper =new DBHelper(this,databaseName,null,1,"member"+spemail);
        db =dbHelper.getWritableDatabase();


        Log.d("dkdkdk", spemail);
        String query = "SELECT * FROM " +"member"+spemail; //테이블을 조회
        cursor = db.rawQuery(query, null); //가리킨다. rawQuery 커서를 가리킨다.
        while (cursor.moveToNext()) {//while문을 통해 커서를 하나씩 하나씩 가르킨다.
            String image =cursor.getString(1);
            String title =cursor.getString(2);
            String content =cursor.getString(3);
            String subject =cursor.getString(4);
            Log.d("hhjhj",spsubject+","+subject);
            if(subject.equals(spsubject)) {
                itemArrayList.add(new Item(title, content, image));
                recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

        }

        btn_fb=findViewById(R.id.btn_fb);

        btn_fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                intent =new Intent(getApplicationContext(),WriteActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }
        });
    }
    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {

        Intent intent =new Intent(getApplicationContext(),SubjectActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);


    }

}