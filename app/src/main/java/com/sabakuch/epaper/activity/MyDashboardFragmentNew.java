package com.sabakuch.epaper.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sabakuch.epaper.R;
import com.sabakuch.epaper.apiclass.APIAccess;
import com.sabakuch.epaper.apputils.CommonUtils;
import com.sabakuch.epaper.apputils.SabaKuchParse;
import com.sabakuch.epaper.apputils.ServiceUrl;
import com.sabakuch.epaper.constants.Constants;
import com.sabakuch.epaper.data.DashboardData;
import com.sabakuch.epaper.data.DashboardHeaderData;
import com.sabakuch.epaper.serviceclasses.OpenConnection;
import com.sabakuch.epaper.serviceclasses.ServiceInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyDashboardFragmentNew extends Fragment implements ServiceInterface {
    private static String[] levelType = {"CBSE Class X", "CBSE Class XII", "ICSC Class X"};
    private View rootView;
    private DashboardExpListAdapterNew listAdapter;
    private Activity context;
    private ExpandableListView exlvMyDashboard;
    private HashMap<String, String> listData = new HashMap<String, String>();
    private List<DashboardHeaderData> listDataHeader = new ArrayList<DashboardHeaderData>();
    private ArrayList<DashboardData> arrData, arrListData = new ArrayList<>();
    private int UrlIndex = 1;
    private int page = 1;
    private int totalPage;
    private String strUserID;
    private int pastVisiblesItems, visibleItemCount, totalItemCount;
    private boolean loadingMore = false;
  //  private Button pmumdash;

    public MyDashboardFragmentNew() {
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
        rootView = inflater.inflate(R.layout.fragment_my_dashboard, container, false);
        strUserID = CommonUtils.getStringPreferences(context, Constants.USER_ID);
        CommonUtils.setTracking("Statistics");
        setId();
        setExpandableListViewData();
        setupToolbar();
       // APIAccess.fetchData(MyDashboardFragmentNew.this, getActivity(), getActivity());
        return rootView;
    }

    private void setId() {
        listDataHeader.clear();
        exlvMyDashboard = (ExpandableListView) rootView.findViewById(R.id.exlv_my_dashboard);
      //  pmumdash = (Button) rootView.findViewById(R.id.pmumdash);

        listAdapter = new DashboardExpListAdapterNew(context, listDataHeader, listData);
        exlvMyDashboard.setAdapter(listAdapter);
        listAdapter.notifyDataSetChanged();
     /*   pmumdash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new MyDashboardFragmentPay();

                setFragment(fragment);

            }
        });*/

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

    private void setExpandableListViewData() {
        exlvMyDashboard.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int i,
                                 int i1, int i2) {
//                if (loadingMore){
//                    if (visibleItemCount > 0) {
//                        int lastInScreen = firstVisibleItem + visibleItemCount;
//
//
//                        if ((lastInScreen == totalItemCount)) {
//                            if (totalPage > page) {
//                                UrlIndex = 0;
//                                APIAccess.fetchPagingData(MyDashboardFragment.this, getActivity(), getActivity(), true);
//                            } else {
//                            }
//                        }
//                    }
//                    loadingMore =false;
//            }
                visibleItemCount = exlvMyDashboard.getChildCount();
                totalItemCount = exlvMyDashboard.getCheckedItemCount();
                pastVisiblesItems = exlvMyDashboard.getFirstVisiblePosition();
                if (loadingMore) {
                    if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                        if (totalPage > page) {
                            UrlIndex = 0;
                            APIAccess.fetchPagingData(MyDashboardFragmentNew.this, getActivity(), getActivity(), true);
                        } else {
                        }
                    }
                    loadingMore = false;
                }
            }
        });

        exlvMyDashboard.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent,
                                        View v, int groupPosition, long id) {

                return false;
            }
        });

        // Listview Group expanded listener
        exlvMyDashboard
                .setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
                    int previousGroup = -1;

                    @Override
                    public void onGroupExpand(int groupPosition) {

                        if (groupPosition != previousGroup)
                            exlvMyDashboard.collapseGroup(previousGroup);
                        previousGroup = groupPosition;
                    }
                });

        // Listview Group collasped listener
        exlvMyDashboard
                .setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

                    @Override
                    public void onGroupCollapse(int groupPosition) {

                    }
                });

        // Listview on child click listener
        exlvMyDashboard
                .setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

                    @Override
                    public boolean onChildClick(ExpandableListView parent,
                                                View v, int groupPosition, int childPosition,
                                                long id) {

                        return false;
                    }
                });

    }

    private void prepareListData(ArrayList<DashboardData> arrListData) {


        for (int i = 0; i < arrListData.size(); i++) {
            listDataHeader.add(new DashboardHeaderData(arrListData.get(i).exam_name, arrListData.get(i).exam_date, arrListData.get(i).level_id
                    , arrListData.get(i).last_paper_id, arrListData.get(i).score_id));

            String examDate = CommonUtils.getFormatedDate(listDataHeader.get(i).getExamDate());

            String level = "";
            if (listDataHeader.get(i).getLevelId() != null && listDataHeader.get(i).getLevelId().length() > 0) {
                if (listDataHeader.get(i).getLevelId().equalsIgnoreCase("1")) {
                    level = "Level :- Beginner";
                } else if (listDataHeader.get(i).getLevelId().equalsIgnoreCase("2")) {
                    level = "Level :- Intermediate";
                } else if (listDataHeader.get(i).getLevelId().equalsIgnoreCase("3")) {
                    level = "Level :- Expert";
                }
                else if (listDataHeader.get(i).getLevelId().equalsIgnoreCase("4")) {
                    level = "Level :- Premium";
                }
            } else {
                level = "";
            }

            listData.put(listDataHeader.get(i).getLastPaperId(), ("Paper :- " + listDataHeader.get(i).getExamName()) + "\n" + ("Exam Date :- " +
                    examDate) + "\n" + level);
        }
        listAdapter.notifyDataSetChanged();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    protected void setFragment(Fragment fragment) {
        FragmentTransaction ftTransaction = ((FragmentActivity)getActivity()).getSupportFragmentManager().beginTransaction();
        ftTransaction.replace(R.id.container_body, fragment)
                .addToBackStack(null);
        ftTransaction.commit();
    }
    @Override
    public String httpPost() {
        String response = "";
        try {
            if (UrlIndex == 0) {
                page++;
                response = OpenConnection.callUrl(ServiceUrl.SABAKUCH_PAPER_MY_DASHBOARD + "?feed=1" +  "&user_id=" + strUserID);
            } else if (UrlIndex == 1) {
                page = 1;
                response = OpenConnection.callUrl(ServiceUrl.SABAKUCH_PAPER_MY_DASHBOARD + "?feed=1" +  "&user_id=" + strUserID);
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
                        loadingMore = true;
                        arrListData.addAll(arrData);
                        prepareListData(arrListData);

                    }

                } else {

                    if (arrData.size() > 0) {
                        loadingMore = true;
                        if (arrListData != null && arrListData.size() > 0) {
                            arrListData.clear();
                        }
                        arrListData.addAll(arrData);

                        prepareListData(arrListData);
                    }
                }

            } else {
                Toast.makeText(context, getResources().getString(R.string.server_not_responding), Toast.LENGTH_SHORT).show();
            }
        }

        return null;
    }

    @Override
    public void setUserVisibleHint(boolean visible) {
        super.setUserVisibleHint(visible);
        if (visible && isResumed()) {
            onResume();
        }
    }

    @Override
    public void onResume() {
        UrlIndex = 1;
        page = 1;
        APIAccess.fetchData(MyDashboardFragmentNew.this, getActivity(), getActivity());
        super.onResume();
    }
}
