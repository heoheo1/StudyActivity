package com.hj.studyactivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton btn_fb;
    Intent intent;
    RecyclerView recyclerView;
    ArrayList itemArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView =findViewById(R.id.recyclerView);

        String str="content://media/external/images/media/281";

        Uri uri =Uri.parse(str);

        itemArrayList =new ArrayList();
        itemArrayList.add(new Item("ㅇㅇ","내용 :ㅇㅇㅇ",uri));

        MyAdapter adapter =new MyAdapter(itemArrayList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);



        btn_fb=findViewById(R.id.btn_fb);

        btn_fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent =getIntent();
                String cursorEmail = intent.getStringExtra("email");
                intent =new Intent(getApplicationContext(),WriteActivity.class);
                intent.putExtra("email",cursorEmail);
                startActivity(intent);

            }
        });
    }

    @Override
    public void onBackPressed() {


    }

}