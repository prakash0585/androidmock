package com.sabakuch.epaper.activity;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
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

public class CLAT extends AppCompatActivity implements ServiceInterface {
    private RecyclerView recyclerView;
    private SelectLevelsAdapterCLATNew adapter;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_po_main);
        context = CLAT.this;



        strUserID = CommonUtils.getStringPreferences(context, Constants.USER_ID);
        /*Bundle bundle = getArguments();
        if (bundle != null) {
            examId = bundle.getString(Constants.EXAM_ID);
        }*/


        CommonUtils.setTracking(Main2Activity.class.getSimpleName() + ", " + Constants.EXAM_ID + ":" + examId);
        setId();
        setRecyclerViewAdapter();
        setupToolbar();

        try {
            if (context != null && !context.isFinishing())
                if (CommonUtils.isOnline(context)) {
                    pb = ProgressDialog.show(context, "", context.getResources().getString(R.string.loader_text));
                    UrlIndex = 0;
                    APIAccess.fetchPagingData(CLAT.this, getApplicationContext(), CLAT.this, true);
                } else {
                    if (context != null && pb != null && pb.isShowing())
                        pb.dismiss();
                    Toast.makeText(context, context.getResources().getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                }

        } catch (Exception e) {
        }


    }



    private void setId() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        TextView tvTitle = (TextView) findViewById(R.id.tv_title);
        tvTitle.setVisibility(View.GONE);
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeContainer.setRefreshing(false);
                UrlIndex = 2;
                APIAccess.fetchPagingData(CLAT.this, getApplicationContext(), CLAT.this, true);
            }
        });

        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    private void setupToolbar() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setIcon(0);
        getSupportActionBar().setTitle("");

        TextView mTitle = (TextView) mToolbar.findViewById(R.id.tv_title);
        mTitle.setVisibility(View.VISIBLE);
        mTitle.setText(getResources().getString(R.string.select_levels));
    }

    private void setRecyclerViewAdapter() {
        arrListData = new ArrayList<LevelsData>();
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (totalPage > page/* && loadingMore*/) {
                    UrlIndex = 1;
                    APIAccess.fetchPagingData(CLAT.this, getApplicationContext(), CLAT.this, true);
                }
            }
        });
    }



    @Override
    public String httpPost() {
        String response = "";
        try {
            if (UrlIndex == 0) {
                response = OpenConnection.callUrl(ServiceUrl.SABAKUCH_USER_DETAIL + "?exam_id=11" + "&user_id=" + strUserID);
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
                    APIAccess.fetchPagingData(CLAT.this, getApplicationContext(), CLAT.this, true);
                } else if (UrlIndex == 1) {
                    totalPage = SabaKuchParse.parsePageCount(str);
                    arrData = SabaKuchParse.parseLevelsData(str);

                    if (arrData.size() > 0) {
                        arrListData.addAll(arrData);
                        adapter = new SelectLevelsAdapterCLATNew(context, arrListData, examId, level1Status, level2Status, level3Status);
                        recyclerView.setAdapter(adapter);
                    }

                } else if (UrlIndex == 2) {
                    if (context != null && pb != null && pb.isShowing())
                        pb.dismiss();
                    totalPage = SabaKuchParse.parsePageCount(str);
                    arrData = SabaKuchParse.parseLevelsData(str);
                    if (arrData.size() > 0) {
                        if (arrListData != null && arrListData.size() > 0) {
                            arrListData.clear();
                        }
                        arrListData.addAll(arrData);
                        adapter = new SelectLevelsAdapterCLATNew(context, arrListData, examId, level1Status, level2Status, level3Status);
                        recyclerView.setAdapter(adapter);
                    }
                }

            } else {
                if (context != null && pb != null && pb.isShowing())
                    pb.dismiss();
                Toast.makeText(context, getResources().getString(R.string.server_not_responding), Toast.LENGTH_SHORT).show();
            }
        }

        return null;
    }

}
