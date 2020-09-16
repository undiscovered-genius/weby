package com.example.browser;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import static android.webkit.WebSettings.FORCE_DARK_ON;

public class MainActivity extends AppCompatActivity {
    TextView urlText;
    WebView webView;
    ImageButton searchBtn;
    ImageButton backBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        urlText = findViewById(R.id.url_txt);
        webView = findViewById(R.id.web_browser);
        searchBtn = findViewById(R.id.search_btn);
        backBtn = findViewById(R.id.back_btn);

        webView.loadUrl("https://www.google.co.in/");
        WebSettings webSettings = webView.getSettings();

        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient());
        webSettings.setJavaScriptEnabled(true);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = urlText.getText().toString();
                if (url.isEmpty()){
                    Toast.makeText(MainActivity.this, "Please enter a URl!", Toast.LENGTH_SHORT).show();
                }else {
                    if (url.startsWith("https://www.")){
                        webView.loadUrl(url);
                    }else if (url.startsWith("www.")) {
                        webView.loadUrl("https://" + url);
                    }else{
                        if (url.contains(".")){
                            webView.loadUrl("https://www." + url);
                        }else{
                            webView.loadUrl("https://www." + url +".com");
                        }

                    }
                }
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                webView.goBack();
            }
        });
    }
    @Override
    public void onBackPressed(){
        if (webView.canGoBack()){
            webView.goBack();
        }else{
            super.onBackPressed();
        }
    }
}
