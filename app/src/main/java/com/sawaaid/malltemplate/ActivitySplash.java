package com.sawaaid.malltemplate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

public class ActivitySplash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        startActivityMainDelay();
    }

    private void startActivityMainDelay() {
        new Handler(Looper.getMainLooper()).postDelayed(this::startMainActivity, 3000);
    }

    private void startMainActivity() {
        Intent i = new Intent(ActivitySplash.this, ActivityMain.class);
        startActivity(i);
        finish();
    }
}