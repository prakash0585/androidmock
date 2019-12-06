package com.sabakuch.epaper.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.sabakuch.epaper.R;
import com.sabakuch.epaper.constants.Constants;

/**
 * Created by dell on 01-Apr-17.
 */
public class WebViewFragment extends Fragment  {
    String url;

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View rooView = inflater.inflate(R.layout.fragment_webview,container,false);
        if(getArguments()!=null){

            url = getArguments().getString(Constants.URL);

        }
        WebView webview = (WebView) rooView.findViewById(R.id.wvMain);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.loadUrl("http://drive.google.com/viewerng/viewer?embedded=true&url=" + url);

        return rooView;
    }
}
