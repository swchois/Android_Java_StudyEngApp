package com.cookandroid.myproject;

import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class student_daily extends AppCompatActivity {
    private String selectedDateDirectoryName; // 선택된 날짜의 디렉토리 이름

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.studentdaily_study);
        TextView txtstate = findViewById(R.id.exist_homework1);
        DatePicker calendar = findViewById(R.id.datePiker1);

        Calendar cal = Calendar.getInstance();
        int cYear = cal.get(Calendar.YEAR);
        int cMonth = cal.get(Calendar.MONTH);
        int cDay = cal.get(Calendar.DAY_OF_MONTH);

        selectedDateDirectoryName = getFormattedDate(cYear,cMonth , cDay);
        // 해당 날짜에 따른 과제 여부를 확인하여 출력할 수 있도록 구현
        checkAssignmentStatus(selectedDateDirectoryName, txtstate);

        calendar.init(cYear, cMonth, cDay, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int year, int monthofyear, int dayofmonth) {
                // 선택된 날짜의 디렉토리 이름을 설정
                selectedDateDirectoryName = getFormattedDate(year, monthofyear, dayofmonth);
                // 해당 날짜에 따른 과제 여부를 확인하여 출력할 수 있도록 구현
                checkAssignmentStatus(selectedDateDirectoryName, txtstate);

            }
        });
        String username = getIntent().getStringExtra("username");

        TextView welcomeTextView = findViewById(R.id.txtwelcome1);
        welcomeTextView.setText("어서오세요 " + username + "님!");
        welcomeTextView.setTextSize(18);
        welcomeTextView.setTextColor(Color.BLACK);
    }
    // 선택된 날짜의 과제 여부를 확인하여 TextView에 출력하는 메서드
    private void checkAssignmentStatus (String directoryName, TextView txtstate){
        // 디렉토리 경로 설정
        ImageButton startBtn=(ImageButton)findViewById(R.id.startBtn);
        File directory = new File(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), directoryName);

        if (directory.exists() && directory.isDirectory()) {
            // 디렉토리가 존재하고, 파일이 있는지 확인
            File[] files = directory.listFiles();
            if (files != null && files.length > 0) {
                // 디렉토리 안에 파일이 존재하면 과제가 있다고 출력
                txtstate.setText("과제가 있습니다.");
                startBtn.setVisibility(View.VISIBLE);
                startBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(), dostudy.class);
                        intent.putExtra("DIRECTORY_NAME", selectedDateDirectoryName);
                        startActivity(intent);
                    }
                });
            } else {
                // 파일이 없으면 과제가 없다고 출력
                txtstate.setText("과제가 없습니다.");
                startBtn.setVisibility(View.INVISIBLE);
            }
        } else {
            // 디렉토리가 존재하지 않으면 과제가 없다고 출력
            txtstate.setText("과제가 없습니다.");
            startBtn.setVisibility(View.INVISIBLE);
        }
    }
    private String getFormattedDate ( int year, int month, int day){
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        return dateFormat.format(calendar.getTime());
    }
}
