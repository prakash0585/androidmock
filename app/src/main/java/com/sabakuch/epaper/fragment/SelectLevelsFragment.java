package com.sabakuch.epaper.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
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
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.sabakuch.epaper.R;
import com.sabakuch.epaper.adapter.SelectLevelsAdapter;
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

public class SelectLevelsFragment extends Fragment implements ServiceInterface {
    private View rootView;
    private RecyclerView recyclerView;
    private SelectLevelsAdapter adapter;
    private Intent intent;
    private Activity context;
    private SwipeRefreshLayout swipeContainer;
    private ArrayList<LevelsData> arrData, arrListData = new ArrayList<>();
    private ArrayList<LevelDetailData> arrLevelDetailData = new ArrayList<>();
    private int UrlIndex = 0;
    private int page = 1;
    private int totalPage;
    private String examId = "";
    private String slug = "";
    private String strUserID;
    private ProgressDialog pb;
    private String level1Status = "0", level2Status = "0", level3Status = "0";
    private AdView mAdView;

    public SelectLevelsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_select_exams, container, false);
        strUserID = CommonUtils.getStringPreferences(context, Constants.USER_ID);
        Bundle bundle = getArguments();
        if (bundle != null) {
            examId = bundle.getString(Constants.EXAM_ID);
            slug = bundle.getString(Constants.SLUG);
        }
        CommonUtils.setTracking(SelectLevelsFragment.class.getSimpleName() + ", " + Constants.EXAM_ID + ":" + examId);
        setId();
        setRecyclerViewAdapter();
        setupToolbar();

        try {
            if (context != null && !context.isFinishing())
                if (CommonUtils.isOnline(context)) {
                    pb = ProgressDialog.show(context, "", context.getResources().getString(R.string.loader_text));
                    UrlIndex = 0;
                    APIAccess.fetchPagingData(SelectLevelsFragment.this, getActivity(), getActivity(), true);
                } else {
                    if (context != null && pb != null && pb.isShowing())
                        pb.dismiss();
                    Toast.makeText(context, context.getResources().getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                }

        } catch (Exception e) {
        }


        if (TextUtils.isEmpty(getString(R.string.banner_home_footer)))
        {
            Toast.makeText(context, "Please mention your Banner Ad ID in strings.xml", Toast.LENGTH_LONG).show();
        }

        mAdView = (AdView) rootView.findViewById(R.id.adView);
        LinearLayout tvinfo = (LinearLayout) rootView.findViewById(R.id.info);
        tvinfo.setVisibility(View.VISIBLE);
        tvinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new ExamInfoFragmentNew();
                Bundle bundle = new Bundle();
                bundle.putString(Constants.EXAM_ID, examId);
                bundle.putString(Constants.SLUG, slug);
                fragment.setArguments(bundle);
                setFragment(fragment);
            }
        });
        // mAdView.setAdSize(AdSize.BANNER);
        //mAdView.setAdUnitId(getString(R.string.banner_home_footer));

        AdRequest adRequest1 = new AdRequest.Builder()
                // .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                // Check the LogCat to get your test device ID
                //  .addTestDevice("2570F3B3E35CE3B026F90863B2E79FBD")
                .build();

        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
            }

            @Override
            public void onAdClosed() {
             //   Toast.makeText(context, "Ad is closed!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {

                if (errorCode==3)
                {
                    mAdView.setVisibility(View.GONE);
                }
              //  Toast.makeText(context, "Ad failed to load! error code: " + errorCode, Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onAdLeftApplication() {
               // Toast.makeText(context, "Ad left application!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
            }
        });

        mAdView.loadAd(adRequest1);
        return rootView;
    }

    private void setId() {
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        TextView tvTitle = (TextView) rootView.findViewById(R.id.tv_title);
        TextView paynow = (TextView) rootView.findViewById(R.id.paynow);
        tvTitle.setVisibility(View.GONE);
        paynow.setVisibility(View.GONE);
        swipeContainer = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeContainer.setRefreshing(false);
                UrlIndex = 2;
                APIAccess.fetchPagingData(SelectLevelsFragment.this, getActivity(), getActivity(), true);
            }
        });

        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    private void setupToolbar() {
        Toolbar mToolbar = (Toolbar) context.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        final ActionBar ab = ((AppCompatActivity) getActivity()).getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(false);
        ab.setIcon(0);
        TextView mTitle = (TextView) mToolbar.findViewById(R.id.tv_title);
        mTitle.setVisibility(View.VISIBLE);
        mTitle.setText(getResources().getString(R.string.select_levels));
    }

    private void setRecyclerViewAdapter() {
        arrListData = new ArrayList<LevelsData>();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (totalPage > page/* && loadingMore*/) {
                    UrlIndex = 1;
                    APIAccess.fetchPagingData(SelectLevelsFragment.this, getActivity(), getActivity(), true);
                }
            }
        });
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public String httpPost() {
        String response = "";
        try {
            if (UrlIndex == 0) {
                response = OpenConnection.callUrl(ServiceUrl.SABAKUCH_USER_DETAIL + "?exam_id=" + examId + "&user_id=" + strUserID);
            }
            if (UrlIndex == 1) {
                page++;
                response = OpenConnection.callUrl(ServiceUrl.SABAKUCH_LEVEL_LIST + "?userid=" + strUserID + "&slug=" + slug /*+"id="+strUserID+"&key="+strServerKey+"&page="+page*/);
            } else if (UrlIndex == 2) {
                page = 1;
                response = OpenConnection.callUrl(ServiceUrl.SABAKUCH_LEVEL_LIST + "?userid=" + strUserID + "&slug=" + slug /*+"id="+strUserID+"&key="+strServerKey+"&page="+page*/);
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
                    APIAccess.fetchPagingData(SelectLevelsFragment.this, getActivity(), getActivity(), true);
                } else if (UrlIndex == 1) {
                    totalPage = SabaKuchParse.parsePageCount(str);
                    arrData = SabaKuchParse.parseLevelsData(str);

                    if (arrData.size() > 0) {
                        arrListData.addAll(arrData);
                        adapter = new SelectLevelsAdapter(context, arrListData, examId, level1Status, level2Status, level3Status,slug);
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
                        adapter = new SelectLevelsAdapter(context, arrListData, examId, level1Status, level2Status, level3Status,slug);
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


    @Override
    public void onResume() {
        super.onResume();

        if (mAdView != null) {
            mAdView.resume();
        }

    }

    @Override
    public void onPause() {

        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }



    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }
    protected void setFragment(Fragment fragment) {
        FragmentTransaction ftTransaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
        ftTransaction.replace(R.id.container_body, fragment)
                .addToBackStack("back");
        ftTransaction.commit();
    }
}
