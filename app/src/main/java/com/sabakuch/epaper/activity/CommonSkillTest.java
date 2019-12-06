package com.sabakuch.epaper.activity;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sabakuch.epaper.R;
import com.sabakuch.epaper.apiclass.APIAccess;
import com.sabakuch.epaper.apputils.CommonUtils;
import com.sabakuch.epaper.apputils.SabaKuchParse;
import com.sabakuch.epaper.apputils.ServiceUrl;
import com.sabakuch.epaper.constants.Constants;
import com.sabakuch.epaper.data.LevelDetailData;
import com.sabakuch.epaper.data.LevelsData;
import com.sabakuch.epaper.serviceclasses.OpenConnection;
import com.sabakuch.epaper.serviceclasses.ServiceInterface;

import java.util.ArrayList;

public class CommonSkillTest extends AppCompatActivity implements ServiceInterface{
    private RecyclerView recyclerView;
    private SelectLevelsAdapterSkillNew adapter;
    private Intent intent;
    private Activity context;
    private SwipeRefreshLayout swipeContainer;
    private ArrayList<LevelsData> arrData, arrListData = new ArrayList<>();
    private ArrayList<LevelDetailData> arrLevelDetailData = new ArrayList<>();
    private int UrlIndex = 0;
    private int page = 1;
    private int totalPage;
    private String examId = " ";
    private String strUserID;
    private ProgressDialog pb;
    private String level1Status = "0", level2Status = "0", level3Status = "0";

    private int footerSelColor, footerUnSelColor;
    int MY_PERMISSIONS_REQUEST_READ_PHONE_STATE = 1;
    int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 2;
    int MY_PERMISSIONS_REQUEST_GET_ACCOUNTS = 3;
    private static String TAG = OnlinetestBank.class.getSimpleName();

    private LinearLayout llStatistics, llHome, llNotification, llSettings;
    private ImageView ivHome, ivStatistics, ivNotification, ivSettings;
    private TextView tvHome, tvStatistics, tvNotification, tvSettings;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_po_main);
        context = CommonSkillTest.this;



        strUserID = CommonUtils.getStringPreferences(context, Constants.USER_ID);
        /*Bundle bundle = getArguments();
        if (bundle != null) {
            examId = bundle.getString(Constants.EXAM_ID);
        }*/

        footerSelColor = ContextCompat.getColor(CommonSkillTest.this, R.color.blue_theme);
        footerUnSelColor = ContextCompat.getColor(CommonSkillTest.this, R.color.white);

        CommonUtils.setTracking(Main2Activity.class.getSimpleName() + ", " + Constants.EXAM_ID + ":" + examId);
        setId();
        setRecyclerViewAdapter();
        setupToolbar();
       // setIdd();
        try {
            if (context != null && !context.isFinishing())
                if (CommonUtils.isOnline(context)) {
                    pb = ProgressDialog.show(context, "", context.getResources().getString(R.string.loader_text));
                    UrlIndex = 0;
                    APIAccess.fetchPagingData(CommonSkillTest.this, getApplicationContext(), CommonSkillTest.this, true);
                } else {
                    if (context != null && pb != null && pb.isShowing())
                        pb.dismiss();
                    Toast.makeText(context, context.getResources().getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                }

        } catch (Exception e) {
        }


    }



    private void setId()

    {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        TextView tvTitle = (TextView) findViewById(R.id.tv_title);
        tvTitle.setVisibility(View.GONE);
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeContainer.setRefreshing(false);
                UrlIndex = 2;
                APIAccess.fetchPagingData(CommonSkillTest.this, getApplicationContext(), CommonSkillTest.this, true);
            }
        });


        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
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
        mTitle.setText(getResources().getString(R.string.select_levels));
    }

    private void setRecyclerViewAdapter()


    {
        arrListData = new ArrayList<LevelsData>();
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (totalPage > page/* && loadingMore*/) {
                    UrlIndex = 1;
                    APIAccess.fetchPagingData(CommonSkillTest.this, getApplicationContext(), CommonSkillTest.this, true);
                }
            }
        });
    }



    @Override
    public String httpPost() {
        String response = "";
        try {
            if (UrlIndex == 0) {
                response = OpenConnection.callUrl(ServiceUrl.SABAKUCH_USER_DETAIL + "?exam_id=25" + "&user_id=" + strUserID);
            }
            if (UrlIndex == 1) {
                page++;
                response = OpenConnection.callUrl(ServiceUrl.SABAKUCH_LEVEL_LIST + "?page=" + page/*+"id="+strUserID+"&key="+strServerKey+"&page="+page*/);
            } else if (UrlIndex == 2) {
                page = 1;
                response = OpenConnection.callUrl(ServiceUrl.SABAKUCH_LEVEL_LIST + "?page=" + page/*+"id="+strUserID+"&key="+strServerKey+"&page="+page*/);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    @Override
    public String httpAfterPost(String str) {
        if (str != null) {
            if (SabaKuchParse.getResponseCode(str) != null && SabaKuchParse.getResponseCode(str).equalsIgnoreCase("200")) {
                if (UrlIndex == 0) {
                    arrLevelDetailData = SabaKuchParse.parseLevelDetailData(str);
                    if (arrLevelDetailData.size() > 0) {
                        level1Status = arrLevelDetailData.get(0).level_1;
                        level2Status = arrLevelDetailData.get(0).level_2;
                        level3Status = arrLevelDetailData.get(0).level_3;
                    }
                    UrlIndex = 2;
                    APIAccess.fetchPagingData(CommonSkillTest.this, getApplicationContext(), CommonSkillTest.this, true);
                } else if (UrlIndex == 1) {
                    totalPage = SabaKuchParse.parsePageCount(str);
                    arrData = SabaKuchParse.parseLevelsData(str);

                    if (arrData.size() > 0)
                    {
                        arrListData.addAll(arrData);
                        adapter = new SelectLevelsAdapterSkillNew(context, arrListData, examId, level1Status, level2Status, level3Status);
                        recyclerView.setAdapter(adapter);
                    }

                }
                else if (UrlIndex == 2)
                {
                    if (context != null && pb != null && pb.isShowing())
                        pb.dismiss();
                    totalPage = SabaKuchParse.parsePageCount(str);
                    arrData = SabaKuchParse.parseLevelsData(str);
                    if (arrData.size() > 0) {
                        if (arrListData != null && arrListData.size() > 0) {
                            arrListData.clear();
                        }
                        arrListData.addAll(arrData);
                        adapter = new SelectLevelsAdapterSkillNew(context, arrListData, examId, level1Status, level2Status, level3Status);
                        recyclerView.setAdapter(adapter);
                    }
                }

            } else

            {
                if (context != null && pb != null && pb.isShowing())
                    pb.dismiss();
                Toast.makeText(context, getResources().getString(R.string.server_not_responding), Toast.LENGTH_SHORT).show();
            }
        }

        return null;
    }

   /* private void setIdd() {
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
  
    protected void setFragment(Fragment fragment) {
        FragmentTransaction ftTransaction = getSupportFragmentManager().beginTransaction();
        ftTransaction.replace(R.id.container_body, fragment).addToBackStack(null);
        ftTransaction.commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_home:
                if (isNavigatingStatus()) {
                    quitExamAlertDialog(0);
                } else {
                    setHomeColor();
                    //startActivity(new Intent(OnlinetestBank.this,InstructionBank.class));
                    setFragment(new SelectExamsFragment());
                }
                break;
            case R.id.ll_statistics:
                boolean isLogin = CommonUtils.getLoginPreferences(CommonSkillTest.this, Constants.IS_LOGIN);
                if (isLogin) {
                    if (isNavigatingStatus()) {
                        quitExamAlertDialog(1);
                    } else {
                        setStatisticsColor();
                        setFragment(new MyDashboardFragment());
                    }
                } else {
                    CommonUtils.loginAlertDialog(CommonSkillTest.this, getResources().getString(R.string.please_login_to_access_this_feature));
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


    private boolean isNavigatingStatus() {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.container_body);
        return (currentFragment instanceof OnlineTestPaperSectionFragment);
    }


    public void quitExamAlertDialog(final int tabPosition) {
        AlertDialog.Builder builder = new AlertDialog.Builder(CommonSkillTest.this);
        builder.setTitle(getResources().getString(R.string.are_you_sure_to_quit));
        builder.setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (tabPosition == 0) {
                    setHomeColor();
                    // startActivity(new Intent(OnlinetestBank.this,InstructionBank.class));

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
            public void onClick(DialogInterface dialog, int id) {
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
        if (isNavigatingMain()) {
            finish();

        }

        else if (isNavigatingStatus())


        {
            quitExamAlertDialog(0);
        }

        else if (isNavigatingResultScreenStatus())


        {
            //startActivity(new Intent(OnlinetestBank.this,InstructionBank.class));

            setFragment(new SelectExamsFragment());
        } else
            super.onBackPressed();
    }

    private boolean isNavigatingMain() {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.container_body);
        return (currentFragment instanceof SelectExamsFragment || currentFragment instanceof MyDashboardFragment
                || currentFragment instanceof ProfileSettingFragmentNew);

    }



    private boolean isNavigatingResultScreenStatus() {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.container_body);
        return (currentFragment instanceof ResultFragment);
    }


    public void checkPermission()

    {
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
            CommonUtils.saveIntPreferences(CommonSkillTest.this, Constants.Permission, SabakuchEmockApplication.permission);
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_PHONE_STATE},
                    MY_PERMISSIONS_REQUEST_READ_PHONE_STATE);

            // MY_PERMISSIONS_REQUEST_READ_PHONE_STATE is an
            // app-defined int constant. The callback method gets the
            // result of the request.
//            }
        } else {
            SabakuchEmockApplication.permission = 1;
            CommonUtils.saveIntPreferences(CommonSkillTest.this, Constants.Permission, SabakuchEmockApplication.permission);
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
                CommonUtils.saveIntPreferences(CommonSkillTest.this, Constants.Permission, SabakuchEmockApplication.permission);
            } else {
                SabakuchEmockApplication.permission = 0;
                CommonUtils.saveIntPreferences(CommonSkillTest.this, Constants.Permission, SabakuchEmockApplication.permission);
                // permission denied, boo! Disable the
                // functionality that depends on this permission.
            }
//            checkReadContactsPermission();
//            checkGetAccountsPermission();
            return;

            // other 'case' lines to check for other
            // permissions this app might request
        } else if (requestCode == MY_PERMISSIONS_REQUEST_GET_ACCOUNTS)

        {
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
    */
    
}
