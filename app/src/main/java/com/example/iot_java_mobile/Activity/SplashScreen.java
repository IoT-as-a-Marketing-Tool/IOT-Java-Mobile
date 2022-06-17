package com.example.iot_java_mobile.Activity;

import androidx.appcompat.app.AppCompatActivity;
import com.example.iot_java_mobile.Domain.SessionManager;
import com.example.iot_java_mobile.R;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.iot_java_mobile.R;

public class SplashScreen extends AppCompatActivity {
    private Handler handler;
    private Runnable callback;
    private SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        handler = new Handler();
        sessionManager= new SessionManager(this);
        callback = new Runnable() {
            @Override
            public void run() {
                sessionManager.checkLogin();
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        };
        handler.postDelayed(callback, 3000);
    }
}