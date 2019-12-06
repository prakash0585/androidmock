package com.sabakuch.epaper.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.sabakuch.epaper.R;
import com.sabakuch.epaper.adapter.OnlinePartAdapter;
import com.sabakuch.epaper.adapter.ProfileSettingsAdapter;
import com.sabakuch.epaper.apiclass.APIAccess;
import com.sabakuch.epaper.apputils.CommonUtils;
import com.sabakuch.epaper.apputils.SabaKuchParse;
import com.sabakuch.epaper.apputils.ServiceUrl;
import com.sabakuch.epaper.constants.Constants;
import com.sabakuch.epaper.data.PaperDetailData;
import com.sabakuch.epaper.data.QuestionDataResponse;
import com.sabakuch.epaper.data.SectionDetail;
import com.sabakuch.epaper.data.SectionDetailData;
import com.sabakuch.epaper.data.SectionDetailPartData;
import com.sabakuch.epaper.data.TestPaperData;
import com.sabakuch.epaper.entitymime.MultipartEntity;
import com.sabakuch.epaper.entitymimecontent.StringBody;
import com.sabakuch.epaper.fragment.OnlineTestPaperSectionFragment;
import com.sabakuch.epaper.serviceclasses.OpenConnection;
import com.sabakuch.epaper.serviceclasses.ServiceInterface;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class OnlineTestPaperSectionFragmentNew extends Fragment implements View.OnClickListener, ServiceInterface {
    public static ArrayList<String> selectedAnswers1, selectedAnswers2, selectedAnswers3, selectedAnswers4;
    public static ArrayList<String> selectedQuestionId1, selectedQuestionId2, selectedQuestionId3, selectedQuestionId4, selectedAssignId;
    private static CountDownTimer countDownTimer;
    public ArrayList<String> selectedchkAnswers1 = new ArrayList<>();
    public ArrayList<String> selectedchkAnswers2 = new ArrayList<>();
    public ArrayList<String> selectedchkAnswers3 = new ArrayList<>();
    public ArrayList<String> selectedchkQuestionId1 = new ArrayList<>();
    public ArrayList<String> selectedchkQuestionId2 = new ArrayList<>();
    public ArrayList<String> selectedchkQuestionId3 = new ArrayList<>();
    ArrayList<TestPaperData> sec1ArrayData, sec2ArrayData, sec3ArrayData, sec4ArrayData;
    int check = 0;
    boolean loading = false;
    private View rootView;
    private RecyclerView recyclerView;
    private ListView lstPart;
    private OnlineTestPaperAdapterNew adapter;
    //    private OnlineTestPartPaperAdapter mOnlineTestPartPaperAdapter;
    private OnlinePartAdapter mOnlinePartAdapter;
    private Intent intent;
    private String strUserID, strServerKey;
    private String strUserName;
   // private TextView tvTimerValue, tvFinish, tvExamName, tvSubject, tvLevel, tvDuration;
    private TextView tvTimerValue,tvDuration;
    private Button tvFinish;
    private Activity context;
    private ArrayList<ArrayList<SectionDetail.Sectiondetail>> sectiondetailinfo = new ArrayList<>();
    private ArrayList<TestPaperData> arrData1 = new ArrayList<>(), arrListData1 = new ArrayList<>();
    private ArrayList<TestPaperData> arrData2 = new ArrayList<>(), arrListData2 = new ArrayList<>();
    private ArrayList<TestPaperData> arrData3 = new ArrayList<>(), arrListData3 = new ArrayList<>();
    private ArrayList<TestPaperData> arrData4 = new ArrayList<>(), arrListData4 = new ArrayList<>();
    private ArrayList<PaperDetailData> arrPaperDetailData = new ArrayList<>();
    private List<TestPaperData.Question> arrQuestions = new ArrayList<>();
    private int UrlIndex = 0;
    private int pageSection1 = 1, pageSection2 = 1, pageSection3 = 1, pageSection4 = 1;
    private int totalPageSection1, totalPageSection2, totalPageSection3, totalPageSection4;
    private String type;
    private String examId = "", levelId = "";
    private String institutionname = "";
    private String timerValue = "90";
    private ProgressDialog pb;
    private MultipartEntity reqEntity;
    private ArrayList<SectionDetailData> arrSectionData = new ArrayList<>();
    private ArrayList<SectionDetailPartData.Sectiondetail> sectiondetail = new ArrayList<>();
    private ArrayList<QuestionDataResponse.Question> questions = new ArrayList<>();
    private ArrayList<QuestionDataResponse.Question> questions1 = new ArrayList<>();
    private ArrayList<QuestionDataResponse.Question> questions2 = new ArrayList<>();
    private ArrayList<QuestionDataResponse.Question> questions3 = new ArrayList<>();
    private ArrayList<SectionDetailPartData.Section> sections = new ArrayList<>();
    private String[] arrayAnswers;
    private String startTime, endTime;
    private String jsonAnswers;
  // private TextView mTitle, tvTotalMarks;
   private TextView exnmane;
   private TextView unamei;
   private TextView mTitle;
    private Spinner spSection;
    private ProfileSettingsAdapter sectionAdapter;
    private ArrayList<String> alSectionId = new ArrayList<>();
    private String strSectionId;
    private LinearLayoutManager linearLayoutManager;
    private int sectionPosition = 0;
    private int selectionPosition = 0;
    private boolean firstTimeEnable = true;
    private int totalItemCount = 0, visibleItemCount = 0;
    private int pastVisiblesItems = 0;
    private ArrayList<TestPaperData.Passage> passage = new ArrayList<>();
    private String TAG = OnlineTestPaperSectionFragment.class.getSimpleName();

    public OnlineTestPaperSectionFragmentNew() {
        // Required empty public constructor
    }

    public static String getCurrentTimeStamp() {
        //yyyy-mm-dd hh:ii:ss// 24 hours
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());//dd/MM/yyyy
        Date now = new Date();
        String strDate = sdfDate.format(now);
        return strDate;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
        strUserID = CommonUtils.getStringPreferences(getActivity(), Constants.USER_ID);
        strUserName = CommonUtils.getStringPreferences(getActivity(), Constants.USER_NAME);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.online_testy, container, false);
        Bundle bundle = getArguments();
        if (bundle != null) {
            examId = bundle.getString(Constants.EXAM_ID);
            levelId = bundle.getString(Constants.LEVEL_ID);
            type = bundle.getString(Constants.EXAM_TYPE);
            institutionname = bundle.getString("INSTITUTE_NAME");
            //arrSection

            CommonUtils.setTracking("OnlineTestPaperSectionFragment, " + Constants.USER_ID + " : " + strUserID + ", " + Constants.EXAM_ID + examId + ", " + Constants.LEVEL_ID + " : " + levelId + ", " + Constants.EXAM_TYPE + " : " + type);
        }
        selectedAnswers1 = new ArrayList<>();
        selectedQuestionId1 = new ArrayList<>();
        selectedAnswers2 = new ArrayList<>();
        selectedQuestionId2 = new ArrayList<>();
        selectedAnswers3 = new ArrayList<>();
        selectedQuestionId3 = new ArrayList<>();
        selectedAnswers4 = new ArrayList<>();
        selectedQuestionId4 = new ArrayList<>();
        selectedAssignId = new ArrayList<>();
        setId();
        setRecyclerViewAdapter();
        setupToolbar();
        //    startTimer();

        try {
            if (context != null && !context.isFinishing())
                if (CommonUtils.isOnline(context)) {
                    pb = ProgressDialog.show(context, "", context.getResources().getString(R.string.loader_text));
                    UrlIndex = 0;
                    APIAccess.fetchPagingData(OnlineTestPaperSectionFragmentNew.this, getActivity(), getActivity(), true);

                } else {
                    if (context != null && pb != null && pb.isShowing())
                        pb.dismiss();
                    Toast.makeText(context, context.getResources().getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                }

        } catch (Exception e) {
            Log.e(OnlineTestPaperSectionFragment.class.getSimpleName(), e.getMessage(), e);
        }
        return rootView;
    }


    private void convertTime(String minutes) {
        //If CountDownTimer is null then start timer
        if (countDownTimer == null) {
            String getMinutes = minutes;
            if (!getMinutes.equals("") && getMinutes.length() > 0) {
                int noOfMinutes = Integer.parseInt(getMinutes) * 60 * 1000;//Convert minutes into milliseconds
                startTime = getCurrentTimeStamp();
                startTimer(noOfMinutes);//start countdown
            }
        } else {
            //Else stop timer and change text
            stopCountdown();
            //      startTimer.setText(getString(R.string.start_timer));
        }
    }

    private void setId() {
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        lstPart = (ListView) rootView.findViewById(R.id.lstPart);
      tvTimerValue = (TextView) rootView.findViewById(R.id.tv_timer);
        exnmane = (TextView) rootView.findViewById(R.id.exnmane);
        unamei = (TextView) rootView.findViewById(R.id.unamei);
        tvFinish = (Button) rootView.findViewById(R.id.tv_finish);
     //   tvExamName = (TextView) rootView.findViewById(R.id.tv_exam_name);
     //   tvSubject = (TextView) rootView.findViewById(R.id.tv_subject);
     //   tvLevel = (TextView) rootView.findViewById(R.id.tv_level);
        tvDuration = (TextView) rootView.findViewById(R.id.tv_duration);
        spSection = (Spinner) rootView.findViewById(R.id.sp_section);
      // tvTotalMarks = (TextView) rootView.findViewById(R.id.tv_total_marks);
        tvFinish.setEnabled(false);
        tvFinish.setOnClickListener(this);

        // exam id == 9 when subject is Jee Advance
        if (examId.equals("9") || examId.equals("16")) {
            recyclerView.setVisibility(View.GONE);
            lstPart.setVisibility(View.VISIBLE);
        } else {
            lstPart.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
        exnmane.setText(getResources().getString(R.string.online_test));
        unamei.setText(strUserName);

        spSection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                check = check + 1;
                if (check > 1) {
                    strSectionId = alSectionId.get(position).toString();
                    sectionPosition = position;
                    // exam id == 9 when subject is Jee Advance
                    if (!examId.equals("9") && !examId.equals("16")) {
                        if (sectionPosition == 0) {
                            if (sec1ArrayData != null && sec1ArrayData.size() > 0) {
                                if (arrListData1 != null && arrListData1.size() > 0) {
                                    arrListData1.clear();
                                }
                                arrListData1.addAll(sec1ArrayData);
                                adapter = new OnlineTestPaperAdapterNew(context, arrListData1, sectionPosition);
                                recyclerView.setAdapter(adapter);
                            } else {
                                adapter = null;
                                UrlIndex = 3;
                                APIAccess.fetchPagingData(OnlineTestPaperSectionFragmentNew.this, getActivity(), getActivity(), true);
                            }
                        } else if (sectionPosition == 1) {
                            if (sec2ArrayData != null && sec2ArrayData.size() > 0) {
                                //   ArrayList arrListData2 = new ArrayList<>();
                                if (arrListData2 != null && arrListData2.size() > 0) {
                                    arrListData2.clear();
                                }
                                arrListData2.addAll(sec2ArrayData);
                                adapter = new OnlineTestPaperAdapterNew(context, arrListData2, sectionPosition);
                                recyclerView.setAdapter(adapter);
                            } else {
                                adapter = null;
                                UrlIndex = 3;
                                APIAccess.fetchPagingData(OnlineTestPaperSectionFragmentNew.this, getActivity(), getActivity(), true);
                            }
                        } else if (sectionPosition == 2) {
                            if (sec3ArrayData != null && sec3ArrayData.size() > 0) {
                                if (arrListData3 != null && arrListData3.size() > 0) {
                                    arrListData3.clear();
                                }
                                arrListData3.addAll(sec3ArrayData);
                                adapter = new OnlineTestPaperAdapterNew(context, arrListData3, sectionPosition);
                                recyclerView.setAdapter(adapter);
                            } else {
                                adapter = null;
                                UrlIndex = 3;
                                APIAccess.fetchPagingData(OnlineTestPaperSectionFragmentNew.this, getActivity(), getActivity(), true);
                            }
                        } else if (sectionPosition == 3) {
                            if (sec4ArrayData != null && sec4ArrayData.size() > 0) {
                                if (arrListData4 != null && arrListData4.size() > 0) {
                                    arrListData4.clear();
                                }
                                arrListData4.addAll(sec4ArrayData);
                                adapter = new OnlineTestPaperAdapterNew(context, arrListData4, sectionPosition);
                                recyclerView.setAdapter(adapter);
                            } else {
                                adapter = null;
                                UrlIndex = 3;
                                APIAccess.fetchPagingData(OnlineTestPaperSectionFragmentNew.this, getActivity(), getActivity(), true);
                            }
                        }
                    } else {
                        CommonUtils.LogMsg(TAG, "ques:  " + questions.size());
                        CommonUtils.LogMsg(TAG, "ques1:  " + questions1.size());
                        CommonUtils.LogMsg(TAG, "ques2:  " + questions2.size());
                        CommonUtils.LogMsg(TAG, "ques3:  " + questions3.size());

                        if (sectionPosition == 0) {
                            selectionPosition = 0;

                            CommonUtils.LogMsg(TAG, "sectionPosition:  " + sectionPosition + " selectionPosition: " + selectionPosition);
                            // check array ques size - If 0 then call API else populate last array saved
                            if (questions1 != null)
                                if (questions1.size() != 0) {
                                    populateQuesData(questions1);
                                } else {
                                    questions.clear();
                                    if (examId.equals("9")) {
                                        UrlIndex = 7;
                                        APIAccess.fetchPagingData(OnlineTestPaperSectionFragmentNew.this, getActivity(), getActivity(), true);
                                    } else if (examId.equals("16")) {
                                        UrlIndex = 8;
                                        APIAccess.fetchPagingData(OnlineTestPaperSectionFragmentNew.this, getActivity(), getActivity(), true);
                                    }
                                }


                        } else if (sectionPosition == 1) {
                            selectionPosition = 0;
                            CommonUtils.LogMsg(TAG, "sectionPosition:  " + sectionPosition + " selectionPosition: " + selectionPosition);
                            CommonUtils.LogMsg(TAG, "ques2:  " + questions2.size());
                            // check array ques size - If 0 then call API else populate last array saved
                            if (questions2 != null)
                                if (questions2.size() != 0) {
                                    populateQuesData(questions2);
                                } else {
                                    questions.clear();
                                    if (examId.equals("9")) {
                                        UrlIndex = 7;
                                        APIAccess.fetchPagingData(OnlineTestPaperSectionFragmentNew.this, getActivity(), getActivity(), true);
                                    } else if (examId.equals("16")) {
                                        UrlIndex = 8;
                                        APIAccess.fetchPagingData(OnlineTestPaperSectionFragmentNew.this, getActivity(), getActivity(), true);
                                    }
                                }

                        } else if (sectionPosition == 2) {
                            selectionPosition = 0;
                            CommonUtils.LogMsg(TAG, "sectionPosition:  " + sectionPosition + " selectionPosition: " + selectionPosition);
                            CommonUtils.LogMsg(TAG, "ques3:  " + questions3.size());
                            // check array ques size - If 0 then call API else populate last array saved
                            if (questions3 != null)
                                if (questions3.size() != 0) {
                                    populateQuesData(questions3);
                                } else {

                                    questions.clear();
                                    if (examId.equals("9")) {
                                        UrlIndex = 7;
                                        APIAccess.fetchPagingData(OnlineTestPaperSectionFragmentNew.this, getActivity(), getActivity(), true);
                                    } else if (examId.equals("16")) {
                                        UrlIndex = 8;
                                        APIAccess.fetchPagingData(OnlineTestPaperSectionFragmentNew.this, getActivity(), getActivity(), true);
                                    }
                                }

                        }


                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        getView().setFocusableInTouchMode(true);
//        getView().requestFocus();
//        getView().setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                if (event.getAction() == KeyEvent.ACTION_DOWN) {
//                    if (keyCode == KeyEvent.KEYCODE_BACK) {
////                    ((MainActivity) getActivity()).quitExamAlertDialog(0);
//                        // not allow fragment to answer back button
//                        return true;
//
//                    }
//                }
//                return false;
//            }
//        });
//    }

    private void setupToolbar() {
        Toolbar mToolbar = (Toolbar) context.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        final ActionBar ab = ((AppCompatActivity) getActivity()).getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(false);
        ab.setIcon(0);
        mTitle = (TextView) mToolbar.findViewById(R.id.tv_title);
        mTitle.setVisibility(View.VISIBLE);
        mTitle.setText(getResources().getString(R.string.online_test));
    }

    private void setRecyclerViewAdapter() {
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);


//        lstPart.setOnScrollListener(new AbsListView.OnScrollListener() {
//
//            @Override
//            public void onScrollStateChanged(AbsListView view, int scrollState) {
//            }
//
//            @Override
//            public void onScroll(AbsListView view, int firstVisibleItem,
//                                 int visibleItemCount, int totalItemCount) {
//
//
//                if (firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount != 0) {
//                    if (loading) {
////                        selectionPosition++;
////                        UrlIndex = 9;
////                        APIAccess.fetchPagingData(OnlineTestPaperSectionFragment.this, getActivity(), getActivity(), true);
////                        mPageMessageDetailAdapter.notifyDataSetChanged();
//                        loading = false;
//                    }
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
                            if (!examId.equals("9") && !examId.equals("16")) {
                                if (sectionPosition == 0) {
                                    if (totalPageSection1 > pageSection1) {
                                        UrlIndex = 2;
                                        APIAccess.fetchPagingData(OnlineTestPaperSectionFragmentNew.this, getActivity(), getActivity(), true);
                                    }
                                } else if (sectionPosition == 1) {
                                    if (totalPageSection2 > pageSection2) {
                                        UrlIndex = 2;
                                        APIAccess.fetchPagingData(OnlineTestPaperSectionFragmentNew.this, getActivity(), getActivity(), true);
                                    }
                                } else if (sectionPosition == 2) {
                                    if (totalPageSection3 > pageSection3) {
                                        UrlIndex = 2;
                                        APIAccess.fetchPagingData(OnlineTestPaperSectionFragmentNew.this, getActivity(), getActivity(), true);
                                    }
                                } else if (sectionPosition == 3) {
                                    if (totalPageSection4 > pageSection4) {
                                        UrlIndex = 2;
                                        APIAccess.fetchPagingData(OnlineTestPaperSectionFragmentNew.this, getActivity(), getActivity(), true);
                                    }
                                }
                            } else {
//                                selectionPosition++;
//                                UrlIndex = 9;
//                                APIAccess.fetchPagingData(OnlineTestPaperSectionFragment.this, getActivity(), getActivity(), true);
//                                mPageMessageDetailAdapter.notifyDataSetChanged();
                            }
                        }
                    }
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

    //Stop Countdown method
    private void stopCountdown() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
        }
    }

    //Start Countdown method
    private void startTimer(int noOfMinutes) {
        countDownTimer = new CountDownTimer(noOfMinutes, 1000) {
            public void onTick(long millisUntilFinished) {
                long millis = millisUntilFinished;
                //Convert milliseconds into hour,minute and seconds
                String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis), TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)), TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
                tvTimerValue.setText(hms);//set text
            }

            public void onFinish() {
                countDownTimer = null;//set CountDownTimer to null

                tvFinish.performClick();

            }
        }.start();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_finish:
                // exam id == 9 when subject is JEE Advance
                if (!examId.equals("9") && !examId.equals("16")) {
                    ArrayList<TestPaperData> arlAnswersData1 = new ArrayList<>();
                    if (selectedQuestionId1 != null && selectedQuestionId1.size() > 0) {
                        for (int i = 0; i < selectedQuestionId1.size(); i++) {
                            TestPaperData testPaperData = new TestPaperData();
                            testPaperData.q_id = selectedQuestionId1.get(i);
                            testPaperData.answer_opt = selectedAnswers1.get(i);
                            if (testPaperData.q_id.length() > 0 && testPaperData.answer_opt.length() > 0) {
                                for (int j = 0; j < arrListData1.size(); j++) {
                                    CommonUtils.LogMsg(TAG, "arrListData1: " + arrListData1.get(j).qb_id + "selectedQuestionId1.get(i)" + selectedQuestionId1.get(i));
                                    if (selectedQuestionId1.get(i).equals(arrListData1.get(j).qb_id)) {
                                        testPaperData.assign_id = arrListData1.get(j).assign_id;
                                        CommonUtils.LogMsg(TAG, "q_id1: " + arrListData1.get(j).assign_id);
                                    }
                                }
                                arlAnswersData1.add(testPaperData);
                            }
                        }


                        CommonUtils.LogMsg(TAG, "arlAnswersData1: " + CommonUtils.getCustomGson().toJson(arlAnswersData1));
                    }
                    if (selectedQuestionId2 != null && selectedQuestionId2.size() > 0) {
                        for (int i = 0; i < selectedQuestionId2.size(); i++) {
                            TestPaperData testPaperData = new TestPaperData();
                            testPaperData.q_id = selectedQuestionId2.get(i);
                            testPaperData.answer_opt = selectedAnswers2.get(i);
                            if (testPaperData.q_id.length() > 0 && testPaperData.answer_opt.length() > 0) {
                                for (int j = 0; j < arrListData2.size(); j++) {
                                    CommonUtils.LogMsg(TAG, "arrListData1: " + arrListData2.get(j).qb_id + "selectedQuestionId2.get(i)" + selectedQuestionId2.get(i));
                                    if (selectedQuestionId2.get(i).equals(arrListData2.get(j).qb_id)) {
                                        testPaperData.assign_id = arrListData2.get(j).assign_id;
                                        CommonUtils.LogMsg(TAG, "q_id1: " + arrListData2.get(j).assign_id);
                                    }
                                }
                                arlAnswersData1.add(testPaperData);
                            }
                        }
                    }
                    if (selectedQuestionId3 != null && selectedQuestionId3.size() > 0) {
                        for (int i = 0; i < selectedQuestionId3.size(); i++) {
                            TestPaperData testPaperData = new TestPaperData();
                            testPaperData.q_id = selectedQuestionId3.get(i);
                            testPaperData.answer_opt = selectedAnswers3.get(i);
                            if (testPaperData.q_id.length() > 0 && testPaperData.answer_opt.length() > 0) {
                                for (int j = 0; j < arrListData3.size(); j++) {
                                    CommonUtils.LogMsg(TAG, "arrListData1: " + arrListData3.get(j).qb_id + "selectedQuestionId3.get(i)" + selectedQuestionId3.get(i));
                                    if (selectedQuestionId3.get(i).equals(arrListData3.get(j).qb_id)) {
                                        testPaperData.assign_id = arrListData3.get(j).assign_id;
                                        CommonUtils.LogMsg(TAG, "q_id1: " + arrListData3.get(j).assign_id);
                                    }
                                }
                                arlAnswersData1.add(testPaperData);
                            }
                        }
                    }
                    if (selectedQuestionId4 != null && selectedQuestionId4.size() > 0) {
                        for (int i = 0; i < selectedQuestionId4.size(); i++) {
                            TestPaperData testPaperData = new TestPaperData();
                            testPaperData.q_id = selectedQuestionId4.get(i);
                            testPaperData.answer_opt = selectedAnswers4.get(i);
                            if (testPaperData.q_id.length() > 0 && testPaperData.answer_opt.length() > 0) {
                                for (int j = 0; j < arrListData4.size(); j++) {
                                    CommonUtils.LogMsg(TAG, "arrListData4: " + arrListData4.get(j).qb_id + "selectedQuestionId4.get(i)" + selectedQuestionId4.get(i));
                                    if (selectedQuestionId4.get(i).equals(arrListData4.get(j).qb_id)) {
                                        testPaperData.assign_id = arrListData4.get(j).assign_id;
                                        CommonUtils.LogMsg(TAG, "q_id1: " + arrListData4.get(j).assign_id);
                                    }
                                }

                                arlAnswersData1.add(testPaperData);
                            }
                        }
                    }

                    jsonAnswers = new Gson().toJson(arlAnswersData1);
                    CommonUtils.LogMsg(TAG, "jsonAnswers: " + CommonUtils.getCustomGson().toJson(jsonAnswers));
                    endTime = getCurrentTimeStamp();
                    stopCountdown();//stop count down

                    UrlIndex = 4;
                    APIAccess.fetchData(OnlineTestPaperSectionFragmentNew.this, getActivity(), getActivity());
                } else {
                    // saves array for section-0 and uiType- 1
                    ArrayList<TestPaperData> arlAnswersData = new ArrayList<>();
                    if (selectedQuestionId1 != null && selectedQuestionId1.size() > 0) {
                        for (int i = 0; i < selectedQuestionId1.size(); i++) {
                            TestPaperData testPaperData = new TestPaperData();
                            testPaperData.q_id = selectedQuestionId1.get(i);
                            testPaperData.answer_opt = selectedAnswers1.get(i);
                            if (testPaperData.q_id.length() > 0 && testPaperData.answer_opt.length() > 0) {
                                for (int j = 0; j < questions1.size(); j++) {
                                    CommonUtils.LogMsg(TAG, "questions: " + questions1.get(j).getQbId() + "selectedQuestionId1.get(i)" + selectedQuestionId1.get(i));
                                    if (selectedQuestionId1.get(i).equals(questions1.get(j).getQbId())) {
                                        testPaperData.assign_id = questions1.get(j).getAssign_id();
                                        CommonUtils.LogMsg(TAG, "Assign_id: " + questions1.get(j).getAssign_id());
                                    }
                                }
                                arlAnswersData.add(testPaperData);

                            }
                        }
                    }

                    // saves array for section-2 and uiType- 1
                    if (selectedQuestionId2 != null && selectedQuestionId2.size() > 0) {
                        for (int i = 0; i < selectedQuestionId2.size(); i++) {
                            TestPaperData testPaperData = new TestPaperData();
                            testPaperData.q_id = selectedQuestionId2.get(i);
                            testPaperData.answer_opt = selectedAnswers2.get(i);
                            if (testPaperData.q_id.length() > 0 && testPaperData.answer_opt.length() > 0) {
                                for (int j = 0; j < questions2.size(); j++) {
                                    CommonUtils.LogMsg(TAG, "questions: " + questions2.get(j).getQbId() + "selectedQuestionId2.get(i)" + selectedQuestionId2.get(i));
                                    if (selectedQuestionId2.get(i).equals(questions2.get(j).getQbId())) {
                                        testPaperData.assign_id = questions2.get(j).getAssign_id();
                                        CommonUtils.LogMsg(TAG, "Assign_id: " + questions2.get(j).getAssign_id());
                                    }
                                }
                                arlAnswersData.add(testPaperData);
                            }
                        }
                    }

                    // saves array for section-3 and uiType- 1
                    if (selectedQuestionId3 != null && selectedQuestionId3.size() > 0) {
                        for (int i = 0; i < selectedQuestionId3.size(); i++) {
                            TestPaperData testPaperData = new TestPaperData();
                            testPaperData.q_id = selectedQuestionId3.get(i);
                            testPaperData.answer_opt = selectedAnswers3.get(i);
                            if (testPaperData.q_id.length() > 0 && testPaperData.answer_opt.length() > 0) {
                                for (int j = 0; j < questions3.size(); j++) {
                                    CommonUtils.LogMsg(TAG, "questions: " + questions3.get(j).getQbId() + "selectedQuestionId3.get(i)" + selectedQuestionId3.get(i));
                                    if (selectedQuestionId3.get(i).equals(questions3.get(j).getQbId())) {
                                        testPaperData.assign_id = questions3.get(j).getAssign_id();
                                        CommonUtils.LogMsg(TAG, "Assign_id: " + questions3.get(j).getAssign_id());
                                    }
                                }
                                arlAnswersData.add(testPaperData);
                            }
                        }
                    }
                    CommonUtils.LogMsg(TAG, "questions1: " + CommonUtils.getCustomGson().toJson(questions1));
                    CommonUtils.LogMsg(TAG, "questions2: " + CommonUtils.getCustomGson().toJson(questions2));
                    CommonUtils.LogMsg(TAG, "questions3: " + CommonUtils.getCustomGson().toJson(questions3));
                    // saves array for section-0 and uiType- 2&3
                    for (int i = 0; i < questions1.size(); i++) {
                        if (questions1.get(i).getAnswer_opt() != null)
                            if (!questions1.get(i).getAnswer_opt().equals("")) {
                                TestPaperData testPaperData = new TestPaperData();
                                testPaperData.q_id = questions1.get(i).getQbId();
                                testPaperData.answer_opt = questions1.get(i).getAnswer_opt();
                                testPaperData.assign_id = questions1.get(i).getAssign_id();
                                if (testPaperData.q_id.length() > 0 && testPaperData.answer_opt.length() > 0) {
                                    arlAnswersData.add(testPaperData);
                                }
                            }
                    }
                    // saves array for section-2 and uiType- 2&3
                    for (int i = 0; i < questions2.size(); i++) {
                        if (questions2.get(i).getAnswer_opt() != null)
                            if (!questions2.get(i).getAnswer_opt().equals("")) {
                                TestPaperData testPaperData = new TestPaperData();
                                testPaperData.q_id = questions2.get(i).getQbId();
                                testPaperData.answer_opt = questions2.get(i).getAnswer_opt();
                                testPaperData.assign_id = questions2.get(i).getAssign_id();
                                if (testPaperData.q_id.length() > 0 && testPaperData.answer_opt.length() > 0) {

                                    arlAnswersData.add(testPaperData);
                                }
                            }
                    }

                    // saves array for section-3 and  uiType- 2&3

                    for (int i = 0; i < questions3.size(); i++) {
                        if (questions3.get(i).getAnswer_opt() != null)
                            if (!questions3.get(i).getAnswer_opt().equals("")) {
                                TestPaperData testPaperData = new TestPaperData();
                                testPaperData.q_id = questions3.get(i).getQbId();
                                testPaperData.answer_opt = questions3.get(i).getAnswer_opt();
                                testPaperData.assign_id = questions3.get(i).getAssign_id();
                                if (testPaperData.q_id.length() > 0 && testPaperData.answer_opt.length() > 0) {
                                    arlAnswersData.add(testPaperData);
                                }
                            }
                    }

                    jsonAnswers = new Gson().toJson(arlAnswersData);
                    CommonUtils.LogMsg(TAG, "arlAnswersData: " + CommonUtils.getCustomGson().toJson(arlAnswersData));
                    endTime = getCurrentTimeStamp();
                    stopCountdown();//stop count down
                    // call api to send ans & ques to server
                    UrlIndex = 4;
                    APIAccess.fetchData(OnlineTestPaperSectionFragmentNew.this, getActivity(), getActivity());
                }
                break;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        countDownTimer = null;
    }

    public void postUserDetail() {
        try {
            String strUserID = CommonUtils.getStringPreferences(context, Constants.USER_ID);
            String strUserName = CommonUtils.getStringPreferences(context, Constants.USER_NAME);
            StringBody userId = new StringBody(strUserID);
            StringBody username = new StringBody(strUserName);
            StringBody strLevelId = new StringBody(levelId);
            StringBody strExamId = new StringBody(examId);

            reqEntity = new MultipartEntity();

            reqEntity.addPart("user_id", userId);
            reqEntity.addPart("username", username);
            reqEntity.addPart("level_id", strLevelId);
            reqEntity.addPart("exam_id", strExamId);

        } catch (Exception e) {
            System.out.println("err" + e);
        }
    }

    protected void setFragment(Fragment fragment) {
        FragmentTransaction ftTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        ftTransaction.replace(R.id.container_body, fragment)
                .addToBackStack(null);
        ftTransaction.commit();
    }

    public void postAnswers() {
        try {
            StringBody strExamId = new StringBody(examId);
            StringBody strLevelId = new StringBody(levelId);
            StringBody strUserId = new StringBody(strUserID);
            StringBody strStartTime = new StringBody(String.valueOf(startTime));
            StringBody strEndTime = new StringBody(String.valueOf(endTime));
            StringBody answers = new StringBody(jsonAnswers);
            reqEntity = new MultipartEntity();

            reqEntity.addPart("exam_id", strExamId);
            reqEntity.addPart("level_id", strLevelId);
            reqEntity.addPart("user_id", strUserId);
            reqEntity.addPart("start_time", strStartTime);
            reqEntity.addPart("end_time", strEndTime);
            reqEntity.addPart("answer", answers);

        } catch (Exception e) {
            System.out.println("err" + e);
        }
    }


    @Override
    public String httpPost() {
        String response = "";
        try {
            if (UrlIndex == 0) {
              //  response = OpenConnection.callUrl("https://sabakuch.com/mock_paper/api/paperdetail?exam_id=2&level_id=1&institute_name=vinayyour");
                response = OpenConnection.callUrl(ServiceUrl.SABAKUCH_PAPER_DETAIL + "?exam_id=" + examId + "&level_id=" + levelId + "&institute_name="+institutionname);
            } else if (UrlIndex == 1) {
                response = OpenConnection.callUrl(ServiceUrl.SABAKUCH_SECTION_DETAIL + "?exam_id="+ examId +  "&level_id=" + levelId  + "&section=1"+"&institute_name="+institutionname);
            } else if (UrlIndex == 2) {
                if (sectionPosition == 0) {
                    pageSection1++;
                    response = OpenConnection.callUrl(ServiceUrl.SABAKUCH_PAPER_QUESTIONS + "?page=" + pageSection1 + "&exam_id="+ examId + "&level_id="  + levelId+ "&section_id=" + strSectionId+"&institute_name="+institutionname);
                } else if (sectionPosition == 1) {
                    pageSection2++;
                    response = OpenConnection.callUrl(ServiceUrl.SABAKUCH_PAPER_QUESTIONS + "?page=" + pageSection2 + "&exam_id=" + examId + "&level_id=" + levelId + "&section_id=" + strSectionId+"&institute_name="+institutionname);
                } else if (sectionPosition == 2) {
                    pageSection3++;
                    response = OpenConnection.callUrl(ServiceUrl.SABAKUCH_PAPER_QUESTIONS + "?page=" + pageSection3 + "&exam_id="+ examId + "&level_id="  + levelId + "&section_id=" + strSectionId+"&institute_name="+institutionname);
                } else if (sectionPosition == 3) {
                    pageSection4++;
                    response = OpenConnection.callUrl(ServiceUrl.SABAKUCH_PAPER_QUESTIONS + "?page=" + pageSection4 + "&exam_id="+ examId + "&level_id=" + levelId  + "&section_id=" + strSectionId+"&institute_name="+institutionname);
                }
                loading = true;
            } else if (UrlIndex == 3) {
                if (sectionPosition == 0) {
                    pageSection1 = 1;
                    response = OpenConnection.callUrl(ServiceUrl.SABAKUCH_PAPER_QUESTIONS + "?feed=1&exam_id="+ examId + "&level_id=" + levelId + "&section_id=" + strSectionId +"&institute_name="+institutionname);
                } else if (sectionPosition == 1) {
                    pageSection2 = 1;
                    response = OpenConnection.callUrl(ServiceUrl.SABAKUCH_PAPER_QUESTIONS + "?feed=1&exam_id=" + examId + "&level_id="  + levelId + "&section_id=" + strSectionId+"&institute_name="+institutionname);
                } else if (sectionPosition == 2) {
                    pageSection3 = 1;
                    response = OpenConnection.callUrl(ServiceUrl.SABAKUCH_PAPER_QUESTIONS + "?feed=1&exam_id=" + examId + "&level_id=" + levelId + "&section_id=" + strSectionId+"&institute_name=" +institutionname);
                } else if (sectionPosition == 3) {
                    pageSection4 = 1;
                    response = OpenConnection.callUrl(ServiceUrl.SABAKUCH_PAPER_QUESTIONS + "?feed=1&exam_id="+ examId  + "&level_id=" + levelId + "&section_id=" + strSectionId+"&institute_name="+institutionname);
                }
                loading = true;
            } else if (UrlIndex == 7) {
                // to call only Jee Advance papers from section wise
                // if exam id == 9 (JEE Advance)
                pageSection1 = 1;
                response = OpenConnection.callUrl(ServiceUrl.SABAKUCH_PAPER_QUESTIONS + "?" + "&exam_id="+ examId + "&level_id="  + levelId +"&institute_name="+institutionname+ "&partid=" + strSectionId + "&sec_ids1=" + sectiondetail.get(sectionPosition).getSections().get(selectionPosition).getSectionid());
            } else if (UrlIndex == 8) {
                // to call only NVPY papers from section wise
                // if exam id == 8 (NVPY)
                pageSection1 = 1;
                response = OpenConnection.callUrl(ServiceUrl.SABAKUCH_PAPER_QUESTIONS + "?" + "&feed=1&exam_id=" + examId+ "&level_id=" + levelId+"&institute_name="  +institutionname+ "&section_id=" + sectiondetail.get(sectionPosition).getSections().get(selectionPosition).getSectionid());
            } else if (UrlIndex == 9) {
                // to call only jee advance from last section but need to change selection
//                pageSection1++;
                pageSection1 = 1;
                response = OpenConnection.callUrl(ServiceUrl.SABAKUCH_PAPER_QUESTIONS + "?" + "&exam_id=" + examId + "&level_id=" + levelId +"&institute_name="+institutionname + "&partid=" + strSectionId + "&sec_ids1=" + sectiondetail.get(sectionPosition).getSections().get(selectionPosition).getSectionid());
            } else if (UrlIndex == 10) {
                // to call only jee advance from last section but need to change selection
                pageSection1 = 1;
                response = OpenConnection.callUrl(ServiceUrl.SABAKUCH_PAPER_QUESTIONS + "?" + "&feed=1&exam_id=" + examId + "&level_id=" + levelId+"&institute_name="+institutionname  + "&section_id=" + sectiondetail.get(sectionPosition).getSections().get(selectionPosition).getSectionid());
            }
            if (UrlIndex == 4) {
                postAnswers();
                response = APIAccess.openConnection(ServiceUrl.SABAKUCH_PAPER_ANSWERS, reqEntity);
            }
            if (UrlIndex == 5) {
                postUserDetail();
                response = APIAccess.openConnection(ServiceUrl.SABAKUCH_USER_DETAIL, reqEntity);
            } else if (UrlIndex == 6) {
                // to call only to get part wise topic array
                response = OpenConnection.callUrl(ServiceUrl.SABAKUCH_SECTION_DETAIL + "?exam_id="+ examId  + "&level_id="+ levelId +
                        "&part=1"+"&institute_name="+institutionname);
            } else if (UrlIndex == 11) {
                // to call only to get passage array i.e fixed
                response = OpenConnection.callUrl(ServiceUrl.SABAKUCH_PAPER_PASSAGE + "?exam_id="+ examId + "&level_id=" + levelId+
                        "&is_fix=1"+"&institute_name="+institutionname);
            } else if (UrlIndex == 12) {
                // to call only to get passage array i.e not fixed
                response = OpenConnection.callUrl(ServiceUrl.SABAKUCH_PAPER_PASSAGE + "?exam_id=" + examId + "&level_id="+ levelId+"&institute_name="+institutionname );
            } else if (UrlIndex == 13) {
                response = OpenConnection.callUrl(ServiceUrl.SABAKUCH_SECTION_DETAIL + "?exam_id=" + examId + "&level_id="+ levelId+"&institute_name="+institutionname+
                        "");
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
                if (UrlIndex == 0) { // UrlIndex == 0
                    arrPaperDetailData = SabaKuchParse.parsePaperDetailData(str);
                    if (arrPaperDetailData.size() > 0) {
                        if (arrPaperDetailData.get(0).time_duration != null && arrPaperDetailData.get(0).time_duration.length() > 0) {
                            timerValue = arrPaperDetailData.get(0).time_duration;
                            UrlIndex = 13;
                            APIAccess.fetchPagingData(OnlineTestPaperSectionFragmentNew.this, getActivity(), getActivity(), true);

                        }
                        mTitle.setText(arrPaperDetailData.get(0).exam_name != null && arrPaperDetailData.get(0).exam_name.length() > 0 ? arrPaperDetailData.get(0).exam_name : getResources().getString(R.string.online_test));
                        exnmane.setText(arrPaperDetailData.get(0).exam_name != null && arrPaperDetailData.get(0).exam_name.length() > 0 ? arrPaperDetailData.get(0).exam_name : getResources().getString(R.string.online_test));
                      //  tvTotalMarks.setText(arrPaperDetailData.get(0).total_marks != null && arrPaperDetailData.get(0).total_marks.length() > 0 ? "Total Marks : " + arrPaperDetailData.get(0).total_marks : "");

                        if (levelId != null && levelId.length() > 0) {
                            if (levelId.equalsIgnoreCase("1")) {
                             //   tvLevel.setText("Level : Beginner");
                            } else if (levelId.equalsIgnoreCase("2")) {
                             //   tvLevel.setText("Level : Intermediate");
                            } else if (levelId.equalsIgnoreCase("3")) {
                               // tvLevel.setText("Level : Expert");
                            }
                        } else {
                           // tvLevel.setText("");
                        }
                        tvDuration.setText(arrPaperDetailData.get(0).time_duration != null && arrPaperDetailData.get(0).time_duration.length() > 0 ? "Duration : " + arrPaperDetailData.get(0).time_duration + " minutes" : "");
                    } else {
                        if (context != null && pb != null && pb.isShowing())
                            pb.dismiss();
                    }
                } else if (UrlIndex == 1) { // UrlIndex == 1
                    arrSectionData = SabaKuchParse.parseSectionDetailData(str);
                    if (arrSectionData != null && arrSectionData.size() > 0) {
                        ArrayList<String> alSectionName = new ArrayList<String>();
                        for (int i = 0; i < arrSectionData.size(); i++) {
                            String sectionName = arrSectionData.get(i).sec_name;
                            alSectionName.add(sectionName);
                            String sectionId = arrSectionData.get(i).sectionid;
                            alSectionId.add(sectionId);
                        }
                        spSection.setVisibility(View.VISIBLE);
                        sectionAdapter = new ProfileSettingsAdapter(getActivity(), alSectionName);
                        spSection.setAdapter(sectionAdapter);

                        strSectionId = alSectionId.get(0);

                        UrlIndex = 3;
                        APIAccess.fetchPagingData(OnlineTestPaperSectionFragmentNew.this, getActivity(), getActivity(), true);

                    } else {
                        spSection.setVisibility(View.GONE);

                        strSectionId = "0";

                        UrlIndex = 3;
                        APIAccess.fetchPagingData(OnlineTestPaperSectionFragmentNew.this, getActivity(), getActivity(), true);
                    }
                } else if (UrlIndex == 2) { // UrlIndex == 2
                    if (context != null && pb != null && pb.isShowing())
                        pb.dismiss();

                    if (sectionPosition == 0) {
                        arrData1 = SabaKuchParse.parseTestPaperData(str);
                        totalPageSection1 = SabaKuchParse.parsePageCount(str);
                        if (arrData1.size() > 0) {
                            arrListData1.addAll(arrData1);
                            sec1ArrayData = new ArrayList<>();
                            sec1ArrayData.addAll(arrListData1);
                            for (int i = 0; i < arrData1.size(); i++) {
                                selectedAnswers1.add("");
                            }
                            for (int i = 0; i < arrData1.size(); i++) {
                                selectedQuestionId1.add("");
                            }
                            populateSampleData(arrListData1);
                        } else {
                            if (context != null && pb != null && pb.isShowing())
                                pb.dismiss();
                        }

                    } else if (sectionPosition == 1) {
                        arrData2 = SabaKuchParse.parseTestPaperData(str);
                        totalPageSection2 = SabaKuchParse.parsePageCount(str);
                        if (arrData2.size() > 0) {
                            arrListData2.addAll(arrData2);
                            sec2ArrayData = new ArrayList<>();
                            sec2ArrayData.addAll(arrListData2);
                            for (int i = 0; i < arrData2.size(); i++) {
                                selectedAnswers2.add("");
                            }
                            for (int i = 0; i < arrData2.size(); i++) {
                                selectedQuestionId2.add("");
                            }
                            populateSampleData(arrListData2);
                        } else {
                            if (context != null && pb != null && pb.isShowing())
                                pb.dismiss();
                        }
                        if (totalPageSection2 == pageSection2)
                            if (strSectionId.equals("35") && examId.equals("17")) {
                                UrlIndex = 11;
                                APIAccess.fetchPagingData(OnlineTestPaperSectionFragmentNew.this, getActivity(), getActivity(), true);
                            }
                    } else if (sectionPosition == 2) {
                        arrData3 = SabaKuchParse.parseTestPaperData(str);
                        totalPageSection3 = SabaKuchParse.parsePageCount(str);
                        if (arrData3.size() > 0) {
                            arrListData3.addAll(arrData3);
                            sec3ArrayData = new ArrayList<>();
                            sec3ArrayData.addAll(arrListData3);
                            for (int i = 0; i < arrData3.size(); i++) {
                                selectedAnswers3.add("");
                            }
                            for (int i = 0; i < arrData3.size(); i++) {
                                selectedQuestionId3.add("");
                            }
                            populateSampleData(arrListData3);
                        } else {
                            if (context != null && pb != null && pb.isShowing())
                                pb.dismiss();
                        }

                    } else if (sectionPosition == 3) {
                        arrData4 = SabaKuchParse.parseTestPaperData(str);
                        totalPageSection4 = SabaKuchParse.parsePageCount(str);
                        if (arrData4.size() > 0) {
                            arrListData4.addAll(arrData4);
                            sec4ArrayData = new ArrayList<>();
                            sec4ArrayData.addAll(arrListData4);
                            for (int i = 0; i < arrData4.size(); i++)

                            {

                                selectedAnswers4.add("");

                            }
                            for (int i = 0; i < arrData4.size(); i++) {
                                selectedQuestionId4.add("");
                                selectedAssignId.add("");
                            }
                            populateSampleData(arrListData4);
                        } else {
                            if (context != null && pb != null && pb.isShowing())
                                pb.dismiss();
                        }

                    }
                } else if (UrlIndex == 3) { // UrlIndex == 3
                    if (firstTimeEnable) {
                        convertTime(timerValue);
                        firstTimeEnable = false;
                    }

                    if (context != null && pb != null && pb.isShowing())
                        pb.dismiss();

                    tvFinish.setEnabled(true);
                    CommonUtils.LogMsg(TAG, "Urlindex: 3, sectionPosition: " + sectionPosition);
                    if (sectionPosition == 0) {
                        arrData1 = SabaKuchParse.parseTestPaperData(str);
                        totalPageSection1 = SabaKuchParse.parsePageCount(str);
                        if (arrData1.size() > 0) {
                            if (arrListData1 != null && arrListData1.size() > 0) {
                                arrListData1.clear();
                            }
                            arrListData1.addAll(arrData1);
                            sec1ArrayData = new ArrayList<>();
                            sec1ArrayData.addAll(arrListData1);
                            for (int i = 0; i < arrData1.size(); i++)
                            {
                                selectedAnswers1.add("");
                            }
                            for (int i = 0; i < arrData1.size(); i++)
                            {
                                selectedQuestionId1.add("");
                            }
                            CommonUtils.LogMsg(TAG, "Urlindex: 3, arrListData1: " + CommonUtils.getCustomGson().toJson(arrListData1));
                            populateSampleData(arrListData1);
                        } else {
                            if (context != null && pb != null && pb.isShowing())
                                pb.dismiss();
                        }
                        if (strSectionId != null)
                            if (strSectionId.equals("11") && examId.equals("11")) {
                                UrlIndex = 12;
                                APIAccess.fetchPagingData(OnlineTestPaperSectionFragmentNew.this, getActivity(), getActivity(), true);
                            }
                    } else if (sectionPosition == 1) {
                        arrData2 = SabaKuchParse.parseTestPaperData(str);
                        totalPageSection2 = SabaKuchParse.parsePageCount(str);
                        if (arrData2.size() > 0) {
                            if (arrListData2 != null && arrListData2.size() > 0) {
                                arrListData2.clear();
                            }
                            arrListData2.addAll(arrData2);
                            sec2ArrayData = new ArrayList<>();
                            sec2ArrayData.addAll(arrListData2);
                            for (int i = 0; i < arrData2.size(); i++) {
                                selectedAnswers2.add("");
                            }
                            for (int i = 0; i < arrData2.size(); i++) {
                                selectedQuestionId2.add("");
                            }
                            populateSampleData(arrListData2);
                        } else {
                            if (context != null && pb != null && pb.isShowing())
                                pb.dismiss();
                        }

                        if (strSectionId.equals("35") && examId.equals("17")) {
                            UrlIndex = 11;
                            APIAccess.fetchPagingData(OnlineTestPaperSectionFragmentNew.this, getActivity(), getActivity(), true);
                        }


                    } else if (sectionPosition == 2) {
                        arrData3 = SabaKuchParse.parseTestPaperData(str);
                        totalPageSection3 = SabaKuchParse.parsePageCount(str);
                        if (arrData3.size() > 0) {
                            if (arrListData3 != null && arrListData3.size() > 0) {
                                arrListData3.clear();
                            }
                            arrListData3.addAll(arrData3);
                            sec3ArrayData = new ArrayList<>();
                            sec3ArrayData.addAll(arrListData3);
                            for (int i = 0; i < arrData3.size(); i++) {
                                selectedAnswers3.add("");
                            }
                            for (int i = 0; i < arrData3.size(); i++) {
                                selectedQuestionId3.add("");
                            }
                            populateSampleData(arrListData3);
                        } else {
                            if (context != null && pb != null && pb.isShowing())
                                pb.dismiss();
                        }
                    } else if (sectionPosition == 3) {
                        arrData4 = SabaKuchParse.parseTestPaperData(str);
                        totalPageSection4 = SabaKuchParse.parsePageCount(str);
                        if (arrData4.size() > 0) {
                            if (arrListData4 != null && arrListData4.size() > 0) {
                                arrListData4.clear();
                            }
                            arrListData4.addAll(arrData4);
                            sec4ArrayData = new ArrayList<>();
                            sec4ArrayData.addAll(arrListData4);
                            for (int i = 0; i < arrData4.size(); i++) {
                                selectedAnswers4.add("");
                            }
                            for (int i = 0; i < arrData4.size(); i++) {
                                selectedQuestionId4.add("");
                                selectedAssignId.add("");
                            }
                            populateSampleData(arrListData4);
                        } else {
                            if (context != null && pb != null && pb.isShowing())
                                pb.dismiss();
                        }
                    }
                } else if (UrlIndex == 4) { // UrlIndex == 4
                    if (SabaKuchParse.jsonTestPaperResult(str) != null && SabaKuchParse.jsonTestPaperResult(str).length() > 0) {
                        UrlIndex = 5;
                        APIAccess.fetchPagingData(OnlineTestPaperSectionFragmentNew.this, getActivity(), getActivity(), true);
                        Fragment fragment = new ResultFragmentNew();
                        Bundle bundle = new Bundle();
                        bundle.putString(Constants.LAST_PAPER_ID, SabaKuchParse.jsonTestPaperResult(str));
                        fragment.setArguments(bundle);
                        setFragment(fragment);
                    }
                } else if (UrlIndex == 6) { // UrlIndex == 6

//                    UrlIndex = 3;
//                    APIAccess.fetchPagingData(OnlineTestPaperSectionFragment.this, getActivity(), getActivity(), true);
                    SectionDetailPartData mSectionDetailPartData = CommonUtils.getCustomGson().fromJson(str, SectionDetailPartData.class);
                    if (mSectionDetailPartData != null && mSectionDetailPartData.getEmbedded().getSectiondetail().size() > 0) {
                        sectiondetail.addAll(mSectionDetailPartData.getEmbedded().getSectiondetail());
                        ArrayList<String> alSectionName = new ArrayList<String>();
                        for (int i = 0; i < sectiondetail.size(); i++) {
                            String sectionName = sectiondetail.get(i).getName();
                            alSectionName.add(sectionName);
                            String sectionId = sectiondetail.get(i).getPartId();
                            alSectionId.add(sectionId);
                        }
                        spSection.setVisibility(View.VISIBLE);
                        sectionAdapter = new ProfileSettingsAdapter(getActivity(), alSectionName);
                        spSection.setAdapter(sectionAdapter);

                        strSectionId = alSectionId.get(0);

                        if (examId.equals("9")) {
                            UrlIndex = 7;
                            APIAccess.fetchPagingData(OnlineTestPaperSectionFragmentNew.this, getActivity(), getActivity(), true);
                        } else if (examId.equals("16")) {
                            UrlIndex = 8;
                            APIAccess.fetchPagingData(OnlineTestPaperSectionFragmentNew.this, getActivity(), getActivity(), true);
                        }

                    } else {
                        spSection.setVisibility(View.GONE);

                        strSectionId = "0";

                        if (examId.equals("9")) {
                            UrlIndex = 7;
                            APIAccess.fetchPagingData(OnlineTestPaperSectionFragmentNew.this, getActivity(), getActivity(), true);
                        } else if (examId.equals("16")) {
                            UrlIndex = 8;
                            APIAccess.fetchPagingData(OnlineTestPaperSectionFragmentNew.this, getActivity(), getActivity(), true);
                        }
                    }
                } else if (UrlIndex == 7 || UrlIndex == 8) { // UrlIndex == 7 Or  UrlIndex == 8
                    selectionPosition = 0;
                    QuestionDataResponse mQuestionDataResponse = CommonUtils.getCustomGson().fromJson(str, QuestionDataResponse.class);

                    CommonUtils.LogMsg(OnlineTestPaperSectionFragment.class.getSimpleName(), CommonUtils.getCustomGson().toJson(mQuestionDataResponse));
                    // add instructions on the top
                    QuestionDataResponse.Question mQuestion = new QuestionDataResponse().new Question();
                    mQuestion.setInstruction(true);
                    mQuestion.setPart_name(sectiondetail.get(sectionPosition).getSections().get(selectionPosition).getSecName());
                    if (sectiondetail.get(sectionPosition).getSections().get(selectionPosition).getInstructions() != null && sectiondetail.get(sectionPosition).getSections().get(selectionPosition).getInstructions().size() > 0) {
                        mQuestion.setInstruction(true);
                        mQuestion.setInstruction_data(sectiondetail.get(sectionPosition).getSections().get(selectionPosition).getInstructions().get(0).getInstruction());
                    } else {
                        mQuestion.setInstruction(true);
                    }
//                        mQuestion.setInstruction_data(sectiondetail.get(sectionPosition).getSections().get(selectionPosition).getInstructions().get(0).getInstruction());
                    mQuestion.setUiType(sectiondetail.get(sectionPosition).getSections().get(selectionPosition).getUitype());
                    questions.add(0, mQuestion);
                    // after adding instructions add all
                    questions.addAll(mQuestionDataResponse.getEmbedded().getQuestions());
                    for (int i = 0; i < questions.size(); i++) {
                        QuestionDataResponse.Question que = questions.get(i);
                        que.setSectionposition(sectionPosition);
                        que.setSelectionposition(selectionPosition);
                        que.setQuestionNo(i);
                        que.setUiType(sectiondetail.get(sectionPosition).getSections().get(selectionPosition).getUitype());
                        questions.set(i, que);
                    }

                    // to saves array according to section position
                    if (sectionPosition == 0) {
                        questions1.addAll(questions);
                        populateQuesData(questions1);
                        questions.clear();
                    } else if (sectionPosition == 1) {
                        questions2.addAll(questions);
                        populateQuesData(questions2);
                        questions.clear();
                    } else if (sectionPosition == 2) {
                        questions3.addAll(questions);
                        populateQuesData(questions3);
                        questions.clear();
                    }


                    mOnlinePartAdapter.notifyDataSetChanged();
                    if (firstTimeEnable) {
                        convertTime(timerValue);
                        firstTimeEnable = false;
                    }

                    if (context != null && pb != null && pb.isShowing())
                        pb.dismiss();

                    tvFinish.setEnabled(true);
                    // call next selection according to section

                    selectionPosition++;
                    if (examId.equals("9")) {
                        UrlIndex = 9;
                        APIAccess.fetchPagingData(OnlineTestPaperSectionFragmentNew.this, getActivity(), getActivity(), true);
                    } else if (examId.equals("16")) {
                        UrlIndex = 10;
                        APIAccess.fetchPagingData(OnlineTestPaperSectionFragmentNew.this, getActivity(), getActivity(), true);
                    }
                    CommonUtils.LogMsg(TAG, "selectionPosition:" + selectionPosition + " " + "questions: " + questions.size() + "ques1: " + questions1.size() + " ques2: " + questions2.size() + " ques3: " + questions3.size());

                } else if (UrlIndex == 9 || UrlIndex == 10) { // UrlIndex == 9 || UrlIndex == 10

                    QuestionDataResponse mQuestionDataResponse = CommonUtils.getCustomGson().fromJson(str, QuestionDataResponse.class);

                    CommonUtils.LogMsg(OnlineTestPaperSectionFragment.class.getSimpleName(), "selectionPosition: " + selectionPosition + CommonUtils.getCustomGson().toJson(mQuestionDataResponse));
                    int size = questions.size();
                    // add instructions on the
                    QuestionDataResponse.Question mQuestion = new QuestionDataResponse().new Question();
                    if (sectiondetail.get(sectionPosition).getSections().get(selectionPosition).getInstructions() != null && sectiondetail.get(sectionPosition).getSections().get(selectionPosition).getInstructions().size() > 0) {
                        mQuestion.setInstruction(true);
                        mQuestion.setInstruction_data(sectiondetail.get(sectionPosition).getSections().get(selectionPosition).getInstructions().get(0).getInstruction());
                    } else {
                        mQuestion.setInstruction(true);
                    }
                    mQuestion.setPart_name(sectiondetail.get(sectionPosition).getSections().get(selectionPosition).getSecName());
                    mQuestion.setUiType(sectiondetail.get(sectionPosition).getSections().get(selectionPosition).getUitype());

                    questions.add(questions.size(), mQuestion);
                    // after adding instructions add all
                    questions.addAll(mQuestionDataResponse.getEmbedded().getQuestions());
                    int j = 0;
                    for (int i = size; i < questions.size(); i++) {

                        QuestionDataResponse.Question que = questions.get(i);
                        que.setSectionposition(sectionPosition);
                        que.setSelectionposition(selectionPosition);
                        que.setQuestionNo(j);
                        que.setUiType(sectiondetail.get(sectionPosition).getSections().get(selectionPosition).getUitype());
//                        que.setInstruction_data(sectiondetail.get(sectionPosition).getSections().get(selectionPosition).getInstructions().get(0).getInstruction());

                        questions.set(i, que);
                        j++;
                        CommonUtils.LogMsg(TAG, "UrlIndex = 9 , Question No: " + questions.get(i).getQuestionNo());
                    }

                    // to saves array according to section position
                    if (sectionPosition == 0) {
                        questions1.addAll(questions);
                        populateQuesDataRefresh(questions1);
                        questions.clear();
                    } else if (sectionPosition == 1) {
                        questions2.addAll(questions);
                        populateQuesDataRefresh(questions2);
                        questions.clear();
                    } else if (sectionPosition == 2) {
                        questions3.addAll(questions);
                        populateQuesDataRefresh(questions3);
                        questions.clear();
                    }

                    // to saves array according to section position
                    if (sectionPosition == 0) {
                        for (int i = 0; i < questions1.size(); i++) {
                            if (questions1.get(i).getUiType() == 1)
                                selectedAnswers1.add("");
                        }
                        for (int i = 0; i < questions1.size(); i++) {
                            if (questions1.get(i).getUiType() == 1)
                                selectedQuestionId1.add("");
                        }
                    } else if (sectionPosition == 1) {
                        for (int i = 0; i < questions2.size(); i++) {
                            if (questions2.get(i).getUiType() == 1)
                                selectedAnswers2.add("");
                        }
                        for (int i = 0; i < questions2.size(); i++) {
                            if (questions2.get(i).getUiType() == 1)
                                selectedQuestionId2.add("");
                        }
                    } else if (sectionPosition == 2) {
                        for (int i = 0; i < questions3.size(); i++) {
                            if (questions3.get(i).getUiType() == 1)
                                selectedAnswers3.add("");
                        }
                        for (int i = 0; i < questions3.size(); i++) {
                            if (questions3.get(i).getUiType() == 1)
                                selectedQuestionId3.add("");
                        }
                    }

                    // call next selection according to section
                    if (selectionPosition > 0) {
                        CommonUtils.LogMsg(TAG, "selectionPosition: " + selectionPosition);
                        selectionPosition++;
                        if (examId.equals("9")) {
                            UrlIndex = 9;
                            APIAccess.fetchPagingData(OnlineTestPaperSectionFragmentNew.this, getActivity(), getActivity(), true);
                        } else if (examId.equals("16")) {
                            UrlIndex = 10;
                            APIAccess.fetchPagingData(OnlineTestPaperSectionFragmentNew.this, getActivity(), getActivity(), true);
                        }
                    }
                    CommonUtils.LogMsg(TAG, "questions: " + questions.size() + "ques1: " + questions1.size() + " ques2: " + questions2.size() + " ques3: " + questions3.size());

                } else if (UrlIndex == 11) { // UrlIndex == 11
                    handlePassageFix(str);
                    UrlIndex = 12;
                    APIAccess.fetchPagingData(OnlineTestPaperSectionFragmentNew.this, getActivity(), getActivity(), true);
                } else if (UrlIndex == 12) { // UrlIndex == 12
                    if (sectionPosition == 1)
                        handlePassage(str);
                    else if (sectionPosition == 0)
                        handlePassageArrList1(str);
                } else if (UrlIndex == 13) { // UrlIndex == 13
                    SectionDetail mSectionDetail = CommonUtils.getCustomGson().fromJson(str, SectionDetail.class);
                    if (mSectionDetail.getEmbedded() != null) {
                        if (mSectionDetail.getEmbedded().getSectiondetail() != null) {
                            sectiondetailinfo.add(mSectionDetail.getEmbedded().getSectiondetail());
                            if (sectiondetailinfo.size() > 0) {
                                String isSection = sectiondetailinfo.get(0).get(0).getIsSection();
                                if (isSection.equalsIgnoreCase("1")) {
                                    if (!examId.equals("9") && !examId.equals("16")) {
                                        UrlIndex = 1;
                                        APIAccess.fetchPagingData(OnlineTestPaperSectionFragmentNew.this, getActivity(), getActivity(), true);
                                    } else {
                                        UrlIndex = 6;
                                        APIAccess.fetchPagingData(OnlineTestPaperSectionFragmentNew.this, getActivity(), getActivity(), true);
                                    }
                                } else {
                                    UrlIndex = 3;
                                    APIAccess.fetchData(OnlineTestPaperSectionFragmentNew.this, getActivity(), getActivity());
                                }
                            }
                        }
                    }
                }
            } else {
                if (context != null && pb != null && pb.isShowing())
                    pb.dismiss();
                //     Toast.makeText(context,getResources().getString(R.string.server_not_responding),Toast.LENGTH_SHORT).show();
            }
        }
        return null;
    }

    private void handlePassageFix(String str) {
        TestPaperData.PassageResponse mPassageResponse = CommonUtils.getCustomGson().fromJson(str, TestPaperData.PassageResponse.class);
        CommonUtils.LogMsg(TAG, CommonUtils.getCustomGson().toJson(mPassageResponse));
        if (mPassageResponse.getEmbedded() != null) {
            if (mPassageResponse.getEmbedded().getPassage() != null && mPassageResponse.getEmbedded().getPassage().size() > 0) {
                if (mPassageResponse.getEmbedded().getPassage().get(0) != null) {
                    TestPaperData mTestPaperData = new TestPaperData();
                    mTestPaperData.isPassage = true;
                    mTestPaperData.Qno = "-1";
                    mTestPaperData.passageid = mPassageResponse.getEmbedded().getPassage().get(0).getPassageid();
                    mTestPaperData.passage = mPassageResponse.getEmbedded().getPassage().get(2).getPassage();
                    mTestPaperData.passagetitle = mPassageResponse.getEmbedded().getPassage().get(1).getPassagetitle();
                    arrListData2.add(arrListData2.size(), mTestPaperData);
                    sec2ArrayData.clear();
                    sec2ArrayData.addAll(arrListData2);

                    if (mPassageResponse.getEmbedded().getPassage().get(3).getQuestions() != null && mPassageResponse.getEmbedded().getPassage().get(3).getQuestions().size() > 0) {
                        arrQuestions.addAll(mPassageResponse.getEmbedded().getPassage().get(3).getQuestions());
                        int size = arrListData2.size();

                        for (int i = 0; i < arrQuestions.size(); i++) {
                            TestPaperData mTestPaperData1 = new TestPaperData();
                            mTestPaperData1.isPassage = false;
                            mTestPaperData1.Qno = size + "";
                            mTestPaperData1.qb_id = arrQuestions.get(i).getQbId();
                            mTestPaperData1.question = arrQuestions.get(i).getQuestion();
                            mTestPaperData1.option_a = arrQuestions.get(i).getOptionA();
                            mTestPaperData1.option_b = arrQuestions.get(i).getOptionB();
                            mTestPaperData1.option_c = arrQuestions.get(i).getOptionC();
                            mTestPaperData1.option_d = arrQuestions.get(i).getOptionD();
                            arrListData2.add(size, mTestPaperData1);
                            size++;
                        }
                        arrQuestions.clear();
                    }
                    sec2ArrayData.clear();
                    sec2ArrayData.addAll(arrListData2);
                    populateSampleData(arrListData2);
                    if (sectionPosition == 1) {
                        for (int i = 0; i < arrListData2.size(); i++) {
                            selectedAnswers2.add("");
                        }
                        for (int i = 0; i < arrListData2.size(); i++) {
                            selectedQuestionId2.add("");
                        }
                    }
                    CommonUtils.LogMsg(TAG, "arrListData2 size: " + arrListData2.size());
                }
            }
        }
    }

    private void handlePassage(String str) {
        TestPaperData.PassageResponse mPassageResponse = CommonUtils.getCustomGson().fromJson(str, TestPaperData.PassageResponse.class);
        CommonUtils.LogMsg(TAG, CommonUtils.getCustomGson().toJson(mPassageResponse));
        if (mPassageResponse.getEmbedded() != null) {
            if (mPassageResponse.getEmbedded().getPassage() != null && mPassageResponse.getEmbedded().getPassage().size() > 0) {
                if (mPassageResponse.getEmbedded().getPassage().get(0) != null) {
                    TestPaperData mTestPaperData = new TestPaperData();
                    mTestPaperData.isPassage = true;
                    mTestPaperData.Qno = "-1";
                    mTestPaperData.passageid = mPassageResponse.getEmbedded().getPassage().get(0).getPassageid();
                    mTestPaperData.passage = mPassageResponse.getEmbedded().getPassage().get(2).getPassage();
                    mTestPaperData.passagetitle = mPassageResponse.getEmbedded().getPassage().get(1).getPassagetitle();
                    arrListData2.add(arrListData2.size(), mTestPaperData);
                    sec2ArrayData.clear();
                    sec2ArrayData.addAll(arrListData2);

                    if (mPassageResponse.getEmbedded().getPassage().get(3).getQuestions() != null && mPassageResponse.getEmbedded().getPassage().get(3).getQuestions().size() > 0) {
                        arrQuestions.addAll(mPassageResponse.getEmbedded().getPassage().get(3).getQuestions());
                        int size = arrListData2.size();

                        for (int i = 0; i < arrQuestions.size(); i++) {
                            TestPaperData mTestPaperData1 = new TestPaperData();
                            mTestPaperData1.isPassage = false;
                            mTestPaperData1.Qno = (size - 1) + "";
                            mTestPaperData1.qb_id = arrQuestions.get(i).getQbId();
                            mTestPaperData1.question = arrQuestions.get(i).getQuestion();
                            mTestPaperData1.option_a = arrQuestions.get(i).getOptionA();
                            mTestPaperData1.option_b = arrQuestions.get(i).getOptionB();
                            mTestPaperData1.option_c = arrQuestions.get(i).getOptionC();
                            mTestPaperData1.option_d = arrQuestions.get(i).getOptionD();
                            arrListData2.add(size, mTestPaperData1);
                            size++;
                        }
                        arrQuestions.clear();
                    }
                    sec2ArrayData.clear();
                    sec2ArrayData.addAll(arrListData2);
                    populateSampleData(arrListData2);
                    if (sectionPosition == 1) {
                        for (int i = 0; i < arrListData2.size(); i++) {
                            selectedAnswers2.add("");
                        }
                        for (int i = 0; i < arrListData2.size(); i++) {
                            selectedQuestionId2.add("");
                        }
                    }
                    CommonUtils.LogMsg(TAG, "arrListData2 size: " + arrListData2.size());
                }
            }
        }
    }

    private void handlePassageArrList1(String str) {
        TestPaperData.PassageResponse mPassageResponse = CommonUtils.getCustomGson().fromJson(str, TestPaperData.PassageResponse.class);
        CommonUtils.LogMsg(TAG, CommonUtils.getCustomGson().toJson(mPassageResponse));
        if (mPassageResponse.getEmbedded() != null) {
            if (mPassageResponse.getEmbedded().getPassage() != null && mPassageResponse.getEmbedded().getPassage().size() > 0) {
                if (mPassageResponse.getEmbedded().getPassage().get(0) != null) {
                    TestPaperData mTestPaperData = new TestPaperData();
                    mTestPaperData.isPassage = true;
                    mTestPaperData.Qno = "-1";
                    mTestPaperData.passageid = mPassageResponse.getEmbedded().getPassage().get(0).getPassageid();
                    mTestPaperData.passage = mPassageResponse.getEmbedded().getPassage().get(2).getPassage();
                    mTestPaperData.passagetitle = mPassageResponse.getEmbedded().getPassage().get(1).getPassagetitle();
                    arrListData1.add(arrListData1.size(), mTestPaperData);
                    sec1ArrayData.clear();
                    sec1ArrayData.addAll(arrListData1);

                    if (mPassageResponse.getEmbedded().getPassage().get(3).getQuestions() != null && mPassageResponse.getEmbedded().getPassage().get(3).getQuestions().size() > 0) {
                        arrQuestions.addAll(mPassageResponse.getEmbedded().getPassage().get(3).getQuestions());
                        int size = arrListData1.size();

                        for (int i = 0; i < arrQuestions.size(); i++) {
                            TestPaperData mTestPaperData1 = new TestPaperData();
                            mTestPaperData1.isPassage = false;
                            mTestPaperData1.Qno = (size - 1) + "";
                            mTestPaperData1.qb_id = arrQuestions.get(i).getQbId();
                            mTestPaperData1.question = arrQuestions.get(i).getQuestion();
                            mTestPaperData1.option_a = arrQuestions.get(i).getOptionA();
                            mTestPaperData1.option_b = arrQuestions.get(i).getOptionB();
                            mTestPaperData1.option_c = arrQuestions.get(i).getOptionC();
                            mTestPaperData1.option_d = arrQuestions.get(i).getOptionD();
                            arrListData1.add(size, mTestPaperData1);
                            size++;
                        }
                        arrQuestions.clear();
                    }
                    sec1ArrayData.clear();
                    sec1ArrayData.addAll(arrListData1);
                    populateSampleData(arrListData1);
                    if (sectionPosition == 0) {
                        for (int i = 0; i < arrListData1.size(); i++) {
                            selectedAnswers1.add("");
                        }
                        for (int i = 0; i < arrListData1.size(); i++) {
                            selectedQuestionId1.add("");
                        }
                    }
                    CommonUtils.LogMsg(TAG, "arrListData1 size: " + arrListData1.size());
                }
            }
        }
    }

    private void populateSampleData(ArrayList<TestPaperData> arrData) {
        for (int i = 0; i < arrData.size(); i++) {
            TestPaperData dm = new TestPaperData();
            dm.setHeaderTitle("" + i);

        }

        if (adapter == null) {
            adapter = new OnlineTestPaperAdapterNew(context, arrData, sectionPosition);
            recyclerView.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    private void populateQuesDataRefresh(ArrayList<QuestionDataResponse.Question> arrData) {


        if (mOnlinePartAdapter == null) {
            mOnlinePartAdapter = new OnlinePartAdapter(context, arrData, this);
            lstPart.setAdapter(mOnlinePartAdapter);
        } else {
            mOnlinePartAdapter.notifyDataSetChanged();
        }
    }

    private void populateQuesData(ArrayList<QuestionDataResponse.Question> arrData) {
        mOnlinePartAdapter = null;

        mOnlinePartAdapter = new OnlinePartAdapter(context, arrData, this);
        lstPart.setAdapter(mOnlinePartAdapter);

    }
}
