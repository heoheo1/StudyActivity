package com.hj.studyactivity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.renderscript.ScriptGroup;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.Inet4Address;

public class WriteActivity extends AppCompatActivity {
    EditText edt_title,edt_contents;
    Button btn_OK;
    SQLiteDatabase db;
    ImageView imageView;
    String spemail,spsubject;
    Uri selectedImageUri;
    String  databaseName ="MemberDB";
    SharedPreferences sharedPreferences;
    Cursor cursor;
    int count=0;
    Uri a;
    int id;
    String title;
    boolean ima=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);
        edt_title =findViewById(R.id.edt_title);
        edt_contents=findViewById(R.id.edt_contents);
        btn_OK=findViewById(R.id.btn_OK);
        imageView=findViewById(R.id.imageView);
        sharedPreferences = getSharedPreferences("pref", MODE_PRIVATE);
        spemail = sharedPreferences.getString("email", "");
        spsubject = sharedPreferences.getString("subject", "");
        DBHelper dbHelper =new DBHelper(this,databaseName,null,1,"member"+spemail);
        db=dbHelper.getWritableDatabase();
        Intent intent=getIntent();
        String i =intent.getStringExtra("titl");
        String query = "SELECT * FROM " +"member"+spemail; //???????????? ??????
        cursor = db.rawQuery(query, null); //????????????. rawQuery ????????? ????????????.
        while (cursor.moveToNext()) {//while?????? ?????? ????????? ????????? ????????? ????????????.

                String image =cursor.getString(1);
                String title =cursor.getString(2);
                String content =cursor.getString(3);
                String subject =cursor.getString(4);
                Log.d("hhjhj",spsubject+","+subject);
                if(title.equals(i)) {
                    id =cursor.getInt(0);
                    edt_title.setText(title);
                    edt_contents.setText(content);
                    imageView.setImageURI(Uri.parse(image));
                    a =Uri.parse(image);
                    count=1;
                }
        }

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
                startActivityForResult(intent,0);
                ima=true;


            }
        });

        btn_OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    String title = edt_title.getText().toString();
                    String contents = edt_contents.getText().toString();
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("Image", String.valueOf(selectedImageUri));
                    contentValues.put("Title", title);
                    contentValues.put("Contents", contents);
                    contentValues.put("Subject", spsubject);
                    if(count==0) {
                        db.insert("member" + spemail, null, contentValues); //???????????? ???????????? ??????
                    }else if(count ==1){
                        if(ima){
                            contentValues.put("Image", String.valueOf(selectedImageUri));
                            ima=false;
                        }else{
                            contentValues.put("Image", String.valueOf(a));
                        }
                        db.update("member"+spemail,contentValues,"id="+id,null);
                        count=0;
                    }
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class); //???????????? ????????? Login ???????????? ??????
                    startActivity(intent);
                    finish();



            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                try {

                    selectedImageUri =data.getData();
                    Glide.with(getApplicationContext()).load(selectedImageUri).into(imageView);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else if (resultCode == RESULT_CANCELED) {
            Toast.makeText(this, "?????? ?????? ??????", Toast.LENGTH_LONG).show();
        }
    }

}