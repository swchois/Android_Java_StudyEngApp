package com.cookandroid.myproject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class dostudy extends AppCompatActivity {
    String DIRECTORY_NAME;
    private VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dostudy_student);
        DIRECTORY_NAME = getIntent().getStringExtra("DIRECTORY_NAME");
        final String[] kindsofstudy = {"일일 회화 공부하기", "일일 문제 풀기", "일일 영어 듣기"};
        final String[] kindsofname = {"conversation", "problem", "video"};
        ListView list = findViewById(R.id.listview);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, kindsofstudy);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedFileName = "daily_" + kindsofname[i].replaceAll(" ", "_").toLowerCase() + ".txt";
                if (kindsofname[i].equals("conversation")) {
                    Intent intent = new Intent(dostudy.this, study_conversation.class);
                    intent.putExtra("DIRECTORY_NAME", DIRECTORY_NAME);
                    startActivity(intent);
                }
                if (kindsofname[i].equals("problem")){
                    Intent intent = new Intent(dostudy.this, study_problem.class);
                    intent.putExtra("DIRECTORY_NAME", DIRECTORY_NAME);
                    startActivity(intent);
                }

                if (kindsofname[i].equals("video")) {
                    String videoUrl = readFromFile(selectedFileName);
                    if (videoUrl != null) {
                        Intent intent = new Intent(dostudy.this, showVideoActivity.class);
                        intent.putExtra("VIDEO_URL", videoUrl);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), "URL을 찾을 수 없습니다.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
    private String readFromFile(String fileName) {
        File directory = new File(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), DIRECTORY_NAME);
        File file = new File(directory, fileName);

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
            return null;
        }
        return content.toString();
    }

}