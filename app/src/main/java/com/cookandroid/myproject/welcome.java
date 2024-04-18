package com.cookandroid.myproject;
//firstscreen 관리자 초기화면과 관련있는 클래스
// WelcomeActivity.java

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class welcome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.firstscreen);
        ImageButton managestudent = findViewById(R.id.managestudent);
        ImageButton managetask = findViewById(R.id.managetask);
        ImageButton searchnews = findViewById(R.id.searchnews);
        ImageButton searchvideo = findViewById(R.id.searchmedia);

        managestudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), infostudent.class);
                startActivity(intent);
            }
        });
        managetask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), manage_study.class);
                startActivity(intent);
            }
        });
        searchnews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://bbc.com"));
                startActivity(mIntent);
            }
        });
        searchvideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://m.youtube.com"));
                startActivity(mIntent);
            }
        });
        String username = getIntent().getStringExtra("username");

        TextView welcomeTextView = findViewById(R.id.txtwelcome);
        welcomeTextView.setText("어서오세요 " + username + "님!");
        welcomeTextView.setTextSize(18);
        welcomeTextView.setTextColor(Color.BLACK);
    }

}
