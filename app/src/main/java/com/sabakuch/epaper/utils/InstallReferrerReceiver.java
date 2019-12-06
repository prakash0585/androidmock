package com.sabakuch.epaper.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.sabakuch.epaper.apputils.CommonUtils;

public class InstallReferrerReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String referrer = intent.getStringExtra("referrer");
        CommonUtils.LogMsg(InstallReferrerReceiver.class.getSimpleName(),"referrer: "+ referrer);
        //Use the referrer
    }
}