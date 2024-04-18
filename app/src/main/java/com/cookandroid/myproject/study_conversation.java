package com.cookandroid.myproject;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Locale;

public class study_conversation extends AppCompatActivity implements TextToSpeech.OnInitListener {

    private String DIRECTORY_NAME;
    private String[] fileNames = {"daily_conversation01.txt", "daily_conversation02.txt", "daily_conversation03.txt", "daily_conversation04.txt"};
    private String[] AudioNames = {"audio01.3gp", "audio02.3gp", "audio03.3gp", "audio04.3gp"};
    private int currentIndex = 0;
    private TextView maintv;
    private ImageButton btnNext;
    private ImageButton btnPrev;
    private ImageButton btnListen;
    private TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.study_conversation);

        DIRECTORY_NAME = getIntent().getStringExtra("DIRECTORY_NAME");
        maintv = findViewById(R.id.maintv);
        btnNext = findViewById(R.id.btnNext);
        btnPrev = findViewById(R.id.btnPrev);
        btnListen = findViewById(R.id.btn_listen);

        textToSpeech = new TextToSpeech(this, this);

        displayFileContent(fileNames[currentIndex]);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentIndex++;
                if (currentIndex < fileNames.length) {
                    displayFileContent(fileNames[currentIndex]);
                } else {
                    finish();
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
                    currentIndex=0;
                }
            }
        });
        btnListen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkAudioFileExists(AudioNames[currentIndex])) {
                    playAudio(AudioNames[currentIndex]);
                    Toast.makeText(getApplicationContext(),"녹음파일 실행",Toast.LENGTH_SHORT).show();
                } else {
                    readTextFile(fileNames[currentIndex]);
                    Toast.makeText(getApplicationContext(),"텍스트를 음성으로 변환하여 재생합니다.",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void displayFileContent(String fileName) {
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
        }

        maintv.setText(content.toString());
    }

    private boolean checkAudioFileExists(String audioFileName) {
        File directory = new File(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), DIRECTORY_NAME);
        File audioFile = new File(directory, audioFileName);
        return audioFile.exists();
    }

    private void playAudio(String audioFileName) {
        File directory = new File(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), DIRECTORY_NAME);
        File audioFile = new File(directory, audioFileName);

        MediaPlayer mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(audioFile.getAbsolutePath());
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "음성 파일을 재생하는 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
        }
    }

    private void readTextFile(String fileName) {
        File directory = new File(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), DIRECTORY_NAME);
        File file = new File(directory, fileName);

        StringBuilder content = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                // 한국어 문장은 건너뜁니다.
                if (!isKorean(line)) {
                    content.append(line).append("\n");
                }
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        speakText(content.toString());
    }

    // 문자열이 한국어인지 확인합니다.
    private boolean isKorean(String text) {
        for (char c : text.toCharArray()) {
            if (Character.UnicodeBlock.of(c) == Character.UnicodeBlock.HANGUL_SYLLABLES) {
                return true;
            }
        }
        return false;
    }

    private void speakText(String text) {
        textToSpeech.setLanguage(Locale.US);
        textToSpeech.setPitch(1.0f);
        textToSpeech.setSpeechRate(1.0f);
        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = textToSpeech.setLanguage(Locale.US); // 영어로 설정

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(getApplicationContext(), "영어를 지원하지 않습니다.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "TTS 초기화에 실패했습니다.", Toast.LENGTH_SHORT).show();
            Log.e("TTS", "TTS 초기화에 실패했습니다. Error code: " + status); // 여기에 로그 추가
        }
    }

    @Override
    protected void onDestroy() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onDestroy();
    }
}
