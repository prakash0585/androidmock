package com.sabakuch.epaper.fragment;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.sabakuch.epaper.R;


public class payu extends Fragment
{
    private View rootView;
    private Activity context;
    WebView view;
    ProgressBar progressBar;
    public payu()
    {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        rootView =inflater.inflate(R.layout.fragment_payu, container, false);

        String url= "https://www.payumoney.com/react/app/merchant/#/pay/merchant/4272A8E430624969D26C39F5EC1919FC?param=5744452";
        view= (WebView) rootView.findViewById(R.id.WebView);
        progressBar = (ProgressBar)rootView. findViewById(R.id.progressBar1);
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
        return rootView;

    }






}
