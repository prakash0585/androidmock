package com.sabakuch.epaper.activity;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.google.firebase.messaging.FirebaseMessaging;
import com.sabakuch.epaper.R;
import com.sabakuch.epaper.application.SabakuchEmockApplication;
import com.sabakuch.epaper.apputils.CommonUtils;
import com.sabakuch.epaper.constants.Constants;
import com.sabakuch.epaper.entitymime.MultipartEntity;
import com.sabakuch.epaper.fragment.ExamList;
import com.sabakuch.epaper.fragment.OnlineTestPaperSectionFragment;
import com.sabakuch.epaper.fragment.ProfileSettingFragmentNew;
import com.sabakuch.epaper.fragment.ResultFragment;
import com.sabakuch.epaper.fragment.SelectExamsFragment;
import com.sabakuch.epaper.fragment.SettingsFragment;
import com.sabakuch.epaper.notification.Config;
import com.sabakuch.epaper.notification.NotificationUtils;


public class MainActivity extends AppCompatActivity implements View.OnClickListener/*FragmentDrawer.FragmentDrawerListener,View.OnClickListener,ServiceInterface*/ {
    private WebView webView;

    private static String TAG1 = MainActivity.class.getSimpleName();
    int MY_PERMISSIONS_REQUEST_READ_PHONE_STATE = 1;
    int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 2;
    int MY_PERMISSIONS_REQUEST_GET_ACCOUNTS = 3;
    GPSTracker gps;
    private static final int REQUEST_CODE_PERMISSION = 2;
    //private AdView mAdView;
   // InterstitialAd mInterstitialAd;
    //    DatabaseHandler db;
//    String email;
//    String name;
//    String id;
//    String phoneNo;
//    ContentResolver cr;
    private Toolbar mToolbar;
    private String strUserID, strServerKey, strUserName;
    private MultipartEntity reqEntity;
    private LinearLayout llStatistics, llHome, llNotification, llSettings;
    private ImageView ivHome, ivStatistics, ivNotification, ivSettings;
    private int footerSelColor, footerUnSelColor;
    private TextView tvHome, tvStatistics, tvNotification, tvSettings;
    private static final String TAG = MainActivity.class.getSimpleName();
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // checking for type intent filter
                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
                    // gcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications
                    FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);

                    displayFirebaseRegId();

                } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                    // new push notification is received

                    String message = intent.getStringExtra("message");

                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();

                    // txtMessage.setText(message);
                }
            }
        };

        displayFirebaseRegId();

        try {
            if (ActivityCompat.checkSelfPermission(this
                    , Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        REQUEST_CODE_PERMISSION);

                // If any permission above not allowed by user, this condition will
                //execute every time, else your else part will work
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        webView = (WebView) findViewById(R.id.webView1);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://sabakuch.com/elearning/");

        final String unme=getIntent().getStringExtra("username");
        final String pss=getIntent().getStringExtra("password");



        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                webView.loadUrl("javascript: {" +
                        "document.getElementById('username').value = '" + unme + "';" +
                        "document.getElementById('password').value = '" + pss + "';" +
                        "document.getElementById('submit_login').click();" +
                        "};");
                //Do something after 100ms
            }
        }, 5000);
        CommonUtils.setTracking("Dashboard " + MainActivity.class.getSimpleName());
        FacebookSdk.sdkInitialize(getApplicationContext());
        footerSelColor = ContextCompat.getColor(MainActivity.this, R.color.blue_theme);
        footerUnSelColor = ContextCompat.getColor(MainActivity.this, R.color.white);
        strUserID = CommonUtils.getStringPreferences(MainActivity.this, Constants.USER_ID);
        strUserName = CommonUtils.getStringPreferences(MainActivity.this, Constants.USER_NAME);
        strServerKey = CommonUtils.getStringPreferences(MainActivity.this, Constants.SERVER_KEY);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        CommonUtils.saveIntPreferences(MainActivity.this, Constants.Permission, SabakuchEmockApplication.permission);
        checkPermission();
        setId();


        // display the first navigation drawer view on app launch
        //   displayView(0);

        //  APIAccess.fetchPagingData(MainActivity.this, MainActivity.this,MainActivity.this,true);
        setHomeColor();
        setFragment(new SelectExamsFragment());
        getSupportActionBar().setTitle("");

//Banner




       //Interstitial
        if (TextUtils.isEmpty(getString(R.string.interstitial_full_screen))) {
            Toast.makeText(getApplicationContext(), "Please mention your Interstitial Ad ID in strings.xml", Toast.LENGTH_LONG).show();
            return;
        }

      /*  mInterstitialAd = new InterstitialAd(this);

        // set the ad unit ID
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_full_screen));

        AdRequest adRequest = new AdRequest.Builder()
               // .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                // Check the LogCat to get your test device ID
             //   .addTestDevice("2570F3B3E35CE3B026F90863B2E79FBD")
                .build();

        mInterstitialAd.loadAd(adRequest);

        mInterstitialAd.setAdListener(new AdListener() {
            public void onAdLoaded() {
                showInterstitial();
            }

            @Override
            public void onAdClosed() {
                Toast.makeText(getApplicationContext(), "Ad is closed!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                Toast.makeText(getApplicationContext(), "Ad failed to load! error code: " + errorCode, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdLeftApplication() {
                Toast.makeText(getApplicationContext(), "Ad left application!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdOpened() {
                Toast.makeText(getApplicationContext(), "Ad is opened!", Toast.LENGTH_SHORT).show();
            }
        });*/


    }

   /* private void showInterstitial() {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
    }*/

    private void setId() {
        llHome = (LinearLayout) findViewById(R.id.ll_home);
        llStatistics = (LinearLayout) findViewById(R.id.ll_statistics);
        llNotification = (LinearLayout) findViewById(R.id.ll_notification);
        llSettings = (LinearLayout) findViewById(R.id.ll_settings);
        ivHome = (ImageView) findViewById(R.id.iv_home);
        ivStatistics = (ImageView) findViewById(R.id.iv_statistics);
        ivNotification = (ImageView) findViewById(R.id.iv_notification);
        ivSettings = (ImageView) findViewById(R.id.iv_settings);
        tvHome = (TextView) findViewById(R.id.tv_home);
        tvStatistics = (TextView) findViewById(R.id.tv_statistics);
        tvNotification = (TextView) findViewById(R.id.tv_notification);
        tvSettings = (TextView) findViewById(R.id.tv_settings);
        llHome.setOnClickListener(this);
        llStatistics.setOnClickListener(this);
        llNotification.setOnClickListener(this);
        llSettings.setOnClickListener(this);

    }

    private boolean isNavigatingMain() {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.container_body);
        return (currentFragment instanceof SelectExamsFragment || currentFragment instanceof ExamList
                || currentFragment instanceof ProfileSettingFragmentNew);
    }

    private boolean isNavigatingStatus() {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.container_body);
        return (currentFragment instanceof OnlineTestPaperSectionFragment);
    }

    private boolean isNavigatingResultScreenStatus() {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.container_body);
        return (currentFragment instanceof ResultFragment);
    }

    @Override
    public void onBackPressed() {
        if (isNavigatingMain()) {
            finish();
        } else if (isNavigatingStatus()) {
            quitExamAlertDialog(0);
        } else if (isNavigatingResultScreenStatus()) {
            setFragment(new SelectExamsFragment());
        } else
            super.onBackPressed();
    }

    protected void setFragment(Fragment fragment) {
        FragmentTransaction ftTransaction = getSupportFragmentManager().beginTransaction();
        ftTransaction.add(R.id.container_body, fragment)
                .addToBackStack(null);
        ftTransaction.commit();
    }

    @Override
    public void onClick(View v) {
        //  Fragment fragment = null;
        switch (v.getId()) {
            case R.id.ll_home:
                if (isNavigatingStatus()) {
                    quitExamAlertDialog(0);
                } else {
                    setHomeColor();
                    setFragment(new SelectExamsFragment());
                }
                break;
            case R.id.ll_statistics:
                boolean isLogin = CommonUtils.getLoginPreferences(MainActivity.this, Constants.IS_LOGIN);
                if (isLogin) {
                    if (isNavigatingStatus()) {
                        quitExamAlertDialog(1);
                    } else {
                        setStatisticsColor();
                        setFragment(new ExamList());
                    }
                } else {
                    CommonUtils.loginAlertDialog(MainActivity.this, getResources().getString(R.string.please_login_to_access_this_feature));
                }
                break;
            case R.id.ll_notification:
                break;
            case R.id.ll_settings:
                if (isNavigatingStatus()) {
                    quitExamAlertDialog(2);
                } else {
                    setSettingsColor();
                    setFragment(new SettingsFragment());
                }
                break;
        }
        getSupportActionBar().setTitle("");

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //No call for super(). Bug on API Level > 11.
    }

    private void setHomeColor() {
        ivHome.setColorFilter(footerSelColor);
        tvHome.setTextColor(footerSelColor);
        ivStatistics.setColorFilter(footerUnSelColor);
        tvStatistics.setTextColor(footerUnSelColor);
        ivNotification.setColorFilter(footerUnSelColor);
        tvNotification.setTextColor(footerUnSelColor);
        ivSettings.setColorFilter(footerUnSelColor);
        tvSettings.setTextColor(footerUnSelColor);
    }

    private void setStatisticsColor() {
        ivHome.setColorFilter(footerUnSelColor);
        tvHome.setTextColor(footerUnSelColor);
        ivStatistics.setColorFilter(footerSelColor);
        tvStatistics.setTextColor(footerSelColor);
        ivNotification.setColorFilter(footerUnSelColor);
        tvNotification.setTextColor(footerUnSelColor);
        ivSettings.setColorFilter(footerUnSelColor);
        tvSettings.setTextColor(footerUnSelColor);
    }

    private void setSettingsColor() {
        ivHome.setColorFilter(footerUnSelColor);
        tvHome.setTextColor(footerUnSelColor);
        ivStatistics.setColorFilter(footerUnSelColor);
        tvStatistics.setTextColor(footerUnSelColor);
        ivNotification.setColorFilter(footerUnSelColor);
        tvNotification.setTextColor(footerUnSelColor);
        ivSettings.setColorFilter(footerSelColor);
        tvSettings.setTextColor(footerSelColor);
    }

    public void quitExamAlertDialog(final int tabPosition) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(getResources().getString(R.string.are_you_sure_to_quit));
        builder.setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (tabPosition == 0) {
                    setHomeColor();
                    setFragment(new SelectExamsFragment());
                } else if (tabPosition == 1) {
                    setStatisticsColor();
                    setFragment(new ExamList());
                } else if (tabPosition == 2) {
                    setSettingsColor();
                    setFragment(new SettingsFragment());
                }
                dialog.dismiss();
            }
        });
        builder.setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void checkPermission() {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            CommonUtils.LogMsg(TAG, "Oncheck permission");

            // Should we show an explanation?
//            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
//                    Manifest.permission.READ_PHONE_STATE)) {
//
//                // Show an explanation to the user *asynchronously* -- don't block
//                // this thread waiting for the user's response! After the user
//                // sees the explanation, try again to request the permission.
//
//            } else {

            // No explanation needed, we can request the permission.
            CommonUtils.saveIntPreferences(MainActivity.this, Constants.Permission, SabakuchEmockApplication.permission);
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_PHONE_STATE},
                    MY_PERMISSIONS_REQUEST_READ_PHONE_STATE);

            // MY_PERMISSIONS_REQUEST_READ_PHONE_STATE is an
            // app-defined int constant. The callback method gets the
            // result of the request.
//            }
        } else {
            SabakuchEmockApplication.permission = 1;
            CommonUtils.saveIntPreferences(MainActivity.this, Constants.Permission, SabakuchEmockApplication.permission);
        }

    }

    public void checkGetAccountsPermission() {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.GET_ACCOUNTS)
                != PackageManager.PERMISSION_GRANTED) {
            CommonUtils.LogMsg(TAG, "Oncheck permission");

            // No explanation needed, we can request the permission.

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.GET_ACCOUNTS},
                    MY_PERMISSIONS_REQUEST_GET_ACCOUNTS);

            // MY_PERMISSIONS_REQUEST_READ_PHONE_STATE is an
            // app-defined int constant. The callback method gets the
            // result of the request.
//            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        CommonUtils.LogMsg(TAG, "request code: " + requestCode);
        if (requestCode == MY_PERMISSIONS_REQUEST_READ_PHONE_STATE) {
            // If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // permission was granted, yay! Do the
                // contacts-related task you need to do.
                SabakuchEmockApplication.permission = 1;
                CommonUtils.saveIntPreferences(MainActivity.this, Constants.Permission, SabakuchEmockApplication.permission);
            } else {
                SabakuchEmockApplication.permission = 0;
                CommonUtils.saveIntPreferences(MainActivity.this, Constants.Permission, SabakuchEmockApplication.permission);
                // permission denied, boo! Disable the
                // functionality that depends on this permission.
            }
//            checkReadContactsPermission();
//            checkGetAccountsPermission();
            return;

            // other 'case' lines to check for other
            // permissions this app might request
        } else if (requestCode == MY_PERMISSIONS_REQUEST_GET_ACCOUNTS) {
            // If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // permission was granted, yay! Do the
                // contacts-related task you need to do.
//                CommonUtils.saveIntPreferences(MainActivity.this, Constants.ReadPermission, 1);
//                Intent intent = new Intent(this, ContactService.class);
//                // add infos for the service which file to download and where to store
//                startService(intent);
            } else {
                // permission denied, boo! Disable the
                // functionality that depends on this permission.
//                CommonUtils.saveIntPreferences(MainActivity.this, Constants.ReadPermission, 2);
            }

            return;
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
    // Fetches reg id from shared preferences
    // and displays on the screen
    private void displayFirebaseRegId() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        String regId = pref.getString("regId", null);

        Log.e(TAG1, "Firebase reg id: " + regId);
      //  Toast.makeText(MainActivity.this, regId, Toast.LENGTH_SHORT).show();

/*
        if (!TextUtils.isEmpty(regId))
            txtRegId.setText("Firebase Reg Id: " + regId);
        else
            txtRegId.setText("Firebase Reg Id is not received yet!");
*/
    }

    @Override
    protected void onResume() {
        super.onResume();

        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.REGISTRATION_COMPLETE));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.PUSH_NOTIFICATION));

        // clear the notification area when the app is opened
        NotificationUtils.clearNotifications(getApplicationContext());

      /*  if (mAdView != null) {
            mAdView.resume();
        }*/

    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);

    /*    if (mAdView != null) {
            mAdView.pause();
        }*/
        super.onPause();
    }



    @Override
    public void onDestroy() {
      /*  if (mAdView != null) {
            mAdView.destroy();
        }*/
        super.onDestroy();
    }

}