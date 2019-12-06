package com.sabakuch.epaper.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sabakuch.epaper.R;
import com.sabakuch.epaper.adapter.ExamListAdapterPremium;
import com.sabakuch.epaper.apiclass.APIAccess;
import com.sabakuch.epaper.apputils.CommonUtils;
import com.sabakuch.epaper.apputils.SabaKuchParse;
import com.sabakuch.epaper.apputils.ServiceUrl;
import com.sabakuch.epaper.constants.Constants;
import com.sabakuch.epaper.data.DashboardData;
import com.sabakuch.epaper.serviceclasses.OpenConnection;
import com.sabakuch.epaper.serviceclasses.ServiceInterface;

import java.util.ArrayList;

public class ExamListPremium extends Fragment implements ServiceInterface {
    private View rootView;
    private RecyclerView recyclerView;
    private ExamListAdapterPremium adapter;
    private Activity context;
    private SwipeRefreshLayout swipeContainer;
    private ArrayList<DashboardData> arrData, arrListData = new ArrayList<>();
    private int UrlIndex ;
    private int totalPage;
    private String strUserID;
    private int page = 1;
    private Button pmumdash;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_exam_list, container, false);
        CommonUtils.setTracking(ExamListPremium.class.getSimpleName());
        strUserID = CommonUtils.getStringPreferences(getActivity(), Constants.USER_ID);

        setId();
        setRecyclerViewAdapter();
        setupToolbar();
        UrlIndex = 1;
        APIAccess.fetchData(ExamListPremium.this, getActivity(), getActivity());
        return rootView;
    }

    private void setId() {
        LinearLayout pinv=(LinearLayout)rootView.findViewById(R.id.pinv);
        pinv.setVisibility(View.GONE);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);

        swipeContainer = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeContainer.setRefreshing(false);
                UrlIndex = 1;
                APIAccess.fetchPagingData(ExamListPremium.this, getActivity(), getActivity(), true);

            }
        });

        pmumdash = (Button) rootView.findViewById(R.id.pmumdash);

        pmumdash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new MyDashboardFragmentPremium();

                setFragment(fragment);

            }
        });

        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);


    }

    protected void setFragment(Fragment fragment) {
        FragmentTransaction ftTransaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
        ftTransaction.replace(R.id.container_body, fragment).addToBackStack(null);
        ftTransaction.commit();
    }

    private void setupToolbar() {
        Toolbar mToolbar = (Toolbar) context.findViewById(R.id.toolbar);
        TextView mTitle = (TextView) mToolbar.findViewById(R.id.tv_title);
        mTitle.setVisibility(View.GONE);
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        final ActionBar ab = ((AppCompatActivity) getActivity()).getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(false);
        ab.setIcon(R.drawable.app_header);
        ab.setTitle("");
    }

    private void setRecyclerViewAdapter() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        arrListData = new ArrayList<DashboardData>();
        adapter = new ExamListAdapterPremium(getActivity(), arrListData);
        recyclerView.setAdapter(adapter);
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (totalPage > page)
                {
                    UrlIndex = 0;
                    APIAccess.fetchPagingData(ExamListPremium.this, getActivity(), getActivity(), true);
                }
            }
        });
        adapter = new ExamListAdapterPremium(getActivity(), arrListData);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onResume() {
        super.onResume();
        CommonUtils.LogMsg("TAG", "onResume: mSelectExamsData: " + "arrListData: " + arrListData.size() + CommonUtils.getCustomGson().toJson(arrListData));



    }


    @Override
    public String httpPost() {
        String response = "";
        try {
            if (UrlIndex == 0) {
                page++;
                response = OpenConnection.callUrl(ServiceUrl.SABAKUCH_PAPER_MY_DASHBOARD + "?feed=1" +  "&user_id=" + strUserID + "&premium_exam=1&exam=1");
            } else if (UrlIndex == 1) {
                page = 1;
                response = OpenConnection.callUrl(ServiceUrl.SABAKUCH_PAPER_MY_DASHBOARD + "?feed=1" +  "&user_id=" + strUserID + "&premium_exam=1&exam=1");
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
                totalPage = SabaKuchParse.parsePageCount(str);
                arrData = SabaKuchParse.parseDashboardData(str);
                if (UrlIndex == 0) {
                    if (arrData.size() > 0) {
                        arrListData.addAll(arrData);
                        adapter.notifyDataSetChanged();

                    }

                }
                else if (UrlIndex == 1) {

                    arrListData.clear();

                    if (arrData.size() > 0)
                    {
                        arrListData.addAll(arrData);
                        adapter.notifyDataSetChanged();

                    }

                }

            } else {
                Toast.makeText(context, getResources().getString(R.string.server_not_responding), Toast.LENGTH_SHORT).show();
            }
        }

        return null;
    }



    @Override
    public void onPause() {


        super.onPause();
    }



    @Override
    public void onDestroy() {

        super.onDestroy();
    }
}
