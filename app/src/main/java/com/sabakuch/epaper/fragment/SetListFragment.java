package com.sabakuch.epaper.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sabakuch.epaper.R;
import com.sabakuch.epaper.adapter.SelectExamsAdapter;
import com.sabakuch.epaper.apiclass.APIAccess;
import com.sabakuch.epaper.apputils.CommonUtils;
import com.sabakuch.epaper.apputils.SabaKuchParse;
import com.sabakuch.epaper.apputils.ServiceUrl;
import com.sabakuch.epaper.constants.Constants;
import com.sabakuch.epaper.data.SelectExamsData;
import com.sabakuch.epaper.serviceclasses.OpenConnection;
import com.sabakuch.epaper.serviceclasses.ServiceInterface;

import java.util.ArrayList;

/**
 * Created by dell on 01-Apr-17.
 */
public class SetListFragment extends Fragment implements ServiceInterface {
    View rootView;
    int UrlIndex = 0;
    String strExamId;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeContainer;
    private Activity context;
    SelectExamsAdapter adapter;
     ArrayList<SelectExamsData> arrListData = new ArrayList<SelectExamsData>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_select_exams, container, false);
        context = getActivity();
        CommonUtils.setTracking(SetListFragment.class.getSimpleName() );
        setId();
        setRecyclerViewAdapter();
        setupToolbar();
        APIAccess.fetchData(SetListFragment.this, getActivity(), getActivity());
        return rootView;
    }

    private void setId() {
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        TextView tvTitle = (TextView) rootView.findViewById(R.id.tv_title);
        tvTitle.setVisibility(View.VISIBLE);
        tvTitle.setText("List of sets");
        swipeContainer = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeContainer.setRefreshing(false);
                UrlIndex = 1;
                APIAccess.fetchPagingData(SetListFragment.this, getActivity(), getActivity(), true);
            }
        });

        // Configure the refreshing colors
//        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
//                android.R.color.holo_green_light,
//                android.R.color.holo_orange_light,
//                android.R.color.holo_red_light);

        if (getArguments() != null) {
            Bundle mBundle = getArguments();
            strExamId = mBundle.getString(Constants.EXAM_ID);
        }
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
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new SelectExamsAdapter(getActivity(), arrListData);
        recyclerView.setAdapter(adapter);
//        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//                if (totalPage > page/* && loadingMore*/) {
//                    UrlIndex = 0;
//                }
//            }
//        });


    }

    @Override
    public String httpPost() {
        String response = OpenConnection.callUrl(ServiceUrl.SABAKUCH_SOLUTION_SET+strExamId);
        return response;
    }

    @Override
    public String httpAfterPost(String str) {
       ArrayList<SelectExamsData> arrData = SabaKuchParse.parseExamsData(str);


        if (arrData.size() > 0) {
            if (arrListData != null && arrListData.size() > 0) {
                arrListData.clear();
            }
            arrListData.addAll(arrData);
        }
        Log.e("TAG","mSelectExamsData: "+"arrListData: "+ arrListData.size()+ CommonUtils.getCustomGson().toJson(arrListData));

        adapter.notifyDataSetChanged();
        return null;
    }
}
