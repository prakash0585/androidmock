package com.sabakuch.epaper.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.sabakuch.epaper.R;
import com.sabakuch.epaper.apputils.CommonUtils;

public class SplashActivity extends Activity {
    private static final int SPLASH_TIME = 1000;
    private Intent intent;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        context = SplashActivity.this;
        CommonUtils.setTracking(SplashActivity.class.getSimpleName());
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                finish();


            }
        }, SPLASH_TIME);
    }


}
