package com.cookandroid.myproject;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class myDBHelper extends SQLiteOpenHelper {
    public myDBHelper(Context context) {
        super(context, "projectDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE STUDENT (s_name TEXT, s_id TEXT PRIMARY KEY, s_passwd TEXT, s_progress TEXT DEFAULT '0');");
        db.execSQL("CREATE TABLE TEACHER (teacher_name TEXT, teacher_id TEXT PRIMARY KEY, teacher_passwd TEXT,teacher_code TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS STUDENT");
        db.execSQL("DROP TABLE IF EXISTS TEACHER");
        onCreate(db);
    }
    // 학생 정보 가져오기
    public String getStudentInfo(String studentID) {
        SQLiteDatabase db = this.getReadableDatabase();
        String info = "";

        Cursor cursor = db.rawQuery("SELECT * FROM STUDENT WHERE s_id=?", new String[]{studentID});
        if (cursor.moveToFirst()) {
            info += "Name: " + cursor.getString(0) + "\n";
            info += "ID: " + cursor.getString(1) + "\n";
            // Add other fields as needed
        }
        cursor.close();
        db.close();
        return info;
    }

    // 선생님 정보 가져오기
    public String getTeacherInfo(String teacherID) {
        SQLiteDatabase db = this.getReadableDatabase();
        String info = "";

        Cursor cursor = db.rawQuery("SELECT * FROM TEACHER WHERE teacher_id=?", new String[]{teacherID});
        if (cursor.moveToFirst()) {
            info += "Name: " + cursor.getString(0) + "\n";
            info += "ID: " + cursor.getString(1) + "\n";
            // Add other fields as needed
        }
        cursor.close();
        db.close();
        return info;
    }

}
