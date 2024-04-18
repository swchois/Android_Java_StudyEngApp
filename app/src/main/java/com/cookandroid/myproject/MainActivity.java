package com.cookandroid.myproject;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    myDBHelper dbHelper;
    RadioButton rdostudent, rdoteacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        setTitle("LifeIngles");

        dbHelper = new myDBHelper(this);

        Button btnEnroll = findViewById(R.id.btnEnroll);
        Button btnLogin = findViewById(R.id.btnlogin);
        EditText editid = findViewById(R.id.edtid);
        EditText editpasswd = findViewById(R.id.edtpwd);
        rdostudent = findViewById(R.id.rdostudent);
        rdoteacher = findViewById(R.id.rdoteacher);
        editid.setHint("아이디를 입력하세요");
        editpasswd.setHint("비밀번호를 입력하세요");
        editpasswd.setTransformationMethod(new PasswordTransformationMethod());
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = editid.getText().toString().trim();
                String passwd = editpasswd.getText().toString().trim();

                if (rdoteacher.isChecked()) {
                    if (loginTeacher(id, passwd)) {
                        String teachername = getTeacherName(id,passwd);
                        Toast.makeText(MainActivity.this, "선생님 로그인 성공", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), welcome.class);
                        intent.putExtra("username", teachername); // 사용자 이름을 전달
                        startActivity(intent);
                    } else {
                        Toast.makeText(MainActivity.this, "선생님 아이디 또는 비밀번호가 올바르지 않습니다.", Toast.LENGTH_SHORT).show();
                    }
                } else if (rdostudent.isChecked()) {
                    if (loginUser(id, passwd)) {
                        Toast.makeText(MainActivity.this, "학생 로그인 성공", Toast.LENGTH_SHORT).show();
                        String studentname = getStudentName(id,passwd);
                        Intent intent = new Intent(getApplicationContext(), student_daily.class);
                        intent.putExtra("username", studentname); // 사용자 이름을 전달
                        startActivity(intent);
                    } else {
                        Toast.makeText(MainActivity.this, "학생 아이디 또는 비밀번호가 올바르지 않습니다.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "선택된 사용자 유형이 없습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnEnroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), enroll.class);
                startActivity(intent);
            }
        });
    }

    private boolean loginUser(String id, String passwd) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selection = "s_id = ? AND s_passwd = ?";
        String[] selectionArgs = {id, passwd};
        Cursor cursor = db.query("STUDENT", null, selection, selectionArgs, null, null, null);
        boolean loggedIn = cursor != null && cursor.getCount() > 0;

        if (cursor != null) {
            cursor.close();
        }
        db.close();

        return loggedIn;
    }

    private boolean loginTeacher(String id, String passwd) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selection = "teacher_id = ? AND teacher_passwd = ?";
        String[] selectionArgs = {id, passwd};
        Cursor cursor = db.query("TEACHER", null, selection, selectionArgs, null, null, null);
        boolean loggedIn = cursor != null && cursor.getCount() > 0;

        if (cursor != null) {
            cursor.close();
        }
        db.close();

        return loggedIn;
    }
    private String getTeacherName(String id, String password) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String name = "";

        String[] projection = {"teacher_name"};
        String selection = "teacher_id = ? AND teacher_passwd = ?";
        String[] selectionArgs = {id, password};

        Cursor cursor = db.query("TEACHER", projection, selection, selectionArgs, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            int nameColumnIndex = cursor.getColumnIndex("teacher_name");
            if (nameColumnIndex != -1) {
                name = cursor.getString(nameColumnIndex);
            }
            cursor.close();
        }

        db.close();
        return name;
    }
    private String getStudentName(String id, String password) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String name = "";

        String[] projection = {"s_name"};
        String selection = "s_id = ? AND s_passwd = ?";
        String[] selectionArgs = {id, password};

        Cursor cursor = db.query("STUDENT", projection, selection, selectionArgs, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            int nameColumnIndex = cursor.getColumnIndex("s_name");
            if (nameColumnIndex != -1) {
                name = cursor.getString(nameColumnIndex);
            }
            cursor.close();
        }

        db.close();
        return name;
    }

}