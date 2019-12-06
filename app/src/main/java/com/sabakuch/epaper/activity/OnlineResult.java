package com.sabakuch.epaper.activity;
import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.sabakuch.epaper.R;
import com.sabakuch.epaper.application.SabakuchEmockApplication;
import com.sabakuch.epaper.apputils.CommonUtils;
import com.sabakuch.epaper.constants.Constants;
import com.sabakuch.epaper.entitymime.MultipartEntity;
import com.sabakuch.epaper.fragment.MyDashboardFragment;
import com.sabakuch.epaper.fragment.OnlineTestPaperSectionFragment;
import com.sabakuch.epaper.fragment.ProfileSettingFragmentNew;
import com.sabakuch.epaper.fragment.ResultFragment;
import com.sabakuch.epaper.fragment.SelectExamsFragment;
import com.sabakuch.epaper.fragment.SettingsFragment;

public class OnlineResult extends AppCompatActivity implements View.OnClickListener
{
    private ProgressDialog pb;
    private MultipartEntity reqEntity;
    private int footerSelColor, footerUnSelColor;
    int MY_PERMISSIONS_REQUEST_READ_PHONE_STATE = 1;
    int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 2;
    int MY_PERMISSIONS_REQUEST_GET_ACCOUNTS = 3;
    private static String TAG = OnlineResult.class.getSimpleName();
    private LinearLayout llStatistics, llHome, llNotification, llSettings;
    private ImageView ivHome, ivStatistics, ivNotification, ivSettings;
    private TextView tvHome, tvStatistics, tvNotification, tvSettings;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_resultbank);
        setIdd();
        setupToolbar();
        footerSelColor = ContextCompat.getColor(OnlineResult.this, R.color.blue_theme);
        footerUnSelColor = ContextCompat.getColor(OnlineResult.this, R.color.white);
        MyDashboardFragment fragment = new MyDashboardFragment();
        setFragment(fragment);


    }
    private void setIdd()
    {
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

    private void setupToolbar()
    {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setIcon(0);
        getSupportActionBar().setTitle("");
        TextView mTitle = (TextView) mToolbar.findViewById(R.id.tv_title);
        mTitle.setVisibility(View.VISIBLE);
        mTitle.setText(getResources().getString(R.string.instruction));
    }
    protected void setFragment(Fragment fragment)
    {
        FragmentTransaction ftTransaction = getSupportFragmentManager().beginTransaction();
        ftTransaction.replace(R.id.container_body, fragment).addToBackStack(null);
        ftTransaction.commit();
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.ll_home:
                if (isNavigatingStatus())
                {
                    quitExamAlertDialog(0);
                } else
                {
                    setHomeColor();
                    setFragment(new SelectExamsFragment());
                }
                break;
            case R.id.ll_statistics:
                boolean isLogin = CommonUtils.getLoginPreferences(OnlineResult.this, Constants.IS_LOGIN);
                if (isLogin)
                {
                    if (isNavigatingStatus())

                    {
                        quitExamAlertDialog(1);
                    } else
                    {
                        setStatisticsColor();
                        setFragment(new MyDashboardFragment());
                    }
                } else {
                    CommonUtils.loginAlertDialog(OnlineResult.this, getResources().getString(R.string.please_login_to_access_this_feature));
                }
                break;
            case R.id.ll_notification:
                break;
            case R.id.ll_settings:
                if (isNavigatingStatus())

                {
                    quitExamAlertDialog(2);
                } else

                {
                    setSettingsColor();
                    setFragment(new SettingsFragment());
                }

                break;
        }
        getSupportActionBar().setTitle("");
    }

    private boolean isNavigatingStatus()
    {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.container_body);
        return (currentFragment instanceof OnlineTestPaperSectionFragment);
    }


    public void quitExamAlertDialog(final int tabPosition)

    {
        AlertDialog.Builder builder = new AlertDialog.Builder(OnlineResult.this);
        builder.setTitle(getResources().getString(R.string.are_you_sure_to_quit));
        builder.setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (tabPosition == 0) {
                    setHomeColor();

                    setFragment(new SelectExamsFragment());
                } else if (tabPosition == 1) {
                    setStatisticsColor();
                    setFragment(new MyDashboardFragment());
                } else if (tabPosition == 2) {
                    setSettingsColor();
                    setFragment(new SettingsFragment());
                }
                dialog.dismiss();
            }
        });
        builder.setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id)

            {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    private void setHomeColor()
    {
        ivHome.setColorFilter(footerSelColor);
        tvHome.setTextColor(footerSelColor);
        ivStatistics.setColorFilter(footerUnSelColor);
        tvStatistics.setTextColor(footerUnSelColor);
        ivNotification.setColorFilter(footerUnSelColor);
        tvNotification.setTextColor(footerUnSelColor);
        ivSettings.setColorFilter(footerUnSelColor);
        tvSettings.setTextColor(footerUnSelColor);
    }
    private void setStatisticsColor()
    {
        ivHome.setColorFilter(footerUnSelColor);
        tvHome.setTextColor(footerUnSelColor);
        ivStatistics.setColorFilter(footerSelColor);
        tvStatistics.setTextColor(footerSelColor);
        ivNotification.setColorFilter(footerUnSelColor);
        tvNotification.setTextColor(footerUnSelColor);
        ivSettings.setColorFilter(footerUnSelColor);
        tvSettings.setTextColor(footerUnSelColor);
    }

    private void setSettingsColor()
    {
        ivHome.setColorFilter(footerUnSelColor);
        tvHome.setTextColor(footerUnSelColor);
        ivStatistics.setColorFilter(footerUnSelColor);
        tvStatistics.setTextColor(footerUnSelColor);
        ivNotification.setColorFilter(footerUnSelColor);
        tvNotification.setTextColor(footerUnSelColor);
        ivSettings.setColorFilter(footerSelColor);
        tvSettings.setTextColor(footerSelColor);
    }

    @Override
    public void onBackPressed()
    {
        if (isNavigatingMain())
        {
            finish();

        }

        else if (isNavigatingStatus())


        {
            quitExamAlertDialog(0);
        }

        else if (isNavigatingResultScreenStatus())


        {

            setFragment(new SelectExamsFragment());
        }

        else
            super.onBackPressed();
    }

    private boolean isNavigatingMain()

    {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.container_body);
        return (currentFragment instanceof SelectExamsFragment || currentFragment instanceof MyDashboardFragment
                || currentFragment instanceof ProfileSettingFragmentNew);

    }



    private boolean isNavigatingResultScreenStatus()

    {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.container_body);
        return (currentFragment instanceof ResultFragment);
    }


    public void checkPermission()

    {

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED)

        {
            CommonUtils.LogMsg(TAG, "Oncheck permission");

            CommonUtils.saveIntPreferences(OnlineResult.this, Constants.Permission, SabakuchEmockApplication.permission);
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_PHONE_STATE},
                    MY_PERMISSIONS_REQUEST_READ_PHONE_STATE);


        } else {
            SabakuchEmockApplication.permission = 1;
            CommonUtils.saveIntPreferences(OnlineResult.this, Constants.Permission, SabakuchEmockApplication.permission);
        }

    }
    public void checkGetAccountsPermission()

    {

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.GET_ACCOUNTS)
                != PackageManager.PERMISSION_GRANTED) {
            CommonUtils.LogMsg(TAG, "Oncheck permission");


            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.GET_ACCOUNTS},
                    MY_PERMISSIONS_REQUEST_GET_ACCOUNTS);

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults)
    {
        CommonUtils.LogMsg(TAG, "request code: " + requestCode);
        if (requestCode == MY_PERMISSIONS_REQUEST_READ_PHONE_STATE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                SabakuchEmockApplication.permission = 1;
                CommonUtils.saveIntPreferences(OnlineResult.this, Constants.Permission, SabakuchEmockApplication.permission);
            } else {
                SabakuchEmockApplication.permission = 0;
                CommonUtils.saveIntPreferences(OnlineResult.this, Constants.Permission, SabakuchEmockApplication.permission);
            }

            return;
      } else if (requestCode == MY_PERMISSIONS_REQUEST_GET_ACCOUNTS)

      {
            // If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED)

            {

            }
            else

            {


            }

            return;

      }
    }
    
}
