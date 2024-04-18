package com.cookandroid.myproject;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;

public class inputConver extends AppCompatActivity {

    private void checkRecordPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, RECORD_AUDIO_PERMISSION_CODE);
        }
    }

    private TextToSpeech textToSpeech;
    private String DIRECTORY_NAME;
    private MediaRecorder mediaRecorder;
    private static final int RECORD_AUDIO_PERMISSION_CODE = 101;
    private boolean isRecording = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inputconver);

        // EditText와 Button들을 가져옴
        EditText edtEng1 = findViewById(R.id.edtEng1);
        EditText edtKor1 = findViewById(R.id.edtKor1);
        Button save01 = findViewById(R.id.save01);
        Button save02 = findViewById(R.id.save02);
        Button save03 = findViewById(R.id.save03);
        Button save04 = findViewById(R.id.save04);
        Button rec01 = findViewById(R.id.rec01);
        EditText edtEng2 = findViewById(R.id.edtEng2);
        EditText edtKor2 = findViewById(R.id.edtKor2);
        Button rec02 = findViewById(R.id.rec02);
        EditText edtEng3 = findViewById(R.id.edtEng3);
        EditText edtKor3 = findViewById(R.id.edtKor3);
        Button rec03 = findViewById(R.id.rec03);
        EditText edtEng4 = findViewById(R.id.edtEng4);
        EditText edtKor4 = findViewById(R.id.edtKor4);
        Button rec04 = findViewById(R.id.rec04);

        // 디렉터리 이름 가져오기
        DIRECTORY_NAME = getIntent().getStringExtra("DIRECTORY_NAME");

        // TextToSpeech 초기화
        textToSpeech = new TextToSpeech(getApplicationContext(), status -> {
            if (status != TextToSpeech.ERROR) {
                textToSpeech.setLanguage(Locale.US);
            }
        });

        // 음성파일 저장 버튼에 대한 클릭 리스너 설정
        save01.setOnClickListener(view -> {
            // EditText에서 사용자가 입력한 텍스트를 읽음
            String engText = edtEng1.getText().toString().trim();
            String korText = edtKor1.getText().toString().trim();

            // 저장될 파일명 설정
            String fileName = "daily_conversation01.txt";

            // 텍스트를 파일로 저장
            writeToFile(fileName, engText + "\n" + korText);

        });
        rec01.setOnClickListener(view -> {
            if (!isRecording) {
                startRecording("audio01.3gp");
                rec01.setText("녹음 중지");
            } else {
                stopRecording();
                rec01.setText("녹음 시작");
                Toast.makeText(getApplicationContext(),"음성파일이 저장되었습니다",Toast.LENGTH_SHORT).show();
            }
            isRecording = !isRecording;
        });

        save02.setOnClickListener(view -> {
            // EditText에서 사용자가 입력한 텍스트를 읽음
            String engText = edtEng2.getText().toString().trim();
            String korText = edtKor2.getText().toString().trim();

            // 저장될 파일명 설정
            String fileName = "daily_conversation02.txt";

            // 텍스트를 파일로 저장
            writeToFile(fileName, engText + "\n" + korText);

        });
        rec02.setOnClickListener(view -> {
            if (!isRecording) {
                startRecording("audio02.3gp");
                rec01.setText("녹음 중지");
            } else {
                stopRecording();
                rec01.setText("녹음 시작");
                Toast.makeText(getApplicationContext(),"음성파일이 저장되었습니다",Toast.LENGTH_SHORT).show();
            }
            isRecording = !isRecording;
        });
        save03.setOnClickListener(view -> {
            // EditText에서 사용자가 입력한 텍스트를 읽음
            String engText = edtEng3.getText().toString().trim();
            String korText = edtKor3.getText().toString().trim();

            // 저장될 파일명 설정
            String fileName = "daily_conversation03.txt";

            // 텍스트를 파일로 저장
            writeToFile(fileName, engText + "\n" + korText);

        });
        rec03.setOnClickListener(view -> {
            if (!isRecording) {
                startRecording("audio03.3gp");
                rec01.setText("녹음 중지");
            } else {
                stopRecording();
                rec01.setText("녹음 시작");
                Toast.makeText(getApplicationContext(),"음성파일이 저장되었습니다",Toast.LENGTH_SHORT).show();
            }
            isRecording = !isRecording;
        });
        save04.setOnClickListener(view -> {
            // EditText에서 사용자가 입력한 텍스트를 읽음
            String engText = edtEng4.getText().toString().trim();
            String korText = edtKor4.getText().toString().trim();

            // 저장될 파일명 설정
            String fileName = "daily_conversation04.txt";

            // 텍스트를 파일로 저장
            writeToFile(fileName, engText + "\n" + korText);

        });
        rec04.setOnClickListener(view -> {
            if (!isRecording) {
                startRecording("audio04.3gp");
                rec01.setText("녹음 중지");
            } else {
                stopRecording();
                rec01.setText("녹음 시작");
                Toast.makeText(getApplicationContext(),"음성파일이 저장되었습니다",Toast.LENGTH_SHORT).show();
            }
            isRecording = !isRecording;
        });
    }

    private void startRecording(String fileName) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, RECORD_AUDIO_PERMISSION_CODE);
        } else {
            File directory = new File(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), DIRECTORY_NAME);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            File file = new File(directory, fileName);

            mediaRecorder = new MediaRecorder();
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mediaRecorder.setOutputFile(file.getAbsolutePath());

            try {
                mediaRecorder.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }

            mediaRecorder.start();
        }
    }

    private void stopRecording() {
        if (mediaRecorder != null) {
            mediaRecorder.stop();
            mediaRecorder.release();
            mediaRecorder = null;
        }
    }

    private void writeToFile(String fileName, String content) {
        // 파일을 저장할 디렉토리 설정
        File directory = new File(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), DIRECTORY_NAME);

        // 디렉토리가 없으면 생성
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // 파일 생성 및 내용 저장
        File file = new File(directory, fileName);
        try {
            FileWriter writer = new FileWriter(file);
            writer.write(content);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // TextToSpeech 종료
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
    }
}
