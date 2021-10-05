package com.hj.studyactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

public class WriteActivity extends AppCompatActivity {
    EditText edt_title,edt_contents;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        edt_title =findViewById(R.id.edt_title);
        edt_contents=findViewById(R.id.edt_contents);


    }
}