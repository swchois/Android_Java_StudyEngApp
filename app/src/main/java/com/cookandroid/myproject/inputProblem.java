package com.cookandroid.myproject;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class inputProblem extends AppCompatActivity {

    private String DIRECTORY_NAME;
    private EditText q1, s1, s2, s3, s4, answer1, q2, s5, s6, s7, s8, answer2;
    private Button btnSave1, btnSave2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inputproblem);
        DIRECTORY_NAME = getIntent().getStringExtra("DIRECTORY_NAME");

        q1 = findViewById(R.id.q1);
        s1 = findViewById(R.id.s1);
        s2 = findViewById(R.id.s2);
        s3 = findViewById(R.id.s3);
        s4 = findViewById(R.id.s4);
        answer1 = findViewById(R.id.answer1);
        q2 = findViewById(R.id.q2);
        s5 = findViewById(R.id.s5);
        s6 = findViewById(R.id.s6);
        s7 = findViewById(R.id.s7);
        s8 = findViewById(R.id.s8);
        answer2 = findViewById(R.id.answer2);

        btnSave1 = findViewById(R.id.btn_save1);
        btnSave2 = findViewById(R.id.btn_save2);


        btnSave1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveProblem(1);
            }
        });

        btnSave2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveProblem(2);
            }
        });
    }

    private void saveProblem(int problemNumber) {
        String fileName = "daily_problem" + problemNumber + ".txt";
        File directory = new File(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), DIRECTORY_NAME);
        File file = new File(directory, fileName);

        try {
            FileWriter writer = new FileWriter(file, false);

            switch (problemNumber) {
                case 1:
                    writer.append(q1.getText().toString()).append("\n");
                    writer.append(s1.getText().toString()).append("\n");
                    writer.append(s2.getText().toString()).append("\n");
                    writer.append(s3.getText().toString()).append("\n");
                    writer.append(s4.getText().toString()).append("\n");
                    writer.append(answer1.getText().toString()).append("\n");
                    break;
                case 2:
                    writer.append(q2.getText().toString()).append("\n");
                    writer.append(s5.getText().toString()).append("\n");
                    writer.append(s6.getText().toString()).append("\n");
                    writer.append(s7.getText().toString()).append("\n");
                    writer.append(s8.getText().toString()).append("\n");
                    writer.append(answer2.getText().toString()).append("\n");
                    break;
                // 필요한 만큼 case를 추가하여 다른 문제들도 처리합니다.
            }

            writer.append("\n"); // 각 문제와 문제 사이에 공백 줄을 추가합니다.
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
