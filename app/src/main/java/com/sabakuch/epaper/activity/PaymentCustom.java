package com.sabakuch.epaper.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sabakuch.epaper.R;
import com.sabakuch.epaper.constants.Constants;

public class PaymentCustom extends AppCompatActivity {

    WebView view;
    ProgressBar progressBar;
    String slug;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_web);
        String sessionId= getIntent().getStringExtra(Constants.EXAM_ID);
         slug= getIntent().getStringExtra(Constants.SLUG);
        setupToolbar();

        String url= "https://sabakuch.com/elearning/mock/"+slug+"/custom";
        view= (WebView) this.findViewById(R.id.WebView);
        progressBar = (ProgressBar) findViewById(R.id.progressBar1);
        final WebViewClient client = new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                // TODO Auto-generated method stub
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                // TODO Auto-generated method stub
                super.onPageFinished(view, url);
                progressBar.setVisibility(View.GONE);
            }

        };

        WebSettings settings = view.getSettings();
        settings.setDomStorageEnabled(true);
        settings.setJavaScriptEnabled(true);
        view.setWebViewClient(client);
        view.loadUrl(url);

    }

    private void setupToolbar()

    {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setIcon(0);
        getSupportActionBar().setTitle("");

        TextView mTitle = (TextView) mToolbar.findViewById(R.id.tv_title);
        mTitle.setVisibility(View.VISIBLE);
        mTitle.setText(slug);
    }


    @Override
    public void onBackPressed(){

        showAppExitDialog();

    }
/*
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && view.canGoBack()) {
            view.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }*/

    protected void showAppExitDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(PaymentCustom.this);
        //builder.setTitle("CANCEL PAYMENT");
        builder.setMessage("Are you sure you want to cancel your test?");
        builder.setCancelable(true);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // PaymentCustom.super.onBackPressed();
                startActivity(new Intent(PaymentCustom.this,MainActivity.class));
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }


}