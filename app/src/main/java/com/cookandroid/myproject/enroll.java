package com.cookandroid.myproject;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class enroll extends Activity {
    myDBHelper myhelper;
    EditText enroll_name, enroll_id, enroll_passwd, checkcode;
    Button btnReturn;
    Button enroll;
    SQLiteDatabase sqlDB;
    RadioButton rdostudent,rdoteacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enroll);

        myhelper = new myDBHelper(this);
        enroll_name = findViewById(R.id.enroll_name);
        enroll_id = findViewById(R.id.enroll_id);
        enroll_passwd = findViewById(R.id.enroll_passwd);
        btnReturn = findViewById(R.id.btnReturn);
        enroll = findViewById(R.id.enroll);
        rdostudent = findViewById(R.id.rdostudent);
        rdoteacher = findViewById(R.id.rdoteacher);
        checkcode = findViewById(R.id.checkcode);
        rdoteacher.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(rdoteacher.isChecked()==true){
                    checkcode.setVisibility(View.VISIBLE);
                }else{
                    checkcode.setVisibility(View.INVISIBLE);
                }

            }
        });
        enroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rdoteacher.isChecked() == true && checkcode.getText().toString().equals("777")) {
                    try {
                        sqlDB = myhelper.getWritableDatabase();
                        String name = enroll_name.getText().toString();
                        String id = enroll_id.getText().toString();
                        String passwd = enroll_passwd.getText().toString();
                        String code = checkcode.getText().toString();
                        sqlDB.execSQL("INSERT INTO TEACHER VALUES ('" + name + "', '" + id + "', '" + passwd+"','777');");

                        sqlDB.close();
                        Toast.makeText(enroll.this, "입력됨", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(enroll.this, "오류 발생: " + e.getMessage(), Toast.LENGTH_LONG).show(); // 예외 메시지 출력
                    }
                } else if (rdoteacher.isChecked() == true) {
                    Toast.makeText(enroll.this, "checkcode가 틀렸습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        sqlDB = myhelper.getWritableDatabase();
                        String name = enroll_name.getText().toString();
                        String id = enroll_id.getText().toString();
                        String passwd = enroll_passwd.getText().toString();

                        sqlDB.execSQL("INSERT INTO STUDENT VALUES ('" + name + "', '" + id + "', '" + passwd + "', '0');");

                        sqlDB.close();
                        Toast.makeText(enroll.this, "입력됨", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(enroll.this, "오류 발생: " + e.getMessage(), Toast.LENGTH_LONG).show(); // 예외 메시지 출력
                    }
                }
            }
        });
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


}
