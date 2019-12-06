package com.sabakuch.epaper.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sabakuch.epaper.R;
import com.sabakuch.epaper.adapter.OnlinePartReviewAdapter;
import com.sabakuch.epaper.adapter.TestReviewAdapter;
import com.sabakuch.epaper.apiclass.APIAccess;
import com.sabakuch.epaper.apputils.CommonUtils;
import com.sabakuch.epaper.apputils.SabaKuchParse;
import com.sabakuch.epaper.apputils.ServiceUrl;
import com.sabakuch.epaper.constants.Constants;
import com.sabakuch.epaper.data.TestPaperData;
import com.sabakuch.epaper.data.TestResultData;
import com.sabakuch.epaper.serviceclasses.OpenConnection;
import com.sabakuch.epaper.serviceclasses.ServiceInterface;

import java.util.ArrayList;


public class TestReviewFragment extends Fragment implements ServiceInterface {
    String examId;
    private View rootView;
    private RecyclerView recyclerView;
    private ListView lstReview;
    private TestReviewAdapter adapter;
    private OnlinePartReviewAdapter mOnlinePartReviewAdapter;
    private Activity context;
    private TextView tvTime, tvExamName, tvSubject, tvLevel, tvTotalMarks, tvObtainedMarks;
    private String lastPaperId, strUserID;
    private int UrlIndex = 0;
    private int page = 1;
    private int totalPage;
    private ProgressDialog pb;
    private ArrayList<TestPaperData> arrData = new ArrayList<>(), arrListData = new ArrayList<>();
    private ArrayList<TestResultData> arrPaperDetailData = new ArrayList<>();
    private TextView mTitle;
    private LinearLayoutManager linearLayoutManager;
    private int totalItemCount = 0, visibleItemCount = 0;
    private int pastVisiblesItems = 0;
    boolean loading = false;

    public TestReviewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
        strUserID = CommonUtils.getStringPreferences(getActivity(), Constants.USER_ID);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_test_review, container, false);
        CommonUtils.setTracking(TestReviewFragment.class.getSimpleName() + ", " + Constants.EXAM_ID + ":" + examId);
        setId();
        setRecyclerViewAdapter();
        setupToolbar();
        Bundle bundle = getArguments();
        if (bundle != null) {
            lastPaperId = bundle.getString(Constants.LAST_PAPER_ID);
        }

        CommonUtils.setTracking(TestReviewFragment.class.getSimpleName() + ", " + Constants.LAST_PAPER_ID + ":" + lastPaperId);
        try {
            if (context != null && !context.isFinishing())
                if (CommonUtils.isOnline(context)) {
                    pb = ProgressDialog.show(context, "", context.getResources().getString(R.string.loader_text));
                    APIAccess.fetchPagingData(TestReviewFragment.this, getActivity(), getActivity(), true);
                } else {
                    if (context != null && pb != null && pb.isShowing())
                        pb.dismiss();
                    Toast.makeText(context, context.getResources().getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                }

        } catch (Exception e) {
        }


        return rootView;
    }

    private void setId() {
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        tvExamName = (TextView) rootView.findViewById(R.id.tv_exam_name);
        tvSubject = (TextView) rootView.findViewById(R.id.tv_subject);
        tvLevel = (TextView) rootView.findViewById(R.id.tv_level);
        tvTime = (TextView) rootView.findViewById(R.id.tv_time_taken);
        tvTotalMarks = (TextView) rootView.findViewById(R.id.tv_total_marks);
        tvObtainedMarks = (TextView) rootView.findViewById(R.id.tv_obtained_marks);
        lstReview = (ListView) rootView.findViewById(R.id.lstReview);
    }

    private void setupToolbar() {
        Toolbar mToolbar = (Toolbar) context.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        final ActionBar ab = ((AppCompatActivity) getActivity()).getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(false);
        ab.setIcon(0);
        mTitle = (TextView) mToolbar.findViewById(R.id.tv_title);
        mTitle.setVisibility(View.VISIBLE);
        mTitle.setText(getResources().getString(R.string.test_review));
    }

    private void setRecyclerViewAdapter() {

        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
//        recyclerView.setOnScrollListener(new EndlessRecyclerOnScrollListener(linearLayoutManager) {
//            @Override
//            public void onLoadMore(int current_page) {
//                CommonUtils.LogMsg("totalPage count", "totalPage count" + totalPage + " " + page);
//                if (totalPage > page) {
//                    UrlIndex = 1;
//                    APIAccess.fetchPagingData(TestReviewFragment.this, getActivity(), getActivity(), true);
//                }
//            }
//        });


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) //check for scroll down
                {
                    visibleItemCount = linearLayoutManager.getChildCount();
                    totalItemCount = linearLayoutManager.getItemCount();
                    pastVisiblesItems = linearLayoutManager.findFirstVisibleItemPosition();

                    if (loading) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            loading = false;
                            //Do pagination.. i.e. fetch new data
                            if (totalPage > page) {
                                UrlIndex = 1;
                                APIAccess.fetchPagingData(TestReviewFragment.this, getActivity(), getActivity(), true);
                            }
                        }
                    }
                }
            }
        });

        lstReview.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {


                if (firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount != 0) {
                    if (loading) {
                        loading = false;
                        if (totalPage > page) {
                            UrlIndex = 1;
                            APIAccess.fetchPagingData(TestReviewFragment.this, getActivity(), getActivity(), true);
                        }
                    }
                }
            }
        });

        mOnlinePartReviewAdapter = new OnlinePartReviewAdapter(context, arrListData);
        lstReview.setAdapter(mOnlinePartReviewAdapter);
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
                response = OpenConnection.callUrl(ServiceUrl.SABAKUCH_PAPER_ANSWERS + "?last_paper_id=" + lastPaperId);
            } else if (UrlIndex == 1) {
                page++;
                response = OpenConnection.callUrl(ServiceUrl.SABAKUCH_PAPER_REVIEW + "?page=" + page + "&last_paper_id=" + lastPaperId + "&type=" + "review" + "&user_id=" + strUserID);
            } else if (UrlIndex == 2) {
                page = 1;
                response = OpenConnection.callUrl(ServiceUrl.SABAKUCH_PAPER_REVIEW + "?page=" + page + "&last_paper_id=" + lastPaperId + "&type=" + "review" + "&user_id=" + strUserID);
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

                    arrPaperDetailData = SabaKuchParse.parseTestResultData(str);
                    if (arrPaperDetailData.size() > 0) {
                        tvTime.setText(arrPaperDetailData.get(0).time_taken != null && arrPaperDetailData.get(0).time_taken.length() > 0 ? "Time Taken : " + arrPaperDetailData.get(0).time_taken : "");
                        mTitle.setText(arrPaperDetailData.get(0).exam_name != null && arrPaperDetailData.get(0).exam_name.length() > 0 ? arrPaperDetailData.get(0).exam_name : "");
                        tvSubject.setVisibility(View.GONE);
                        if (arrPaperDetailData.get(0).total_marks != null && arrPaperDetailData.get(0).total_marks.length() > 0) {
                            tvTotalMarks.setVisibility(View.VISIBLE);
                            tvTotalMarks.setText(arrPaperDetailData.get(0).total_marks != null && arrPaperDetailData.get(0).total_marks.length() > 0 ? "Total Marks : " + arrPaperDetailData.get(0).total_marks : "");
                        } else {
                            tvTotalMarks.setVisibility(View.GONE);
                        }

                        if (arrPaperDetailData.get(0).obtained_marks != null && arrPaperDetailData.get(0).obtained_marks.length() > 0) {
                            tvObtainedMarks.setVisibility(View.VISIBLE);
                            tvObtainedMarks.setText(arrPaperDetailData.get(0).obtained_marks != null && arrPaperDetailData.get(0).obtained_marks.length() > 0 ? "Obtained Marks : " + arrPaperDetailData.get(0).obtained_marks : "");
                        } else {
                            tvObtainedMarks.setVisibility(View.GONE);
                        }
                        if (arrPaperDetailData.get(0).examid != null)
                            examId = arrPaperDetailData.get(0).examid;

                        if (arrPaperDetailData.get(0).levelid != null && arrPaperDetailData.get(0).levelid.length() > 0) {
                            if (arrPaperDetailData.get(0).levelid.equalsIgnoreCase("1")) {
                                tvLevel.setText("Level : Beginner");
                            } else if (arrPaperDetailData.get(0).levelid.equalsIgnoreCase("2")) {
                                tvLevel.setText("Level : Intermediate");
                            } else if (arrPaperDetailData.get(0).levelid.equalsIgnoreCase("3")) {
                                tvLevel.setText("Level : Expert");
                            }
                        } else {
                            tvLevel.setText("");
                        }
                    }
                    if (examId.equals("9")) {
                        lstReview.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    } else {
                        lstReview.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                    }
                    UrlIndex = 2;
                    APIAccess.fetchPagingData(TestReviewFragment.this, getActivity(), getActivity(), true);
                } else if (UrlIndex == 1) {
                    if (context != null && pb != null && pb.isShowing())
                        pb.dismiss();
                    totalPage = SabaKuchParse.parsePageCount(str);
                    arrData = SabaKuchParse.parseReviewTestPaperData(str);

                    if (arrData.size() > 0) {
                        arrListData.addAll(arrData);
                        if (examId.equals("9"))
                            mOnlinePartReviewAdapter.notifyDataSetChanged();
                        else
                            populateSampleData(arrListData);
                    }
                    loading= true;
                } else if (UrlIndex == 2) {
                    if (context != null && pb != null && pb.isShowing())
                        pb.dismiss();
                    totalPage = SabaKuchParse.parsePageCount(str);
                    arrData = SabaKuchParse.parseReviewTestPaperData(str);
                    if (arrData.size() > 0) {
                        if (arrListData != null && arrListData.size() > 0) {
                            arrListData.clear();
                        }
                        arrListData.addAll(arrData);
                        if (examId.equals("9")) {
                            mOnlinePartReviewAdapter = new OnlinePartReviewAdapter(context, arrListData);
                            lstReview.setAdapter(mOnlinePartReviewAdapter);
                        } else
                            populateSampleData(arrListData);
                    }
                    loading=true;
                }

            } else {
                if (context != null && pb != null && pb.isShowing())
                    pb.dismiss();
                Toast.makeText(context, getResources().getString(R.string.server_not_responding), Toast.LENGTH_SHORT).show();
            }
        }
        return null;
    }

    private void populateSampleData(ArrayList<TestPaperData> arrData) {
        for (int i = 0; i < arrData.size(); i++) {
            TestPaperData dm = new TestPaperData();
            dm.setHeaderTitle("" + i);

        }
        if (adapter == null) {
            adapter = new TestReviewAdapter(context, arrData);
            recyclerView.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }
}
