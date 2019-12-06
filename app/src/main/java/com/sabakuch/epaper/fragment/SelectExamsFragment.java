package com.sabakuch.epaper.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.sabakuch.epaper.R;
import com.sabakuch.epaper.activity.PaymentWeb;
import com.sabakuch.epaper.activity.WebVew;
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
import java.util.List;

public class SelectExamsFragment extends Fragment implements ServiceInterface,AdapterView.OnItemSelectedListener {
    String strExamId;
    SelectExamsData mSelectExamsData;
    private View rootView;
    private RecyclerView recyclerView;
    private SelectExamsAdapter adapter;
    private Intent intent;
    private Activity context;
    private SwipeRefreshLayout swipeContainer;
    private ArrayList<SelectExamsData> arrData, arrListData = new ArrayList<>();
    private int UrlIndex ;
    private int page = 1;
    private int totalPage;
//    private ImageView iv_solution_paper;
    private String strUserID;
    private AdView mAdView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_select_exams, container, false);
        CommonUtils.setTracking(SelectExamsFragment.class.getSimpleName());
        strUserID = CommonUtils.getStringPreferences(getActivity(), Constants.USER_ID);

        setId();
        setRecyclerViewAdapter();
        setupToolbar();
        UrlIndex = 1;
        APIAccess.fetchData(SelectExamsFragment.this, getActivity(), getActivity());
        if (TextUtils.isEmpty(getString(R.string.banner_home_footer)))
        {
            Toast.makeText(context, "Please mention your Banner Ad ID in strings.xml", Toast.LENGTH_LONG).show();
        }

        mAdView = (AdView) rootView.findViewById(R.id.adView);
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
              //  Toast.makeText(context, "Ad is closed!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
             //   Toast.makeText(context, "Ad failed to load! error code: " + errorCode, Toast.LENGTH_SHORT).show();
                if (errorCode==3)
                {
                    mAdView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onAdLeftApplication() {
             //   Toast.makeText(context, "Ad left application!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
            }
        });

        mAdView.loadAd(adRequest1);
        return rootView;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        if (item.equals("Students") || item=="Students")
        {

            Intent intentp=new Intent(context, WebVew.class);
            intentp.putExtra("url", "https://sabakuch.com/elearning/mock/pricing1");
            context.startActivity(intentp);
            Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();

        }
        if (item.equals("Institutes") || item=="Institutes")
        {
            Intent intentp=new Intent(context, WebVew.class);
            intentp.putExtra("url", "https://sabakuch.com/elearning/mock/pricing");
            context.startActivity(intentp);
            Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();

        }

        // Showing selected spinner item

    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }

    private void setId() {
//        iv_solution_paper = (ImageView) rootView.findViewById(R.id.iv_solution_paper);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        TextView tvTitle = (TextView) rootView.findViewById(R.id.tv_title);
        TextView paynow = (TextView) rootView.findViewById(R.id.paynow);
        Spinner spinner = (Spinner) rootView.findViewById(R.id.spinner);
      //  spinner.setVisibility(View.VISIBLE);

        // Spinner click listener
        spinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("Pricing");
        categories.add("Students");
        categories.add("Institutes");


        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);



        paynow.setVisibility(View.VISIBLE);
        paynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setFragment(new paynow());



            }
        });
        tvTitle.setVisibility(View.VISIBLE);
        swipeContainer = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeContainer.setRefreshing(false);
                UrlIndex = 1;
                APIAccess.fetchPagingData(SelectExamsFragment.this, getActivity(), getActivity(), true);

            }
        });

        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

//        iv_solution_paper.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Fragment fragment = new SetListFragment();
//                Bundle bundle = new Bundle();
//                bundle.putString(Constants.EXAM_ID, strExamId);
//                fragment.setArguments(bundle);
//                if (context != null) {
//                    setFragment(fragment);
//                }
//            }
//        });
    }

    protected void setFragment(Fragment fragment) {
        FragmentTransaction ftTransaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
        ftTransaction.replace(R.id.container_body, fragment)
                .addToBackStack(null);
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
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(layoutManager);

        arrListData = new ArrayList<SelectExamsData>();
        adapter = new SelectExamsAdapter(getActivity(), arrListData);
        recyclerView.setAdapter(adapter);
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (totalPage > page/* && loadingMore*/) {
                    UrlIndex = 0;
                    APIAccess.fetchPagingData(SelectExamsFragment.this, getActivity(), getActivity(), true);
                }
            }
        });
        adapter = new SelectExamsAdapter(getActivity(), arrListData);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onResume() {
        super.onResume();
        CommonUtils.LogMsg("TAG", "onResume: mSelectExamsData: " + "arrListData: " + arrListData.size() + CommonUtils.getCustomGson().toJson(arrListData));

        if (mAdView != null) {
            mAdView.resume();
        }

    }


    @Override
    public String httpPost() {
        String response = "";
        try {
            if (UrlIndex == 0) {
                page++;
                response = OpenConnection.callUrl(ServiceUrl.SABAKUCH_EXAM_LIST + "?userid=" + strUserID/*+"id="+strUserID+"&key="+strServerKey+"&page="+page*/);
            } else if (UrlIndex == 1) {
                page = 1;
                response = OpenConnection.callUrl(ServiceUrl.SABAKUCH_EXAM_LIST + "?userid=" + strUserID/*+"id="+strUserID+"&key="+strServerKey+"&page="+page*/);
            } else if (UrlIndex == 3) {
                response = OpenConnection.callUrl(ServiceUrl.SABAKUCH_SOLUTION);
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
                    totalPage = SabaKuchParse.parsePageCount(str);
                    arrData = SabaKuchParse.parseExamsData(str);
                    arrListData.addAll(arrData);
                    adapter.notifyDataSetChanged();

                } else if (UrlIndex == 1) {
                    arrListData.clear();
                    totalPage = SabaKuchParse.parsePageCount(str);
                    arrData = SabaKuchParse.parseExamsData(str);
                    if (arrData.size() > 0) {

                        arrListData.addAll(arrData);

//                        if (adapter == null) {
//                            adapter = new SelectExamsAdapter(getActivity(), arrListData);
//                            recyclerView.setAdapter(adapter);
//                        } else {
                        adapter.notifyDataSetChanged();
//                        }
                    }
//                    UrlIndex = 3;
//                    APIAccess.fetchPagingData(SelectExamsFragment.this, getActivity(), getActivity(), true);
                } else if (UrlIndex == 3) {
                    CommonUtils.LogMsg("TAG", "arrListData before: " + arrListData.size());
                    mSelectExamsData = SabaKuchParse.parseSolutionData(str);
                    arrListData.add(arrListData.size(), mSelectExamsData);
                    adapter.notifyDataSetChanged();

                    CommonUtils.LogMsg("TAG", "mSelectExamsData: " + "arrListData: " + arrListData.size() + CommonUtils.getCustomGson().toJson(arrListData));
//                    iv_solution_paper.setVisibility(View.VISIBLE);
//                    Picasso.with(context).load(mSelectExamsData.solution_image).into(iv_solution_paper);
                }

            } else {
                Toast.makeText(context, getResources().getString(R.string.server_not_responding), Toast.LENGTH_SHORT).show();
            }
        }

        return null;
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
}
