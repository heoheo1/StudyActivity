package com.hj.studyactivity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    Button login_button;
    TextView text_SignUp;
    EditText edt_email,edt_password;
    SQLiteDatabase db;
    String  databaseName ="MemberDB";
    String  table ="Membertbl";
    String password;
    String cursorEmail,cursorPasswod;
    SharedPreferences sharedPreferences;
    int version = 1;
    String email;
    DBHelper databaseHelper;
    long pressedTime =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login_button= findViewById(R.id.login_button);
        text_SignUp= findViewById(R.id.text_SignUp);
        edt_email=findViewById(R.id.edt_email);
        edt_password=findViewById(R.id.edt_password);

        databaseHelper =new DBHelper(this,databaseName,null,version,table);
        db =databaseHelper.getWritableDatabase();


        ActivityCompat.requestPermissions(this,new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE
        },0);


        text_SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getApplicationContext(),SingUpActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }
        });

        login_button.setOnClickListener(v -> { //onClick 메서드에 view 매개변수를 표현한 람다식
            //함수형 인터페이스(인터페이스가 딱 한개의 메서드만 가지고 있을때)
            //그때 그 메서드의 매개변수만 적고 화살표로 작성한다면 이 인터페이스의 이메서드를 가리키는거다
            //프로젝트를 할때 A가 데이터를 만들어내서 B 가 사용해야한다면 A가 작업이 끝날때까지 기다려야한다.
            //하지만 인터페이스를 통해서 그 데이터를 넣어둔다면 B는 인터페이스를 통해서 데이터를 활용할수있어 바로작업가능
            //B업체가 데이터를 만들어내야하고 A업체는 그 데이터를 받아서 사용해야한다면 B업체가 작업이 끝날때까지
            //A업체는 기다릴수 밖에 없다. 그럼 시간낭비이다.그러니 서로 약속을 하여 인터페이스를 만들어서
            //Data를 이런 형식으로 넣어달라고 요구하고 A업체는 그 인터페이스가 구현되면 아직 만들어지지 않은 Data라도 미리 활용 가능
            //나는 Data가 필요한거다.(여기에 물건을 넣어놔 내가 찾으로 갈꺼닌까 나는 그물건을 찾아서 활용만하면 끝)
           email =edt_email.getText().toString();
           password=edt_password.getText().toString();
            String query = "SELECT * FROM " + table; //테이블을 조회
            Cursor cursor = db.rawQuery(query, null); //가리킨다. rawQuery 커서를 가리킨다.
            while (cursor.moveToNext()) {//while문을 통해 커서를 하나씩 하나씩 가르킨다.
                cursorEmail = cursor.getString(0);// db에 저장할때 0번 인덱스 즉 email 을 확인
                cursorPasswod = cursor.getString(1);
                Log.d("yousin", cursorEmail + "," + email);
                if (cursorEmail.equals(email)) {
                    if (cursorPasswod.equals(password)) {
                        db.execSQL("CREATE TABLE IF NOT EXISTS " + "member" + email + "(" //시작이 숫자로 되면안되서 member라고 임의로 넣어주었다dur
                                + " id INTEGER PRIMARY KEY AUTOINCREMENT,image BLOB,title TEXT,contents TEXT,subject TEXT)"); //테이블이 존재하지않으면 테이블 생성
                        Intent intent = new Intent(getApplicationContext(), SubjectActivity.class);
                        sharedPreferences =getSharedPreferences("pref",MODE_PRIVATE);
                        SharedPreferences.Editor editor= sharedPreferences.edit();
                        editor.putString("email",cursorEmail);
                        editor.commit();
                        startActivity(intent);
                        finish();
                        break;
                    }

                } else if (email.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "이메일을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                } else if (password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            if(cursorEmail==null) {
                Toast.makeText(LoginActivity.this, "회원가입을 해주세요.", Toast.LENGTH_SHORT).show();
            }else if (!(cursorEmail.equals(email))) {
                Toast.makeText(LoginActivity.this, "존재하지 않는 아이디 입니다.", Toast.LENGTH_SHORT).show();
            }
        });
        }

    @Override
    protected void onDestroy() {
        databaseHelper.close();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if ( pressedTime == 0 ) {
            Toast.makeText(LoginActivity.this, " 한 번 더 누르면 종료됩니다." , Toast.LENGTH_LONG).show();
            pressedTime = System.currentTimeMillis();
        }
        else {
            int seconds = (int) (System.currentTimeMillis() - pressedTime);

            if ( seconds > 2000 ) {
                Toast.makeText(LoginActivity.this, " 한 번 더 누르면 종료됩니다." , Toast.LENGTH_LONG).show();
                pressedTime = 0 ;
            }
            else {
                ActivityCompat.finishAffinity(this);
                System.exit(0);
            }
        }
    }


}