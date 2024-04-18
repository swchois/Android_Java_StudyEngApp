package com.cookandroid.myproject;

import android.content.Intent;
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

public class manage_study extends AppCompatActivity {
    private String selectedDateDirectoryName; // 선택된 날짜의 디렉토리 이름

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_study);
        ImageButton plus = findViewById(R.id.btnplus);
        ImageButton minus = findViewById(R.id.btnminus);
        TextView txtstate = findViewById(R.id.exist_homework);
        DatePicker calendar = findViewById(R.id.datePiker);

        Calendar cal = Calendar.getInstance();
        int cYear = cal.get(Calendar.YEAR);
        int cMonth = cal.get(Calendar.MONTH);
        int cDay = cal.get(Calendar.DAY_OF_MONTH);
        selectedDateDirectoryName = getFormattedDate(cYear, cMonth, cDay);
        checkAssignmentStatus(selectedDateDirectoryName, txtstate); // 과제 여부 확인하여 텍스트뷰에 출력

        calendar.init(cYear, cMonth, cDay, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int year, int monthofyear, int dayofmonth) {
                // 선택된 날짜의 디렉토리 이름을 설정
                selectedDateDirectoryName = getFormattedDate(year, monthofyear, dayofmonth);
                // 해당 날짜에 따른 과제 여부를 확인하여 출력할 수 있도록 구현
                checkAssignmentStatus(selectedDateDirectoryName, txtstate);

            }
        });

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedDateDirectoryName != null && !selectedDateDirectoryName.isEmpty()) {
                    Intent intent = new Intent(getApplicationContext(), dailycontentActivity.class);
                    intent.putExtra("DIRECTORY_NAME", selectedDateDirectoryName);
                    startActivity(intent);
                }
            }
        });

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedDateDirectoryName != null && !selectedDateDirectoryName.isEmpty()) {
                    // 선택된 날짜의 디렉토리에서 과제를 삭제하는 작업 수행
                    deleteAssignment(selectedDateDirectoryName);
                    // 삭제 후 메시지 업데이트
                    txtstate.setText("생성된 과제가 없습니다.");
                }
            }
        });
    }

    // 선택된 날짜를 원하는 형식으로 포맷팅하는 메서드
    private String getFormattedDate(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        return dateFormat.format(calendar.getTime());
    }

    // 선택된 날짜의 과제 여부를 확인하여 TextView에 출력하는 메서드
    private void checkAssignmentStatus(String directoryName, TextView txtstate) {
        // 디렉토리 경로 설정
        File directory = new File(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), directoryName);

        if (directory.exists() && directory.isDirectory()) {
            // 디렉토리가 존재하고, 파일이 있는지 확인
            File[] files = directory.listFiles();
            if (files != null && files.length > 0) {
                // 디렉토리 안에 파일이 존재하면 과제가 있다고 출력
                txtstate.setText("과제가 생성되어 있습니다.");
            } else {
                // 파일이 없으면 과제가 없다고 출력
                txtstate.setText("생성된 과제가 없습니다.");
            }
        } else {
            // 디렉토리가 존재하지 않으면 과제가 없다고 출력
            txtstate.setText("생성된 과제가 없습니다.");
        }
    }

    // 선택된 날짜의 과제를 삭제하는 메서드
    private void deleteAssignment(String directoryName) {
        // 해당 날짜의 디렉토리에서 과제를 삭제하는 작업 수행
        File directory = new File(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), directoryName);
        if (directory.exists() && directory.isDirectory()) {
            // 디렉토리가 존재하면 해당 디렉토리와 그 내부의 파일들을 삭제
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    file.delete();
                }
            }
            // 디렉토리 삭제
            directory.delete();
        }
    }
}
