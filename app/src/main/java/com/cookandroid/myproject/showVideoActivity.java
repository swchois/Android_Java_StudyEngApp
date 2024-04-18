package com.cookandroid.myproject;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import androidx.appcompat.app.AppCompatActivity;

public class showVideoActivity extends AppCompatActivity {

    private WebView videoWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.showvideo);

        videoWebView = findViewById(R.id.videoWebView);
        String videoUrl = getIntent().getStringExtra("VIDEO_URL");

        WebSettings webSettings = videoWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        videoWebView.setWebChromeClient(new WebChromeClient());
        videoWebView.loadUrl(videoUrl);
    }
}

