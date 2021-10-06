package com.hj.studyactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton btn_fb;
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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