package com.sabakuch.epaper.application;

import android.content.Context;
import android.net.http.RequestQueue;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.sabakuch.epaper.R;
import com.sabakuch.epaper.apputils.CommonUtils;
import com.sabakuch.epaper.data.Contact;

import java.util.ArrayList;

/**
 * Created by dell on 02-Aug-17.
 */
public class SabakuchEmockApplication extends MultiDexApplication {
    /**
     * The Constant TAG.
     */
    private static final String TAG = SabakuchEmockApplication.class.getSimpleName();
    /**
     * The Context .
     */
    public static Context context;
    public static int permission = 0;
    public static ArrayList<Contact> contacts = new ArrayList<Contact>();
    /**
     * The instance.
     */
    private static SabakuchEmockApplication _instance = null;
    private static GoogleAnalytics sAnalytics;
    private static Tracker sTracker;
    int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 0;
    private RequestQueue mRequestQueue;
    /**
     * The _is app in background.
     */
    private boolean _isAppInBackground = false;

    /**
     * Gets the default {@link Tracker} .
     *
     * @return tracker
     */
    static synchronized public Tracker getDefaultTracker() {
        // To enable debug logging use: adb shell setprop log.tag.GAv4 DEBUG
        if (sTracker == null) {
            sTracker = sAnalytics.newTracker(R.xml.global_tracker);
        }

        return sTracker;
    }

    /**
     * Gets the app instance.
     *
     * @return the app instance
     */
    public static SabakuchEmockApplication getAppInstance() {
        return _instance;
    }

    /*
     * (non-Javadoc)
     *
     * @see android.app.Application#onCreate()
     */
    @Override
    public void onCreate() {
//		Fresco.initialize(context);
        super.onCreate();
        _instance = this;
        context = getApplicationContext();
        MultiDex.install(this);

        sAnalytics = GoogleAnalytics.getInstance(this);
        CommonUtils.LogMsg(TAG, "onCreate--");
        // initialize the AdMob app
        MobileAds.initialize(this, getString(R.string.admob_app_id));
    }

    public void handleUncaughtException(Thread thread, Throwable e) {
        e.printStackTrace(); // not all Android versions will print the stack
        // trace automatically

        // Intent intent = new Intent ();
        // intent.setAction ("com.mydomain.SEND_LOG"); // see step 5.
        // intent.setFlags (Intent.FLAG_ACTIVITY_NEW_TASK); // required when
        // starting from Application
        // startActivity (intent);

        System.exit(1); // kill off the crashed app
    }

}
