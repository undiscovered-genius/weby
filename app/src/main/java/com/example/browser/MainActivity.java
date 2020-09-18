package com.example.browser;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    TextView urlText;
    WebView webView;
    ImageButton searchBtn;
    ImageButton backBtn;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        urlText = findViewById(R.id.url_txt);
        webView = findViewById(R.id.web_browser);
        searchBtn = findViewById(R.id.search_btn);
        backBtn = findViewById(R.id.back_btn);
        progressBar = findViewById(R.id.progressBar);

        webView.loadUrl("https://www.google.co.in/");
        final WebSettings webSettings = webView.getSettings();

        progressBar.setVisibility(View.VISIBLE);
        webView.setWebChromeClient(new WebChromeClient(){
            public void onProgressChanged(WebView view, int newProgress){
                // Update the progress bar with page loading progress
                progressBar.setProgress(newProgress);
                if(newProgress == 100){
                    // Hide the progressbar
                    progressBar.setVisibility(View.GONE);
                }
            }
        });

        webView.setWebViewClient(new WebViewClient());
        webSettings.setJavaScriptEnabled(true);
//        webView.addJavascriptInterface(new WebAppInterface(this), "Android");

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = urlText.getText().toString();
//                webSettings.setJavaScriptEnabled(true);
                if (url.isEmpty()){
                    Toast.makeText(MainActivity.this, "Please enter a URl!", Toast.LENGTH_SHORT).show();
                }else {
                    if (url.startsWith("https://www.")){
                        webView.loadUrl(url);
                        Toast.makeText(MainActivity.this, "Loading WEBSITE!", Toast.LENGTH_SHORT).show();
                        urlText.setText("");
                    }else if (url.startsWith("www.")) {
                        webView.loadUrl("https://" + url);
                        Toast.makeText(MainActivity.this, "Loading WEBSITE!", Toast.LENGTH_SHORT).show();
                        urlText.setText("");
                    }else{
                        if (url.contains(".")){
                            webView.loadUrl("https://www." + url);
                            Toast.makeText(MainActivity.this, "Loading WEBSITE!", Toast.LENGTH_SHORT).show();
                            urlText.setText("");
                        }else{
                            webView.loadUrl("https://www." + url +".com");
                            Toast.makeText(MainActivity.this, "Loading WEBSITE!", Toast.LENGTH_SHORT).show();
                            urlText.setText("");
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
    public class WebAppInterface {
        Context mContext;

        /** Instantiate the interface and set the context */
        WebAppInterface(Context c) {
            mContext = c;
        }

        /** Show a toast from the web page */
        @JavascriptInterface
        public void showToast(String toast) {
            Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
        }
    }
}
