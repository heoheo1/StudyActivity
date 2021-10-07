package com.hj.studyactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class PictureActivtiy extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_activtiy);

        ImageView imageView =findViewById(R.id.galleryImage);
        ImageView imageBack  =findViewById(R.id.backButton);

        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Intent intent =getIntent();
        String str=intent.getStringExtra("uri");
        Uri uri =Uri.parse(str);
        if(uri!=null) {
            imageView.setImageURI(uri);
        }



    }
}