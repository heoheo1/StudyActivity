package com.hj.studyactivity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SingUpActivity extends AppCompatActivity {
    DBHelper databaseHelper;
    SQLiteDatabase db;
    String  databaseName ="MemberDB";
    String  table ="Membertbl";
    int version = 1;
    EditText edt_SignUpEmail,edt_SignUpPassword,edt_SignUpPassword2;
    Button btn_SignUp;
    String cursorEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_up);
        edt_SignUpEmail=findViewById(R.id.edt_SignUpEmail);
        edt_SignUpPassword=findViewById(R.id.edt_SignUpPassword);
        edt_SignUpPassword2=findViewById(R.id.edt_SignUpPassword2);
        btn_SignUp=findViewById(R.id.btn_SignUp);
        databaseHelper =new DBHelper(this,databaseName,null,version,table);
        db =databaseHelper.getWritableDatabase();
        Log.d("SignUpTableName",table);

        btn_SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = edt_SignUpEmail.getText().toString(); //email을 String 형으로 변경
                String password = edt_SignUpPassword.getText().toString(); // password을 String 형으로 변경
                String passwordCheck = edt_SignUpPassword2.getText().toString();// passwordcheck을 String 형으로 변경

                String query = "SELECT * FROM " + table; //테이블을 조회
                Cursor cursor = db.rawQuery(query, null); //가리킨다. rawQuery 커서를 가리킨다.
                while (cursor.moveToNext()) {//while문을 통해 커서를 하나씩 하나씩 가르킨다.
                    cursorEmail = cursor.getString(0);// db에 저장할때 0번 인덱스 즉 email 을 확인
                    Log.d("d", cursorEmail);
                    if (cursorEmail.equals(email)) { //커서 이메일과 사용자가 입력한 이메일이 동일한지 확인
                        Toast.makeText(SingUpActivity.this, "이미 존재하는 이메일 입니다.", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        if (email.isEmpty()) { //이메일이 비었으면
                            Toast.makeText(SingUpActivity.this, "이메일을 입력해주세요", Toast.LENGTH_SHORT).show();
                            return;
                        } else if (password.isEmpty() || passwordCheck.isEmpty()) { //패스워드 둘중 하나라도 비었으면
                            Toast.makeText(SingUpActivity.this, "비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show();
                            return;
                        } else if (!(password.equals(passwordCheck))) {//패스워드랑 패스워드확인이랑 다를경우
                            Toast.makeText(SingUpActivity.this, "비밀번호를 확인해주세요.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                }
                ContentValues contentValues = new ContentValues(); //map과 같은형태 번들형식 키와 값으로 이루어져있다. 사용될 데이터를 저장하는 객체
                contentValues.put("Email", email);
                contentValues.put("password", password);
                db.insert(table, null, contentValues); //테이블에 데이터를 생성
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class); //데이터를 생성후 Login 화면으로 이동
                startActivity(intent);
                finish();

            }
        });

    }
    @Override
    protected void onDestroy() {
        databaseHelper.close();
        super.onDestroy();
    }




}
