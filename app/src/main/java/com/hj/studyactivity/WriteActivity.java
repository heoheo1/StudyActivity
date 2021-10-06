package com.hj.studyactivity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
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
    String email;
    Uri selectedImageUri;
    String  databaseName ="MemberDB";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);
        edt_title =findViewById(R.id.edt_title);
        edt_contents=findViewById(R.id.edt_contents);
        btn_OK=findViewById(R.id.btn_OK);
        imageView=findViewById(R.id.imageView);
        Intent intent=getIntent();
        email =intent.getStringExtra("email");
        Log.d("hhj", email);
        DBHelper dbHelper =new DBHelper(this,databaseName,null,1,"member"+email);
        db=dbHelper.getWritableDatabase();

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
                startActivityForResult(intent,0);

            }
        });

        btn_OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title=edt_title.getText().toString();
                String contents=edt_contents.getText().toString();
                ContentValues contentValues = new ContentValues();
                contentValues.put("Image", String.valueOf(selectedImageUri));
                contentValues.put("Title",title);
                contentValues.put("Contents",contents);
                Log.d("hhj", String.valueOf(selectedImageUri)+","+title+","+contents+","+email);
                db.insert("member"+email, null, contentValues); //테이블에 데이터를 생성
                Intent intent = new Intent(getApplicationContext(), MainActivity.class); //데이터를 생성후 Login 화면으로 이동
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
            Toast.makeText(this, "사진 선택 취소", Toast.LENGTH_LONG).show();
        }
    }

    private void setImage(Uri uri) {
        try{
            InputStream in = getContentResolver().openInputStream(uri);
            Bitmap bitmap = BitmapFactory.decodeStream(in);
            imageView.setImageBitmap(bitmap);
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }
}