package com.cookandroid.myproject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class dailycontentActivity extends AppCompatActivity {

    String DIRECTORY_NAME; // 새로운 디렉토리 이름

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enroll_study);

        DIRECTORY_NAME = getIntent().getStringExtra("DIRECTORY_NAME");

        Button dayConversationBtn = findViewById(R.id.dayconversation);
        Button dayProblemBtn = findViewById(R.id.dayproblem);
        Button dayVideoBtn = findViewById(R.id.dayvideo);

        dayConversationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), inputConver.class);
                intent.putExtra("DIRECTORY_NAME",DIRECTORY_NAME); // 사용자 이름을 전달
                startActivity(intent);
            }
        });

        dayProblemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), inputProblem.class);
                intent.putExtra("DIRECTORY_NAME",DIRECTORY_NAME); // 사용자 이름을 전달
                startActivity(intent);
            }
        });

        dayVideoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInputDialog("일일 추천 링크 영상 입력", "daily_video.txt");
            }
        });
    }

    private void showInputDialog(String title, final String fileName) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);

        final EditText input = new EditText(this);
        builder.setView(input);

        builder.setPositiveButton("확인", (dialog, which) -> {
            String content = input.getText().toString();
            createDailyContent(fileName, content);
        });

        builder.setNegativeButton("취소", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    private void createDailyContent(String fileName, String content) {
        File directory = new File(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), DIRECTORY_NAME);

        if (!directory.exists()) {
            directory.mkdirs();
        }

        writeToFile(new File(directory, fileName), content);
        Toast.makeText(this, "일일 내용이 생성되었습니다.", Toast.LENGTH_SHORT).show();
    }

    private void writeToFile(File file, String content) {
        try {
            FileWriter writer = new FileWriter(file);
            writer.write(content);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
