package com.cookandroid.myproject;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class study_problem extends AppCompatActivity {

    private String DIRECTORY_NAME;
    private String[] fileNames = {"daily_problem1.txt", "daily_problem2.txt"}; // 파일 이름 배열
    private int currentIndex = 0; // 현재 파일 인덱스
    private ImageButton btnNext;
    private ImageButton btnPrev;
    private TextView q1, no1, no2, no3, no4, checkAnswer;
    private ImageView img1,img2,img3,img4,wrong,right;
    private ImageButton btn1, btn2, btn3, btn4;
    private Button btnCheckAnswer;
    private int answer=0;
    private int selectedAnswer = 0;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.study_problem);

        DIRECTORY_NAME = getIntent().getStringExtra("DIRECTORY_NAME");
        img1 = findViewById(R.id.img1);
        img2 = findViewById(R.id.img2);
        img3 = findViewById(R.id.img3);
        img4 = findViewById(R.id.img4);
        wrong = findViewById(R.id.wrong);
        right = findViewById(R.id.right);
        q1 = findViewById(R.id.q1);
        no1 = findViewById(R.id.no1);
        no2 = findViewById(R.id.no2);
        no3 = findViewById(R.id.no3);
        no4 = findViewById(R.id.no4);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
        btnCheckAnswer = findViewById(R.id.btnCheck);
        checkAnswer = findViewById(R.id.checkanswer);
        btnNext = findViewById(R.id.btnNext);
        btnPrev = findViewById(R.id.btnPrev);

        displayFileContent(fileNames[currentIndex]);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedAnswer = 1; // 1번 버튼 선택
                img1.setVisibility(View.VISIBLE);
                img2.setVisibility(View.INVISIBLE);
                img3.setVisibility(View.INVISIBLE);
                img4.setVisibility(View.INVISIBLE);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedAnswer = 2; // 2번 버튼 선택
                img1.setVisibility(View.INVISIBLE);
                img2.setVisibility(View.VISIBLE);
                img3.setVisibility(View.INVISIBLE);
                img4.setVisibility(View.INVISIBLE);
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedAnswer = 3; // 3번 버튼 선택
                img1.setVisibility(View.INVISIBLE);
                img2.setVisibility(View.INVISIBLE);
                img3.setVisibility(View.VISIBLE);
                img4.setVisibility(View.INVISIBLE);
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img1.setVisibility(View.INVISIBLE);
                img2.setVisibility(View.INVISIBLE);
                img3.setVisibility(View.INVISIBLE);
                img4.setVisibility(View.VISIBLE);
                selectedAnswer = 4; // 4번 버튼 선택
            }
        });

        btnCheckAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer();
            }
        });


        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentIndex++;
                if (currentIndex < fileNames.length) {
                    displayFileContent(fileNames[currentIndex]);
                } else {
                    Toast.makeText(getApplicationContext(),"마지막 페이지입니다.",Toast.LENGTH_SHORT).show();
                    currentIndex = fileNames.length - 1;
                }
            }
        });

        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentIndex--;
                if (currentIndex >= 0) {
                    displayFileContent(fileNames[currentIndex]);
                } else {
                    Toast.makeText(getApplicationContext(),"첫번째 페이지입니다.",Toast.LENGTH_SHORT).show();
                    currentIndex = 0;
                }
            }
        });
    }

    private void displayFileContent(String fileName) {
        File directory = new File(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), DIRECTORY_NAME);
        File file = new File(directory, fileName);
        wrong.setVisibility(View.INVISIBLE);
        right.setVisibility(View.INVISIBLE);
        img1.setVisibility(View.INVISIBLE);
        img2.setVisibility(View.INVISIBLE);
        img3.setVisibility(View.INVISIBLE);
        img4.setVisibility(View.INVISIBLE);
        StringBuilder content = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                content.append(line).append("\n");
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] contentArray = content.toString().split("\n");
        q1.setText(contentArray[0]); // 문제 설정
        no1.setText(contentArray[1]); // 1번 선택지 설정
        no2.setText(contentArray[2]); // 2번 선택지 설정
        no3.setText(contentArray[3]); // 3번 선택지 설정
        no4.setText(contentArray[4]); // 4번 선택지 설정
        answer = Integer.parseInt(contentArray[5]);
    }
    private void checkAnswer() {
        String result;
        if (selectedAnswer == answer) {
            result = "정답입니다!";
            right.setVisibility(View.VISIBLE);
            wrong.setVisibility(View.INVISIBLE);
        } else {
            result = "오답입니다!";
            right.setVisibility(View.INVISIBLE);
            wrong.setVisibility(View.VISIBLE);
        }
        checkAnswer.setText(result);
    }
}

