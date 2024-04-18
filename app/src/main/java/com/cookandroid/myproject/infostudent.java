package com.cookandroid.myproject;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.fonts.FontStyle;
import android.os.Bundle;

import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;


// ... (이전 코드)

public class infostudent extends AppCompatActivity {
    myDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.infostudent);
        dbHelper = new myDBHelper(this);
        displayStudents();
    }

    private void displayStudents() {
        ArrayList<String[]> studentList = retrieveStudentsFromDB(); // String 배열로 수정

        TableLayout tableLayout = findViewById(R.id.tableLayout); // 테이블 레이아웃

        for (String[] studentInfo : studentList) {
            TableRow row = new TableRow(this);

            for (String info : studentInfo) {
                TextView textView = new TextView(this);
                textView.setText(info);
                textView.setPadding(8, 8, 8, 8); // 패딩 설정
                textView.setGravity(50);
                textView.setBackgroundResource(R.drawable.table_inside);

                row.addView(textView); // 텍스트 뷰를 테이블 로우에 추가
            }

            tableLayout.addView(row); // 테이블 레이아웃에 로우 추가
        }
    }

    private ArrayList<String[]> retrieveStudentsFromDB() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        ArrayList<String[]> studentList = new ArrayList<>();

        String[] projection = {"s_name", "s_id", "s_progress"};
        Cursor cursor = db.query("STUDENT", projection, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int nameIndex = cursor.getColumnIndex("s_name");
                int idIndex = cursor.getColumnIndex("s_id");
                int progressIndex = cursor.getColumnIndex("s_progress");

                if (nameIndex != -1 && idIndex != -1 && progressIndex != -1) {
                    String sName = cursor.getString(nameIndex);
                    String sId = cursor.getString(idIndex);
                    String sProgress = cursor.getString(progressIndex);

                    String[] studentInfo = {sName, sId, sProgress}; // 문자열 배열로 변경

                    studentList.add(studentInfo);
                }
            } while (cursor.moveToNext());
            cursor.close();
        }

        db.close();
        return studentList;
    }

    // ... (이후 코드)
}

